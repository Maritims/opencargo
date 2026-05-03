package no.clueless.opencargo.pricing.domain.service.engine;

import no.clueless.opencargo.bindings.PricingPolicyListDTO;
import no.clueless.opencargo.infrastructure.marshalling.XmlMarshaller;
import no.clueless.opencargo.pricing.domain.model.breakdown.PriceBreakdown;
import no.clueless.opencargo.pricing.domain.model.breakdown.PriceComponent;
import no.clueless.opencargo.pricing.domain.model.breakdown.PriceComponentType;
import no.clueless.opencargo.pricing.domain.model.policy.PricingPolicy;
import no.clueless.opencargo.pricing.domain.model.policy.PricingPolicyMapper;
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

    public static class Builder {
        private Population<PricingPolicy, Set<PricingPolicy>> policies;

        private Builder() {
        }

        public Builder withPolicies(Population<PricingPolicy, Set<PricingPolicy>> policies) {
            this.policies = ArgumentExceptionHelper.throwIfNull(policies, "policies");
            return this;
        }

        public Builder withPoliciesFromResources(String filename) {
            ArgumentExceptionHelper.throwIfNullOrBlank(filename, "filename");

            var pricingPolicyListDTO = XmlMarshaller.unmarshalResourceSilently(filename, PricingPolicyListDTO.class);
            var policies = PricingPolicyMapper.getInstance().mapToPolicies(pricingPolicyListDTO);

            return withPolicies(policies);
        }

        public Builder withPoliciesFromResources() {
            return withPoliciesFromResources("pricing.xml");
        }

        public PriceEngine build() {
            return new PriceEngine(policies);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private static final class SingletonHolder {
        private static final PriceEngine INSTANCE = PriceEngine.newBuilder()
                .withPoliciesFromResources()
                .build();
    }

    public static PriceEngine getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
