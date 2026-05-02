package no.clueless.opencargo.pricing;

import no.clueless.opencargo.PricingQuery;
import no.clueless.opencargo.pricing.breakdown.PriceBreakdown;
import no.clueless.opencargo.pricing.breakdown.PriceComponent;
import no.clueless.opencargo.pricing.breakdown.PriceComponentType;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

import java.util.Comparator;
import java.util.stream.Stream;

public class PriceEngine {
    private final PricingPolicies policies;

    public PriceEngine(PricingPolicies policies) {
        this.policies = ArgumentExceptionHelper.throwIfNull(policies, "policies");
    }

    public PriceBreakdown calculate(PricingQuery query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");

        var bestPolicy = policies.stream()
                .filter(policy -> policy.resolve(query).isApplicable())
                .max(Comparator.comparingInt(PricingPolicy::getPriority))
                .orElseThrow(() -> new RuntimeException("No applicable pricing policies found"));

        var baseComponent = new PriceComponent(bestPolicy.getName(), bestPolicy.getBasePrice(), PriceComponentType.BASE);

        var modifierStream = bestPolicy.getPriceModifiers()
                .stream()
                .filter(modifier -> modifier.isApplicable(query))
                .map(modifier -> new PriceComponent(
                        modifier.getName(),
                        modifier.calculateDelta(query, baseComponent.getAmount()),
                        PriceComponentType.SURCHARGE
                ));

        return Stream.concat(Stream.of(baseComponent), modifierStream)
                .collect(PriceBreakdown.collector(query.getCurrency()));
    }
}
