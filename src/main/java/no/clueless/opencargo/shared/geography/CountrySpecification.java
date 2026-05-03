package no.clueless.opencargo.shared.geography;

import no.clueless.opencargo.bindings.CountrySpecificationDTO;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class CountrySpecification {
    private final CountryCode                  countryCode;
    private final Set<PostalCodeSpecification> postalCodeSpecifications;

    public CountrySpecification(CountryCode countryCode, Set<PostalCodeSpecification> postalCodeSpecifications) {
        if (countryCode == null) {
            throw new IllegalArgumentException("countryCode must not be null");
        }
        this.countryCode              = countryCode;
        this.postalCodeSpecifications = postalCodeSpecifications == null ? Collections.emptySet() : postalCodeSpecifications;
    }

    public CountrySpecification(CountryCode countryCode) {
        this(countryCode, Collections.emptySet());
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public Set<PostalCodeSpecification> getPostalCodeConstraints() {
        return postalCodeSpecifications;
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
        return countryCode.equals(this.countryCode) && (postalCodeSpecifications.isEmpty() || postalCodeSpecifications.stream().anyMatch(postalCodeConstraint -> postalCodeConstraint.contains(postalCode)));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CountrySpecification that = (CountrySpecification) o;
        return Objects.equals(countryCode, that.countryCode) && Objects.equals(postalCodeSpecifications, that.postalCodeSpecifications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, postalCodeSpecifications);
    }

    @Override
    public String toString() {
        return "CountryConstraint{" +
                "countryCode=" + countryCode +
                ", postalCodeConstraints=" + postalCodeSpecifications +
                '}';
    }

    public static CountrySpecification from(CountrySpecificationDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        return new CountrySpecification(new CountryCode(dto.getCountryCode()), dto.getPostalCodeSpecification()
                .stream()
                .map(PostalCodeSpecification::from)
                .collect(Collectors.toSet()));
    }
}
