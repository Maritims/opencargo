package no.clueless.opencargo.pricing;

import no.clueless.opencargo.bindings.PricingPolicyListDTO;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
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

    public static Collector<PricingPolicy, Set<PricingPolicy>, PricingPolicies> collector() {
        return new Collector<>() {

            @Override
            public Supplier<Set<PricingPolicy>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<PricingPolicy>, PricingPolicy> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<PricingPolicy>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<PricingPolicy>, PricingPolicies> finisher() {
                return PricingPolicies::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }

    public static PricingPolicies from(PricingPolicyListDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        return PricingPoliciesMapper.getInstance().mapToPolicies(dto);
    }
}
