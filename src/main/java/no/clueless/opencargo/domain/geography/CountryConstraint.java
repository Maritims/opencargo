package no.clueless.opencargo.domain.geography;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class CountryConstraint {
    private final CountryCode               countryCode;
    private final Set<PostalCodeConstraint> postalCodeConstraints;

    public CountryConstraint(CountryCode countryCode, Set<PostalCodeConstraint> postalCodeConstraints) {
        if (countryCode == null) {
            throw new IllegalArgumentException("countryCode must not be null");
        }
        this.countryCode           = countryCode;
        this.postalCodeConstraints = postalCodeConstraints == null ? Collections.emptySet() : postalCodeConstraints;
    }

    public CountryConstraint(CountryCode countryCode) {
        this(countryCode, Collections.emptySet());
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public Set<PostalCodeConstraint> getPostalCodeConstraints() {
        return postalCodeConstraints;
    }

    /**
     * Checks if the country code and postal code (if present) matches this constraint.
     *
     * @param countryCode The country code to check.
     * @param postalCode  The postal code to check. Can be null.
     * @return True if the country codes match, and the postal code constraints is either empty or matches the given postal code.
     */
    public boolean matches(CountryCode countryCode, PostalCode postalCode) {
        ArgumentExceptionHelper.throwIfNull(countryCode, "countryCode");
        ArgumentExceptionHelper.throwIfNull(postalCode, "postalCode");
        return countryCode.equals(this.countryCode) && (postalCodeConstraints.isEmpty() || postalCodeConstraints.stream().anyMatch(postalCodeConstraint -> postalCodeConstraint.contains(postalCode)));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CountryConstraint that = (CountryConstraint) o;
        return Objects.equals(countryCode, that.countryCode) && Objects.equals(postalCodeConstraints, that.postalCodeConstraints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, postalCodeConstraints);
    }

    @Override
    public String toString() {
        return "CountryConstraint{" +
                "countryCode=" + countryCode +
                ", postalCodeConstraints=" + postalCodeConstraints +
                '}';
    }
}
