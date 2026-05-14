package no.clueless.opencargo.domain.shared;

import java.util.Objects;
import java.util.regex.Pattern;

public class CountryCode {
    private static final Pattern ISO_3166_1_ALPHA_2_PATTERN = Pattern.compile("^[A-Z]{2}$");
    private final        String  value;

    public CountryCode(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        var normalized = value.trim().toUpperCase();

        if (!ISO_3166_1_ALPHA_2_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Invalid country code: " + value + ". Must follow ISO 3166-1 alpha-2 (e.g., NO, DE).");
        }

        this.value = normalized;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CountryCode that = (CountryCode) o;
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
