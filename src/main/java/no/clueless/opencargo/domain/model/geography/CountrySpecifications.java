package no.clueless.opencargo.domain.model.geography;

import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CountrySpecifications implements Iterable<CountrySpecification> {
    private final Set<CountrySpecification> countrySpecifications;

    public CountrySpecifications(Set<CountrySpecification> countrySpecifications) {
        this.countrySpecifications = ArgumentExceptionHelper.throwIfNullOrEmpty(countrySpecifications, "countryConstraints");
    }

    public CountrySpecifications(CountrySpecification... countrySpecifications) {
        this(Set.of(countrySpecifications));
    }

    @Override
    public Iterator<CountrySpecification> iterator() {
        return countrySpecifications.iterator();
    }

    public Stream<CountrySpecification> stream() {
        return countrySpecifications.stream();
    }

    public static Collector<CountrySpecification, Set<CountrySpecification>, CountrySpecifications> collector() {
        return new Collector<>() {

            @Override
            public Supplier<Set<CountrySpecification>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<CountrySpecification>, CountrySpecification> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<CountrySpecification>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<CountrySpecification>, CountrySpecifications> finisher() {
                return CountrySpecifications::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }
}
