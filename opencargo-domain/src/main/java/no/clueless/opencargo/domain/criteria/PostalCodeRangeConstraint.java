package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.shared.CountryCode;

public class PostalCodeRangeConstraint implements Constraint {
    private final CountryCode countryCode;
    private final String      fromPostalCode;
    private final String      toPostalCode;

    public PostalCodeRangeConstraint(CountryCode countryCode, String fromPostalCode, String toPostalCode) {
        if (countryCode == null) {
            throw new IllegalArgumentException("countryCode must not be null");
        }
        if (fromPostalCode == null || fromPostalCode.isBlank()) {
            throw new IllegalArgumentException("fromPostalCode must not be null or empty");
        }
        if (toPostalCode == null || toPostalCode.isBlank()) {
            throw new IllegalArgumentException("toPostalCode must not be null or empty");
        }
        this.countryCode    = countryCode;
        this.fromPostalCode = fromPostalCode;
        this.toPostalCode   = toPostalCode;
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        var satisfied = parcel.getDestination().getCountryCode().equals(countryCode) &&
                parcel.getDestination().getPostalCode().compareTo(fromPostalCode) >= 0 &&
                parcel.getDestination().getPostalCode().compareTo(toPostalCode) <= 0;
        return new Decision(
                getClass().getSimpleName(),
                satisfied,
                satisfied ? String.format("Postal code %s is within allowed range of %s to %s", parcel.getDestination().getPostalCode(), fromPostalCode, toPostalCode)
                        : String.format("Postal code %s is outside allowed range of %s to %s", parcel.getDestination().getPostalCode(), fromPostalCode, toPostalCode)
        );
    }
}
