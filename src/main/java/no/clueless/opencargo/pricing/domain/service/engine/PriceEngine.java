package no.clueless.opencargo.pricing.domain.service.engine;

import no.clueless.opencargo.pricing.domain.model.breakdown.PriceBreakdown;
import no.clueless.opencargo.pricing.domain.model.breakdown.PriceComponent;
import no.clueless.opencargo.pricing.domain.model.breakdown.PriceComponentType;
import no.clueless.opencargo.pricing.domain.model.policy.PricingPolicy;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.Population;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class PriceEngine {
    private final Population<PricingPolicy, Set<PricingPolicy>> policies;

    public PriceEngine(Population<PricingPolicy, Set<PricingPolicy>> policies) {
        this.policies = ArgumentExceptionHelper.throwIfNull(policies, "policies");
    }

    public Optional<PriceBreakdown> calculate(PricingQuery query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");

        var bestPolicy = policies.stream()
                .filter(policy -> policy.resolve(query).isApplicable())
                .max(Comparator.comparingInt(PricingPolicy::getPriority))
                .orElse(null);

        if (bestPolicy == null) {
            // No applicable policies found.
            return Optional.empty();
        }

        var baseComponent = new PriceComponent(bestPolicy.getName(), bestPolicy.getBasePrice(), PriceComponentType.BASE);

        if (bestPolicy.getPriceModifiers() != null) {
            var modifierStream = bestPolicy.getPriceModifiers()
                    .stream()
                    .filter(modifier -> modifier.isApplicable(query))
                    .map(modifier -> new PriceComponent(
                            modifier.getName(),
                            modifier.calculateDelta(query, baseComponent.getAmount()),
                            PriceComponentType.SURCHARGE
                    ));

            return Optional.ofNullable(Stream.concat(Stream.of(baseComponent), modifierStream)
                    .collect(PriceBreakdown.collector(query.getCurrency())));
        } else {
            return Optional.ofNullable(Stream.of(baseComponent).collect(PriceBreakdown.collector(query.getCurrency())));
        }
    }
}
