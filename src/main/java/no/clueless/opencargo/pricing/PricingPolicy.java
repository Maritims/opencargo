package no.clueless.opencargo.pricing;

import no.clueless.opencargo.PricingQuery;
import no.clueless.opencargo.applicability.ApplicabilityReports;
import no.clueless.opencargo.applicability.Rejection;
import no.clueless.opencargo.applicability.Rejections;
import no.clueless.opencargo.util.ArgumentExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class PricingPolicy implements Comparable<PricingPolicy> {
    private static final Logger         log = LoggerFactory.getLogger(PricingPolicy.class);
    private final        String         name;
    private final        PricingRules   pricingRules;
    private final        PriceModifiers priceModifiers;
    private final        BigDecimal     basePrice;
    private final        int            priority;

    public PricingPolicy(String name, PricingRules pricingRules, PriceModifiers priceModifiers, BigDecimal basePrice, int priority) {
        this.name           = ArgumentExceptionHelper.throwIfNullOrBlank(name, "name");
        this.pricingRules   = ArgumentExceptionHelper.throwIfNull(pricingRules, "rules");
        this.priceModifiers = priceModifiers;
        this.basePrice      = ArgumentExceptionHelper.throwIfNullOrNegative(basePrice, "basePrice");
        this.priority       = ArgumentExceptionHelper.throwIfNegative(priority, "priority");
    }

    public String getName() {
        return name;
    }

    public PricingRules getPricingRules() {
        return pricingRules;
    }

    public PriceModifiers getPriceModifiers() {
        return priceModifiers;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public int getPriority() {
        return priority;
    }

    public ApplicabilityReports<PricingRule> resolve(PricingQuery query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");
        var applicablePricingRules = new HashSet<PricingRule>();
        var rejections             = new HashSet<Rejection<PricingRule>>();

        for (var pricingRule : pricingRules) {
            var evaluationResult = pricingRule.evaluate(query);
            if (evaluationResult.isSatisfied()) {
                applicablePricingRules.add(pricingRule);
            } else {
                rejections.add(new Rejection<>(pricingRule, evaluationResult.getReason()));
            }
        }

        return new ApplicabilityReports<>(applicablePricingRules.isEmpty() ? null : new PricingRules(applicablePricingRules), rejections.isEmpty() ? null : new Rejections<>(rejections));
    }

    public Optional<BigDecimal> calculateFinalPrice(PricingQuery query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");

        var applicabilityReports = resolve(query);
        if (!applicabilityReports.isApplicable()) {
            log.warn("No applicable pricing rules found for query {}: {}", query, applicabilityReports.getRejections()
                    .stream()
                    .map(Rejection::getReason)
                    .collect(Collectors.joining(", ")));
            return Optional.empty();
        }

        var finalPrice = basePrice;

        for (var modifier : priceModifiers) {
            if (modifier.isApplicable(query)) {
                finalPrice = modifier.modify(query, finalPrice);
            }
        }

        return Optional.ofNullable(finalPrice);
    }

    @Override
    public int compareTo(PricingPolicy o) {
        return Integer.compare(priority, o.priority);
    }
}
