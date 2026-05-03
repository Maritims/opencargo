package no.clueless.opencargo.product_selection;

import no.clueless.opencargo.shared.geography.*;
import no.clueless.opencargo.shared.geography.Address;
import no.clueless.opencargo.shared.cargo.Cargo;
import no.clueless.opencargo.shared.applicability.EvaluationResult;
import no.clueless.opencargo.product_selection.engine.GeographyRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GeographyRuleTest {

    @Test
    void throw_when_countryConstraints_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new GeographyRule(mock(), null));
    }

    @Test
    void do_not_throw_when_countryConstraints_is_not_null() {
        Set<PostalCodeSpecification> postalCodeSpecifications = new HashSet<>();
        var                          countryConstraints       = new CountrySpecifications(new CountrySpecification(new CountryCode("no"), postalCodeSpecifications));

        assertDoesNotThrow(() -> new GeographyRule(mock(), countryConstraints));
    }

    @Test
    void isSatisfiedBy_should_throw_when_query_is_null() {
        var           countryConstraints = new CountrySpecifications(new CountrySpecification(new CountryCode("no"), new HashSet<>()));
        GeographyRule rule               = new GeographyRule(mock(), countryConstraints);

        assertThrows(IllegalArgumentException.class, () -> rule.evaluate(null));
    }

    @Test
    void evaluate_should_return_true_when_any_countryConstraint_is_matched() {
        // arrange
        var           countryConstraints = new CountrySpecifications(new CountrySpecification(new CountryCode("no"), new HashSet<>()));
        GeographyRule rule               = new GeographyRule(mock(), countryConstraints);
        Cargo         cargo              = new Cargo(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("4.0"), new BigDecimal("5.0"));
        Address               destination           = new Address("foo", "bar", "baz", "lorem", new PostalCode("ipsum"), new CountryCode("no"));
        ProductSelectionQuery productSelectionQuery = new ProductSelectionQuery(cargo, destination);

        // act
        EvaluationResult result = rule.evaluate(productSelectionQuery);

        // assert
        assertTrue(result.isSatisfied(), result.getReason());
    }
}