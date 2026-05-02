package no.clueless.opencargo.pricing.breakdown;

import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class PriceComponents implements Iterable<PriceComponent> {
    private final Set<PriceComponent> priceComponents;

    public PriceComponents(Set<PriceComponent> priceComponents) {
        this.priceComponents = ArgumentExceptionHelper.throwIfNullOrEmpty(priceComponents, "priceComponents");
    }

    @Override
    public Iterator<PriceComponent> iterator() {
        return priceComponents.iterator();
    }

    public Stream<PriceComponent> stream() {
        return priceComponents.stream();
    }
}
