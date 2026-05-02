package no.clueless.opencargo.geography;

import no.clueless.opencargo.domain.geography.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CountrySpecificationTest {

    @Test
    void throw_when_countryCode_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new CountrySpecification(null, null));
    }

    @Test
    void do_not_throw_when_postalCodeConstraints_is_null() {
        assertDoesNotThrow(() -> new CountrySpecification(new CountryCode("no"), null));
    }

    @Test
    void do_not_throw_when_postalCodeConstraints_is_empty() {
        assertDoesNotThrow(() -> new CountrySpecification(new CountryCode("no"), new HashSet<>()));
    }

    @Test
    void matches_should_throw_when_countryCode_is_null() {
        CountrySpecification countrySpecification = new CountrySpecification(new CountryCode("no"), null);
        assertThrows(IllegalArgumentException.class, () -> countrySpecification.matches(null, null));
    }

    @Test
    void matches_should_throw_when_postalCode_is_null() {
        CountrySpecification countrySpecification = new CountrySpecification(new CountryCode("no"), null);
        assertThrows(IllegalArgumentException.class, () -> countrySpecification.matches(new CountryCode("no"), null));
    }

    @Test
    void matches_should_return_false_when_countryCode_does_not_match() {
        CountrySpecification countrySpecification = new CountrySpecification(new CountryCode("no"), new HashSet<>());
        assertFalse(countrySpecification.matches(new CountryCode("se"), new PostalCode("9999")));
    }

    @Test
    void matches_should_return_true_when_countryCode_matches_and_postalCodeConstraints_is_empty() {
        CountrySpecification countrySpecification = new CountrySpecification(new CountryCode("no"), new HashSet<>());
        assertTrue(countrySpecification.matches(new CountryCode("no"), new PostalCode("9999")));
    }

    @Test
    void matches_should_return_true_when_countryCode_matches_and_postalCode_is_within_postalCodeConstraints() {
        Set<PostalCode> postalCodes = new HashSet<>();
        postalCodes.add(new PostalCode("9999"));

        var postalCodeSet            = new PostalCodes(postalCodes);
        var postalCodeSpecifications = new HashSet<PostalCodeSpecification>();
        postalCodeSpecifications.add(postalCodeSet);

        CountrySpecification countrySpecification = new CountrySpecification(new CountryCode("no"), postalCodeSpecifications);
        assertTrue(countrySpecification.matches(new CountryCode("no"), new PostalCode("9999")));
    }

    @Test
    void matches_should_return_false_when_countryCode_matches_and_postalCode_is_not_within_postalCodeContactConstraints() {
        Set<PostalCode> postalCodes = new HashSet<>();
        postalCodes.add(new PostalCode("9999"));

        var postalCodeSet            = new PostalCodes(postalCodes);
        var postalCodeSpecifications = new HashSet<PostalCodeSpecification>();
        postalCodeSpecifications.add(postalCodeSet);

        CountrySpecification countrySpecification = new CountrySpecification(new CountryCode("no"), postalCodeSpecifications);
        assertFalse(countrySpecification.matches(new CountryCode("no"), new PostalCode("9998")));
    }
}