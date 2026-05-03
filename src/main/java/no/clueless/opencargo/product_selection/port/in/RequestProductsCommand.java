package no.clueless.opencargo.product_selection.port.in;

import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.domain.model.Cargo;
import no.clueless.opencargo.domain.model.geography.Address;

import java.util.Objects;

public class RequestProductsCommand {
    private final Cargo   cargo;
    private final Address destination;

    public RequestProductsCommand(Cargo cargo, Address destination) {
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
