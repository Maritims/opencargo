package no.clueless.opencargo.domain.model;

import java.util.Objects;

public class CarrierId {
    private final String value;

    public CarrierId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("value cannot be null or blank");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CarrierId carrierId = (CarrierId) o;
        return Objects.equals(value, carrierId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
