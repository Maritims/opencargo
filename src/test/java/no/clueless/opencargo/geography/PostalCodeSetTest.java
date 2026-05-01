package no.clueless.opencargo.geography;

import no.clueless.opencargo.domain.geography.PostalCode;
import no.clueless.opencargo.domain.geography.PostalCodeSet;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PostalCodeSetTest {

    @Test
    void throw_when_postalCodes_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new PostalCodeSet(null));
    }

    @Test
    void throw_when_postalCodes_is_empty() {
        assertThrows(IllegalArgumentException.class, () -> new PostalCodeSet(new HashSet<>()));
    }

    @Test
    void throw_when_postalCodes_contain_null_values() {
        Set<PostalCode> postalCodes = new HashSet<>();
        postalCodes.add(new PostalCode("9999"));
        postalCodes.add(null);
        assertThrows(IllegalArgumentException.class, () -> new PostalCodeSet(postalCodes));
    }

    @Test
    void do_not_throw_when_postalCodes_is_valid() {
        Set<PostalCode> postalCodes = new HashSet<>();
        postalCodes.add(new PostalCode("9999"));
        assertDoesNotThrow(() -> new PostalCodeSet(postalCodes));
    }

    @Test
    void contains_should_throw_when_postalCode_is_null() {
        Set<PostalCode> postalCodes = new HashSet<>();
        postalCodes.add(new PostalCode("9999"));
        PostalCodeSet postalCodeSet = new PostalCodeSet(postalCodes);
        assertThrows(IllegalArgumentException.class, () -> postalCodeSet.contains(null));
    }

    @Test
    void contains_should_return_true_when_postalCode_is_in_the_set() {
        Set<PostalCode> postalCodes = new HashSet<>();
        postalCodes.add(new PostalCode("9998"));
        postalCodes.add(new PostalCode("9999"));

        assertTrue(new PostalCodeSet(postalCodes).contains(new PostalCode("9999")));
    }

    @Test
    void contains_should_return_false_when_postalCode_is_not_in_the_set() {
        Set<PostalCode> postalCodes = new HashSet<>();
        postalCodes.add(new PostalCode("9999"));

        assertFalse(new PostalCodeSet(postalCodes).contains(new PostalCode("9998")));
    }
}