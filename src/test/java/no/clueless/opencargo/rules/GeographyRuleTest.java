package no.clueless.opencargo.rules;

import no.clueless.opencargo.Address;
import no.clueless.opencargo.Cargo;
import no.clueless.opencargo.Query;
import no.clueless.opencargo.domain.geography.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GeographyRuleTest {

    @Test
    void throw_when_countryConstraints_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new GeographyRule(1, null, null, "foo", "bar", 0, false, null));
    }

    @Test
    void throw_when_countryConstraints_is_empty() {
        assertThrows(IllegalArgumentException.class, () -> new GeographyRule(1, null, null, "foo", "bar", 0, false, null));
    }

    @Test
    void do_not_throw_when_countryConstraints_is_not_null_nor_empty() {
        Set<PostalCodeConstraint> postalCodeConstraints = new HashSet<>();
        var                       countryConstraints    = new CountryConstraints(new CountryConstraint(new CountryCode("no"), postalCodeConstraints));

        assertDoesNotThrow(() -> new GeographyRule(1, null, null, "foo", "bar", 0, false, countryConstraints));
    }

    @Test
    void isSatisfiedBy_should_throw_when_query_is_null() {
        var           countryConstraints = new CountryConstraints(new CountryConstraint(new CountryCode("no"), new HashSet<>()));
        GeographyRule rule               = new GeographyRule(1, null, null, "foo", "bar", 0, false, countryConstraints);

        assertThrows(IllegalArgumentException.class, () -> rule.evaluate(null));
    }

    @Test
    void evaluate_should_return_true_when_any_countryConstraint_is_matched() {
        // arrange
        var           countryConstraints = new CountryConstraints(new CountryConstraint(new CountryCode("no"), new HashSet<>()));
        GeographyRule rule               = new GeographyRule(1, null, null, "foo", "bar", 0, false, countryConstraints);
        Cargo         cargo              = new Cargo(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("4.0"), new BigDecimal("5.0"));
        Address       destination        = new Address("foo", "bar", "baz", "lorem", new PostalCode("ipsum"), new CountryCode("no"));
        Query         query              = new Query(cargo, destination);

        // act
        EvaluationResult result = rule.evaluate(query);

        // assert
        assertTrue(result.isSatisfied(), result.getReason());
    }
}