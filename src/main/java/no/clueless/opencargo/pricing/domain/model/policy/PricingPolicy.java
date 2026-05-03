package no.clueless.opencargo.pricing.domain.model.policy;

import no.clueless.opencargo.shared.applicability.ApplicabilityReports;
import no.clueless.opencargo.shared.applicability.Rejection;
import no.clueless.opencargo.pricing.domain.service.engine.PricingQuery;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.Population;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PricingPolicy implements Comparable<PricingPolicy> {
    private static final Logger                                        log = LoggerFactory.getLogger(PricingPolicy.class);
    private final        String                                        name;
    private final        Population<PricingRule, Set<PricingRule>>     pricingRules;
    private final        Population<PriceModifier, Set<PriceModifier>> priceModifiers;
    private final        BigDecimal                                    basePrice;
    private final        int                                           priority;

    public PricingPolicy(String name, Population<PricingRule, Set<PricingRule>> pricingRules, Population<PriceModifier, Set<PriceModifier>> priceModifiers, BigDecimal basePrice, int priority) {
        this.name           = ArgumentExceptionHelper.throwIfNullOrBlank(name, "name");
        this.pricingRules   = pricingRules;
        this.priceModifiers = priceModifiers;
        this.basePrice      = ArgumentExceptionHelper.throwIfNullOrNegative(basePrice, "basePrice");
        this.priority       = ArgumentExceptionHelper.throwIfNegative(priority, "priority");
    }

    public String getName() {
        return name;
    }

    public Population<PricingRule, Set<PricingRule>> getPricingRules() {
        return pricingRules;
    }

    public Population<PriceModifier, Set<PriceModifier>> getPriceModifiers() {
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

        return new ApplicabilityReports<>(applicablePricingRules.isEmpty() ? null : Population.fromSetOf(applicablePricingRules), rejections.isEmpty() ? null : Population.fromSetOf(rejections));
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
