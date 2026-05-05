package no.clueless.opencargo.pricing.domain.service.engine;

import no.clueless.opencargo.domain.model.geography.Address;
import no.clueless.opencargo.domain.model.Cargo;
import no.clueless.opencargo.pricing.domain.model.PricingQuery;
import no.clueless.opencargo.domain.model.geography.CountryCode;
import no.clueless.opencargo.domain.model.geography.PostalCode;
import no.clueless.opencargo.pricing.domain.service.RequestPricingService;
import no.clueless.opencargo.pricing.port.out.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PriceEngineTest {
    PolicyRepository      policyRepository;
    RequestPricingService sut;

    @BeforeEach
    void setUp() {
        policyRepository = PolicyRepository.create();
        sut              = new RequestPricingService(policyRepository);
    }

    @Test
    void calculate_should_return_price_for_cargo() {
        var cargo       = new Cargo(35.0, 100.0, 50.0, 50.0, 1337.0);
        var productIds  = Set.of(1, 2, 3);
        var destination = new Address("Foo", "Bar", "Oslo", null, new PostalCode("9170"), new CountryCode("no"));
        var currency    = Currency.getInstance("NOK");
        var query       = new PricingQuery(cargo, productIds, destination, currency);

        var actual = sut.requestPricing(query).getTotalPrice();

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

        var actual = sut.requestPricing(query);

        assertNull(actual);
    }
}