package no.clueless.opencargo.domain.geography;

import java.util.Objects;

public final class PostalCode implements Comparable<PostalCode> {
    private final String value;

    public PostalCode(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty");
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
    public int compareTo(PostalCode other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }
        return  value.compareTo(other.value);
    }
}
