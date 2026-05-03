package no.clueless.opencargo.pricing.breakdown;

import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.Population;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PriceBreakdown {
    private final Population<PriceComponent, Set<PriceComponent>> priceComponents;
    private final Currency                                        currency;

    public PriceBreakdown(Population<PriceComponent, Set<PriceComponent>> priceComponents, Currency currency) {
        this.priceComponents = ArgumentExceptionHelper.throwIfNull(priceComponents, "priceComponents");
        this.currency        = ArgumentExceptionHelper.throwIfNull(currency, "currency");
    }

    public Population<PriceComponent, Set<PriceComponent>> getPriceComponents() {
        return priceComponents;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getTotalPrice() {
        return priceComponents.stream()
                .map(PriceComponent::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PriceBreakdown that = (PriceBreakdown) o;
        return Objects.equals(priceComponents, that.priceComponents) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceComponents, currency);
    }

    @Override
    public String toString() {
        return "PriceBreakdown{" +
                "priceComponents=" + priceComponents +
                ", currency=" + currency +
                '}';
    }

    public static Collector<PriceComponent, Set<PriceComponent>, PriceBreakdown> collector(Currency currency) {
        ArgumentExceptionHelper.throwIfNull(currency, "currency");

        return new Collector<>() {

            @Override
            public Supplier<Set<PriceComponent>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<PriceComponent>, PriceComponent> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<PriceComponent>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<PriceComponent>, PriceBreakdown> finisher() {
                return (set) -> new PriceBreakdown(Population.fromSetOf(set), currency);
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }
}
