package no.clueless.opencargo.geography;

import no.clueless.opencargo.domain.geography.PostalCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostalCodeTest {
    @Test
    void throw_when_value_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new PostalCode(null));
    }

    @Test
    void throw_when_value_is_empty() {
        assertThrows(IllegalArgumentException.class, () -> new PostalCode(""));
    }

    @Test
    void throw_when_value_is_blank() {
        assertThrows(IllegalArgumentException.class, () -> new PostalCode(" "));
    }

    @Test
    void do_not_throw_when_value_is_valid() {
        assertDoesNotThrow(() -> new PostalCode("test"));
    }
}