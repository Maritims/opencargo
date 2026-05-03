package no.clueless.opencargo.catalog;

import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.Population;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Products extends Population<Product, Set<Product>> {
    private final Map<String, Product> productsByNumber;

    public Products(Set<Product> products) {
        super(products);
        this.productsByNumber = ArgumentExceptionHelper.throwIfNullOrEmpty(products, "products")
                .stream()
                .collect(Collectors.toMap(Product::getNumber, product -> product));
    }

    public Products(Product... products) {
        this(Set.of(products));
    }

    public Optional<Product> findByNumber(String number) {
        return Optional.ofNullable(productsByNumber.get(ArgumentExceptionHelper.throwIfNull(number, "number")));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return Objects.equals(productsByNumber, products.productsByNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productsByNumber);
    }

    @Override
    public String toString() {
        return "Products{" +
                "productsByNumber=" + productsByNumber +
                '}';
    }

    public static Collector<Product, Set<Product>, Products> collector() {
        return new Collector<>() {
            @Override
            public Supplier<Set<Product>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<Product>, Product> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<Product>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<Product>, Products> finisher() {
                return Products::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }
}
