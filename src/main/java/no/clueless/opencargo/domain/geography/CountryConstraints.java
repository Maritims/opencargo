package no.clueless.opencargo.domain.geography;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import javax.swing.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CountryConstraints implements Iterable<CountryConstraint> {
    private final Set<CountryConstraint> countryConstraints;

    public CountryConstraints(Set<CountryConstraint> countryConstraints) {
        this.countryConstraints = ArgumentExceptionHelper.throwIfNullOrEmpty(countryConstraints, "countryConstraints");
    }

    public CountryConstraints(CountryConstraint... countryConstraints) {
        this(Set.of(countryConstraints));
    }

    @Override
    public Iterator<CountryConstraint> iterator() {
        return countryConstraints.iterator();
    }

    public Stream<CountryConstraint> stream() {
        return countryConstraints.stream();
    }

    public static Collector<CountryConstraint, Set<CountryConstraint>, CountryConstraints> collector() {
        return new Collector<>() {

            @Override
            public Supplier<Set<CountryConstraint>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<CountryConstraint>, CountryConstraint> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<CountryConstraint>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<CountryConstraint>, CountryConstraints> finisher() {
                return CountryConstraints::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }
}
