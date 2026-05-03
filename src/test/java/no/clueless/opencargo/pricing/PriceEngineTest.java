package no.clueless.opencargo.pricing;

import no.clueless.opencargo.PricingQuery;
import no.clueless.opencargo.bindings.PricingPolicyListDTO;
import no.clueless.opencargo.domain.cargo.Address;
import no.clueless.opencargo.domain.cargo.Cargo;
import no.clueless.opencargo.domain.geography.CountryCode;
import no.clueless.opencargo.domain.geography.PostalCode;
import no.clueless.opencargo.infrastructure.xml.XmlMarshaller;
import no.clueless.opencargo.pricing.breakdown.PriceBreakdown;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PriceEngineTest {
    PriceEngine sut;

    @BeforeEach
    void setUp() {
        var dto      = XmlMarshaller.unmarshalResourceSilently("pricing.xml", PricingPolicyListDTO.class);
        var policies = PricingPolicies.from(dto);
        sut = new PriceEngine(policies);
    }

    @Test
    void calculate_should_return_price_for_cargo() {
        var cargo       = new Cargo(35.0, 100.0, 50.0, 50.0, 1337.0);
        var productIds  = Set.of(1, 2, 3);
        var destination = new Address("Foo", "Bar", "Oslo", null, new PostalCode("9170"), new CountryCode("no"));
        var currency    = Currency.getInstance("NOK");
        var query       = new PricingQuery(cargo, productIds, destination, currency);

        var actual = sut.calculate(query)
                .map(PriceBreakdown::getTotalPrice)
                .orElse(null);

        assertNotNull(actual);
        assertEquals(new BigDecimal("135.0"), actual);
    }

    @Test
    void calculate_should_return_null_when_no_policy_matches() {
        var cargo       = new Cargo(35.0, 100.0, 50.0, 50.0, 1337.0);
        var productIds  = Set.of(1, 2, 3);
        var destination = new Address("Foo", "Bar", "Oslo", null, new PostalCode("9170"), new CountryCode("se"));
        var currency    = Currency.getInstance("NOK");
        var query       = new PricingQuery(cargo, productIds, destination, currency);

        var actual = sut.calculate(query).orElse(null);

        assertNull(actual);
    }
}