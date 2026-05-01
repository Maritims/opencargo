package no.clueless.opencargo;

import no.clueless.opencargo.domain.geography.CountryCode;
import no.clueless.opencargo.domain.geography.PostalCode;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;

@SuppressWarnings("unused")
public final class Address {
    private final String      addressLine1;
    private final String      addressLine2;
    private final String      city;
    private final String      state;
    private final PostalCode  postalCode;
    private final CountryCode countryCode;

    public Address(String addressLine1, String addressLine2, String city, String state, PostalCode postalCode, CountryCode countryCode) {
        this.addressLine1 = ArgumentExceptionHelper.throwIfNullOrBlank(addressLine1, "addressLine1");
        this.addressLine2 = addressLine2;
        this.city         = ArgumentExceptionHelper.throwIfNullOrBlank(city, "city");
        this.state        = state;
        this.postalCode   = ArgumentExceptionHelper.throwIfNull(postalCode, "postalCode");
        this.countryCode  = ArgumentExceptionHelper.throwIfNull(countryCode, "countryCode");
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addressLine1, address.addressLine1) && Objects.equals(addressLine2, address.addressLine2) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(postalCode, address.postalCode) && Objects.equals(countryCode, address.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressLine1, addressLine2, city, state, postalCode, countryCode);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode=" + postalCode +
                ", countryCode=" + countryCode +
                '}';
    }
}
