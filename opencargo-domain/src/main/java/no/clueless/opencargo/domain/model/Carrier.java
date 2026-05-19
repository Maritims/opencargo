package no.clueless.opencargo.domain.model;

import java.util.Objects;
import java.util.Set;

public class Carrier {
    private final CarrierId           id;
    private final String              name;
    private final Set<FreightProduct> freightProducts;

    public Carrier(CarrierId id, String name, Set<FreightProduct> freightProducts) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        if (freightProducts == null || freightProducts.isEmpty()) {
            throw new IllegalArgumentException("freightProducts cannot be null or empty");
        }
        this.id              = Objects.requireNonNull(id, "id cannot be null");
        this.name            = name;
        this.freightProducts = freightProducts;
    }

    public CarrierId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<FreightProduct> getFreightProducts() {
        return freightProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Carrier carrier = (Carrier) o;
        return Objects.equals(id, carrier.id) && Objects.equals(name, carrier.name) && Objects.equals(freightProducts, carrier.freightProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, freightProducts);
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
