package no.clueless.opencargo.geography;

import no.clueless.opencargo.domain.geography.PostalCode;
import no.clueless.opencargo.domain.geography.PostalCodeRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostalCodeRangeTest {

    @Test
    void throw_when_start_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new PostalCodeRange(null, new PostalCode("9999")));
    }

    @Test
    void throw_when_end_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new PostalCodeRange(new PostalCode("9999"), null));
    }

    @Test
    void throw_when_start_is_greater_than_end() {
        assertThrows(IllegalArgumentException.class, () -> new PostalCodeRange(new PostalCode("9999"), new PostalCode("9000")));
    }

    @Test
    void do_not_throw_when_start_and_end_are_equal() {
        assertDoesNotThrow(() -> new PostalCodeRange(new PostalCode("9999"), new PostalCode("9999")));
    }

    @Test
    void do_not_throw_when_start_is_less_than_end() {
        assertDoesNotThrow(() -> new PostalCodeRange(new PostalCode("9000"), new PostalCode("9999")));
    }

    @Test
    void contains_should_throw_when_postalCode_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new PostalCodeRange(null, new PostalCode("9999")).contains(null));
    }

    @Test
    void contains_should_return_true_when_checking_if_start_is_contained() {
        assertTrue(new PostalCodeRange(new PostalCode("9000"), new PostalCode("9999")).contains(new PostalCode("9000")));
    }

    @Test
    void contains_should_return_true_when_checking_if_end_is_contained() {
        assertTrue(new PostalCodeRange(new PostalCode("9000"), new PostalCode("9999")).contains(new PostalCode("9999")));
    }

    @Test
    void contains_should_return_true_when_checking_if_a_postal_code_between_start_and_end_is_contained() {
        assertTrue(new PostalCodeRange(new PostalCode("9000"), new PostalCode("9999")).contains(new PostalCode("9500")));
    }

    @Test
    void contains_should_return_false_when_checking_for_a_postal_code_not_in_the_range() {
        assertFalse(new PostalCodeRange(new PostalCode("9000"), new PostalCode("9999")).contains(new PostalCode("1000")));
    }
}