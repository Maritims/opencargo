package no.clueless.opencargo.domain.model;

import java.util.Objects;

public class FreightProductId {
    private final String value;

    public FreightProductId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("value cannot be null or blank");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FreightProductId that = (FreightProductId) o;
        return Objects.equals(value, that.value);
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
