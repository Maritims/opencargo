package no.clueless.opencargo.pricing;

import no.clueless.opencargo.bindings.PricingRuleListDTO;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class PricingRules implements Iterable<PricingRule> {
    private final Set<PricingRule> rules;

    public PricingRules(Set<PricingRule> rules) {
        this.rules = ArgumentExceptionHelper.throwIfNullOrEmpty(rules, "rules");
    }

    public int size() {
        return rules.size();
    }

    @Override
    public Iterator<PricingRule> iterator() {
        return rules.iterator();
    }

    public Stream<PricingRule> stream() {
        return rules.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PricingRules that = (PricingRules) o;
        return Objects.equals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rules);
    }

    @Override
    public String toString() {
        return "PricingRules{" +
                "rules=" + rules +
                '}';
    }

    public static Collector<PricingRule, Set<PricingRule>, PricingRules> collector() {
        return new Collector<PricingRule, Set<PricingRule>, PricingRules>() {
            @Override
            public Supplier<Set<PricingRule>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<PricingRule>, PricingRule> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<PricingRule>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<PricingRule>, PricingRules> finisher() {
                return PricingRules::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }

    public static PricingRules from(PricingRuleListDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        return PricingPoliciesMapper.getInstance().mapToPricingRules(dto);
    }
}
