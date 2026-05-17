package no.clueless.opencargo.domain.shared;

import java.util.Objects;

public class PostalCode implements Comparable<PostalCode> {
    private final String value;

    public PostalCode(String value) {
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
        PostalCode that = (PostalCode) o;
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

    @Override
    public int compareTo(PostalCode o) {
        return value.compareTo(o.value);
    }
}
