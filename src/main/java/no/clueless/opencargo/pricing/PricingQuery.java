package no.clueless.opencargo.pricing;

import no.clueless.opencargo.catalog.Address;
import no.clueless.opencargo.catalog.Cargo;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Currency;
import java.util.Objects;
import java.util.Set;

public class PricingQuery {
    private final Cargo        cargo;
    private final Set<Integer> productIds;
    private final Address      destination;
    private final Currency     currency;

    public PricingQuery(Cargo cargo, Set<Integer> productIds, Address destination, Currency currency) {
        this.cargo       = ArgumentExceptionHelper.throwIfNull(cargo, "cargo");
        this.productIds  = ArgumentExceptionHelper.throwIfNullOrEmpty(productIds, "products");
        this.destination = ArgumentExceptionHelper.throwIfNull(destination, "destination");
        this.currency    = ArgumentExceptionHelper.throwIfNull(currency, "currency");
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Set<Integer> getProductIds() {
        return productIds;
    }

    public Address getDestination() {
        return destination;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PricingQuery that = (PricingQuery) o;
        return Objects.equals(cargo, that.cargo) && Objects.equals(productIds, that.productIds) && Objects.equals(destination, that.destination) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cargo, productIds, destination, currency);
    }

    @Override
    public String toString() {
        return "PricingQuery{" +
                "cargo=" + cargo +
                ", productIds=" + productIds +
                ", destination=" + destination +
                ", currency=" + currency +
                '}';
    }
}
