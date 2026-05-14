package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.shared.CountryCode;

import java.util.Set;

public class PostalCodeSetConstraint implements Constraint {
    private final CountryCode countryCode;
    private final Set<String> postalCodes;

    public PostalCodeSetConstraint(CountryCode countryCode, Set<String> postalCodes) {
        if (countryCode == null) {
            throw new IllegalArgumentException("countryCode must not be null");
        }
        if (postalCodes == null || postalCodes.isEmpty()) {
            throw new IllegalArgumentException("postalCodes must not be null or empty");
        }
        this.countryCode = countryCode;
        this.postalCodes = postalCodes;
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        var satisfied = postalCodes.contains(parcel.getDestination().getPostalCode());
        return new Decision(getClass().getSimpleName(), satisfied, "Postal code is " + (satisfied ? "" : "not ") + "in the set");
    }

    @Override
    public boolean isSatisfiedBy(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        return parcel.getDestination().getCountryCode().equals(countryCode) &&
                postalCodes.contains(parcel.getDestination().getPostalCode());
    }
}
