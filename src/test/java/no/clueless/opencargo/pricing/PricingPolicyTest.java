package no.clueless.opencargo.pricing;

import no.clueless.opencargo.Address;
import no.clueless.opencargo.Cargo;
import no.clueless.opencargo.PricingQuery;
import no.clueless.opencargo.domain.geography.CountryCode;
import no.clueless.opencargo.domain.geography.CountrySpecification;
import no.clueless.opencargo.domain.geography.PostalCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PricingPolicyTest {
    PricingPolicy servicepakke;

    @BeforeEach
    void setUp() {
        servicepakke = new PricingPolicy(
                "Servicepakke",
                new PricingRules(Set.of(
                        new GeographicPricingRule(new CountrySpecification(new CountryCode("no"), null)),
                        new WeightLimitRule(new BigDecimal("35.0")),
                        new ProductRequirementRule(1)
                )),
                null,
                new BigDecimal("150.0"),
                0
        );
    }

    @Test
    void policy_should_apply_when_cargo_is_within_limits_and_address_is_within_allowed_areas() {
        // arrange
        var cargo                = new Cargo(10.0, 100.0, 50.0, 50.0, 1337.0);
        var destination          = new Address("Foo", "Bar", "Oslo", null, new PostalCode("1111"), new CountryCode("no"));
        var query                = new PricingQuery(cargo, Set.of(1), destination, Currency.getInstance("NOK"));
        var applicabilityReports = servicepakke.resolve(query);

        // act
        var actual = applicabilityReports.isApplicable();

        // assert
        assertTrue(actual, "The policy should apply, but it did not:" + applicabilityReports.getRejections());
    }

    @Test
    void policy_should_not_apply_when_cargo_is_outside_limits() {
        // arrange
        var cargo                = new Cargo(36.0, 100.0, 50.0, 50.0, 1337.0);
        var destination          = new Address("Foo", "Bar", "Oslo", null, new PostalCode("1111"), new CountryCode("no"));
        var query                = new PricingQuery(cargo, Set.of(1), destination, Currency.getInstance("NOK"));
        var applicabilityReports = servicepakke.resolve(query);

        // act
        var actual = applicabilityReports.isApplicable();

        // assert
        assertFalse(actual, "The policy should not apply, but it did:" + applicabilityReports.getRejections());
    }

    @Test
    void policy_should_not_apply_when_destination_is_outside_of_allowed_areas() {
        // arrange
        var cargo                = new Cargo(10.0, 100.0, 50.0, 50.0, 1337.0);
        var destination          = new Address("Foo", "Bar", "Stockholm", null, new PostalCode("1111"), new CountryCode("se"));
        var query                = new PricingQuery(cargo, Set.of(1), destination, Currency.getInstance("NOK"));
        var applicabilityReports = servicepakke.resolve(query);

        // act
        var actual = applicabilityReports.isApplicable();

        // assert
        assertFalse(actual, "The policy should not apply, but it did:" + applicabilityReports.getRejections());
    }
}