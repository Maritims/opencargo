package no.clueless.opencargo.pricing.domain.model;

import no.clueless.opencargo.pricing.port.in.RequestPricingCommand;
import no.clueless.opencargo.domain.model.Cargo;
import no.clueless.opencargo.domain.model.geography.Address;

import java.util.Currency;
import java.util.Set;

public class PricingQuery extends RequestPricingCommand {

    public PricingQuery(Cargo cargo, Set<Integer> productIds, Address destination, Currency currency) {
        super(cargo, productIds, destination, currency);
    }
}
