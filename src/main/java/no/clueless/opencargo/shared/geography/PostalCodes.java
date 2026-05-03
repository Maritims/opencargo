package no.clueless.opencargo.shared.geography;

import no.clueless.opencargo.bindings.PostalCodeSetSpecificationDTO;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PostalCodes implements Iterable<PostalCode>, PostalCodeSpecification {
    private final Set<PostalCode> postalCodes;

    public PostalCodes(Set<PostalCode> postalCodes) {
        this.postalCodes = ArgumentExceptionHelper.throwIfNullOrEmpty(postalCodes, "postalCodes");
        if (this.postalCodes.contains(null)) {
            throw new IllegalArgumentException("postalCodes cannot contain null values");
        }
    }

    public boolean contains(PostalCode postalCode) {
        return postalCodes.contains(ArgumentExceptionHelper.throwIfNull(postalCode, "postalCode"));
    }

    @Override
    public Iterator<PostalCode> iterator() {
        return postalCodes.iterator();
    }

    public static Collector<String, Set<PostalCode>, PostalCodes> collector() {
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
            public Function<Set<PostalCode>, PostalCodes> finisher() {
                return PostalCodes::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }

    public static PostalCodes from(PostalCodeSetSpecificationDTO dto) {
        return ArgumentExceptionHelper.throwIfNull(dto, "dto")
                .getPostalCode()
                .stream()
                .collect(PostalCodes.collector());
    }
}
