package no.clueless.opencargo.domain.geography;

import java.util.Objects;
import java.util.regex.Pattern;

public final class CountryCode {
    private static final Pattern ISO_3166_1_ALPHA_2_PATTERN = Pattern.compile("^[A-Z]{2}$");
    private final        String  value;

    public CountryCode(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("value must not be null or empty");
        }
        if (!ISO_3166_1_ALPHA_2_PATTERN.matcher(value.toUpperCase()).matches()) {
            throw new IllegalArgumentException("value must match ISO 3166-1 alpha 2 pattern");
        }
        this.value = value.toUpperCase();
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
