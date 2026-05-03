package no.clueless.opencargo.product_selection;

import no.clueless.opencargo.catalog.Address;
import no.clueless.opencargo.catalog.Cargo;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Objects;

/**
 * A query for applicable products.
 */
public class ProductSelectionQuery {
    private final Cargo   cargo;
    private final Address destination;

    public ProductSelectionQuery(Cargo cargo, Address destination) {
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
        ProductSelectionQuery productQuery = (ProductSelectionQuery) o;
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
