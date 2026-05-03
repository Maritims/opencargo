package no.clueless.opencargo.shared;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * A population is a collection of objects which is guaranteed to not be empty.
 *
 * @param <T> The type of objects in the population
 * @param <C> The type of collection the population is backed by.
 */
public class Population<T, C extends Collection<T>> implements Iterable<T> {
    private final C collection;

    protected Population(C collection) {
        this.collection = ArgumentExceptionHelper.throwIfNull(collection, "collection");
    }

    public int size() {
        return collection.size();
    }

    @Override
    public Iterator<T> iterator() {
        return collection.iterator();
    }

    public Stream<T> stream() {
        return collection.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Population<?, ?> that = (Population<?, ?>) o;
        return Objects.equals(collection, that.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(collection);
    }

    @Override
    public String toString() {
        return "Population{" +
                "collection=" + collection +
                '}';
    }

    public static <T, C extends Collection<T>> Collector<T, C, Population<T, C>> collector(Supplier<C> collectionSupplier) {
        return new Collector<>() {
            @Override
            public Supplier<C> supplier() {
                return collectionSupplier;
            }

            @Override
            public BiConsumer<C, T> accumulator() {
                return C::add;
            }

            @Override
            public BinaryOperator<C> combiner() {
                return (c1, c2) -> {
                    c1.addAll(c2);
                    return c1;
                };
            }

            @Override
            public Function<C, Population<T, C>> finisher() {
                return Population::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }

    public static <T> Population<T, Set<T>> fromOneOf(T element) {
        ArgumentExceptionHelper.throwIfNull(element, "element");
        return new Population<>(Set.of(element));
    }

    public static <T> Population<T, Set<T>> fromSetOf(Set<T> elements) {
        ArgumentExceptionHelper.throwIfNullOrEmpty(elements, "elements");
        return new Population<>(elements);
    }
}
