package no.clueless.opencargo.pricing;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class PriceModifiers implements Iterable<PriceModifier> {
    private final Set<PriceModifier> priceModifiers;

    public PriceModifiers(Set<PriceModifier> priceModifiers) {
        this.priceModifiers = ArgumentExceptionHelper.throwIfNullOrEmpty(priceModifiers, "priceModifiers");
    }

    @Override
    public Iterator<PriceModifier> iterator() {
        return priceModifiers.iterator();
    }

    public Stream<PriceModifier> stream() {
        return priceModifiers.stream();
    }
}
