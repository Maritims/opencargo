package no.clueless.opencargo.pricing.port.in;

import no.clueless.opencargo.pricing.domain.model.breakdown.PriceBreakdown;

public interface RequestPricingUseCase {
    PriceBreakdown requestPricing(RequestPricingCommand command);
}
