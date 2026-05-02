package no.clueless.opencargo.pricing;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class PricingPolicies implements Iterable<PricingPolicy> {
    private final Set<PricingPolicy> policies;

    public PricingPolicies(Set<PricingPolicy> policies) {
        this.policies = ArgumentExceptionHelper.throwIfNullOrEmpty(policies, "policies");
    }

    @Override
    public Iterator<PricingPolicy> iterator() {
        return policies.iterator();
    }

    public Stream<PricingPolicy> stream() {
        return policies.stream();
    }
}
