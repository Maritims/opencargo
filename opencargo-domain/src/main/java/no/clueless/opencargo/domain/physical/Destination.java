package no.clueless.opencargo.domain.physical;

import no.clueless.opencargo.domain.shared.CountryCode;

public class Destination {
    private final CountryCode countryCode;
    private final String      postalCode;

    public Destination(CountryCode countryCode, String postalCode) {
        if (countryCode == null) {
            throw new IllegalArgumentException("countryCode must not be null");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("postalCode must not be null or empty");
        }
        this.countryCode = countryCode;
        this.postalCode  = postalCode;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
