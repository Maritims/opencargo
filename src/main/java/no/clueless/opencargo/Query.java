package no.clueless.opencargo;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;

/**
 * A query for applicable products.
 */
public class Query {
    private final Cargo   cargo;
    private final Address destination;

    public Query(Cargo cargo, Address destination) {
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
        Query query = (Query) o;
        return Objects.equals(cargo, query.cargo) && Objects.equals(destination, query.destination);
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
