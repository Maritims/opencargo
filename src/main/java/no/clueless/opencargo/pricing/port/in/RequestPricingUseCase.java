package no.clueless.opencargo.pricing.port.in;

import no.clueless.opencargo.pricing.domain.model.breakdown.PriceBreakdown;
import no.clueless.opencargo.pricing.domain.service.RequestPricingService;

public interface RequestPricingUseCase {
    PriceBreakdown requestPricing(RequestPricingCommand command);

    static RequestPricingUseCase create() {
        return RequestPricingService.getInstance();
    }
}
