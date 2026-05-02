package no.clueless.opencargo;

import no.clueless.opencargo.domain.cargo.Address;
import no.clueless.opencargo.domain.cargo.Cargo;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

import java.util.Objects;

/**
 * A query for applicable products.
 */
public class ProductQuery {
    private final Cargo   cargo;
    private final Address destination;

    public ProductQuery(Cargo cargo, Address destination) {
        this.cargo       = ArgumentExceptionHelper.throwIfNull(cargo, "cargo");
        this.destination = ArgumentExceptionHelper.throwIfNull(destination, "destination");
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Address getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductQuery productQuery = (ProductQuery) o;
        return Objects.equals(cargo, productQuery.cargo) && Objects.equals(destination, productQuery.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cargo, destination);
    }

    @Override
    public String toString() {
        return "Query{" +
                "cargo=" + cargo +
                ", destination=" + destination +
                '}';
    }


}
