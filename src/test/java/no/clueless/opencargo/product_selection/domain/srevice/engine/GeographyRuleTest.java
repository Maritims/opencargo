package no.clueless.opencargo.product_selection.domain.srevice.engine;

import no.clueless.opencargo.domain.model.geography.*;
import no.clueless.opencargo.product_selection.domain.service.engine.ProductSelectionQuery;
import no.clueless.opencargo.domain.model.Cargo;
import no.clueless.opencargo.product_selection.domain.service.engine.GeographyRule;
import no.clueless.opencargo.shared.Population;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GeographyRuleTest {

    @Test
    void throw_when_countryConstraints_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new GeographyRule(mock(), null));
    }

    @Test
    void do_not_throw_when_countryConstraints_is_not_null() {
        var postalCodeSpecifications = new HashSet<PostalCodeSpecification>();
        var countrySpecifications    = Population.fromOneOf(new CountrySpecification(new CountryCode("no"), postalCodeSpecifications));

        assertDoesNotThrow(() -> new GeographyRule(mock(), countrySpecifications));
    }

    @Test
    void isSatisfiedBy_should_throw_when_query_is_null() {
        var countryConstraints = Population.fromOneOf(new CountrySpecification(new CountryCode("no"), new HashSet<>()));
        var rule               = new GeographyRule(mock(), countryConstraints);

        assertThrows(IllegalArgumentException.class, () -> rule.evaluate(null));
    }

    @Test
    void evaluate_should_return_true_when_any_countryConstraint_is_matched() {
        // arrange
        var countryConstraints    = Population.fromOneOf(new CountrySpecification(new CountryCode("no"), new HashSet<>()));
        var rule                  = new GeographyRule(mock(), countryConstraints);
        var cargo                 = new Cargo(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("4.0"), new BigDecimal("5.0"));
        var destination           = new Address("foo", "bar", "baz", "lorem", new PostalCode("ipsum"), new CountryCode("no"));
        var productSelectionQuery = new ProductSelectionQuery(cargo, destination);

        // act
        var result = rule.evaluate(productSelectionQuery);

        // assert
        assertTrue(result.isSatisfied(), result.getReason());
    }
}