package no.clueless.opencargo.domain.geography;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public final class PostalCodeSet extends PostalCodes implements PostalCodeConstraint {
    public PostalCodeSet(Set<PostalCode> postalCodes) {
        super(postalCodes);
    }

    public static Collector<String, Set<PostalCode>, PostalCodeSet> collector() {
        return new Collector<>() {
            @Override
            public Supplier<Set<PostalCode>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<PostalCode>, String> accumulator() {
                return (set, str) -> set.add(new PostalCode(str));
            }

            @Override
            public BinaryOperator<Set<PostalCode>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<PostalCode>, PostalCodeSet> finisher() {
                return PostalCodeSet::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }
}
