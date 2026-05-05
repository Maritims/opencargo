package no.clueless.opencargo.product_selection.port.in;

import no.clueless.opencargo.domain.model.Cargo;
import no.clueless.opencargo.domain.model.geography.Address;

import java.util.Objects;

/**
 * An immutable command object for requesting products.
 * <p>
 * All fields are required. This object ensures the domain logic receives a valid set of parameters for product selection.
 */
public class RequestProductsCommand {
    private final Cargo   cargo;
    private final Address destination;

    public RequestProductsCommand(Cargo cargo, Address destination) {
        if(cargo == null) {
            throw new IllegalArgumentException("Cargo must not be null");
        }
        if(destination == null) {
            throw new IllegalArgumentException("Destination must not be null");
        }
        this.cargo       = cargo;
        this.destination = destination;
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
        RequestProductsCommand that = (RequestProductsCommand) o;
        return Objects.equals(cargo, that.cargo) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cargo, destination);
    }

    @Override
    public String toString() {
        return "RequestProductsCommand{" +
                "cargo=" + cargo +
                ", destination=" + destination +
                '}';
    }
}
