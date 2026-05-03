package no.clueless.opencargo.domain.model.geography;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CountryCodeTest {
    @Test
    void throw_when_country_code_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new CountryCode(null));
    }

    @Test
    void throw_when_country_code_is_empty() {
        assertThrows(IllegalArgumentException.class, () -> new CountryCode(""));
    }

    @Test
    void throw_when_country_is_blank() {
        assertThrows(IllegalArgumentException.class, () -> new CountryCode(" "));
    }

    @Test
    void do_not_throw_when_country_code_is_lowercase() {
        assertDoesNotThrow(() -> new CountryCode("no"));
    }

    @Test
    void do_not_throw_when_country_code_is_uppercase() {
        assertDoesNotThrow(() -> new CountryCode("NO"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"af", "al", "de"})
    void do_not_throw_when_country_code_is_valid(String countryCode) {
        assertDoesNotThrow(() -> new CountryCode(countryCode));
    }
}