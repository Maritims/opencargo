package no.clueless.opencargo.pricing.domain.service;

import no.clueless.opencargo.pricing.domain.model.breakdown.PriceBreakdown;
import no.clueless.opencargo.pricing.domain.service.engine.PriceEngine;
import no.clueless.opencargo.pricing.domain.service.engine.PricingQuery;
import no.clueless.opencargo.pricing.port.in.RequestPricingCommand;
import no.clueless.opencargo.pricing.port.in.RequestPricingUseCase;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

public class RequestPricingService implements RequestPricingUseCase {
    private final PriceEngine priceEngine;

    public RequestPricingService(PriceEngine priceEngine) {
        this.priceEngine = ArgumentExceptionHelper.throwIfNull(priceEngine, "priceEngine");
    }

    @Override
    public PriceBreakdown requestPricing(RequestPricingCommand command) {
        ArgumentExceptionHelper.throwIfNull(command, "command");
        return priceEngine.calculate(new PricingQuery(
                command.getCargo(),
                command.getProductIds(),
                command.getDestination(),
                command.getCurrency()
        )).orElseThrow(() -> new RuntimeException("No pricing policy could be found for the given query"));
    }
}
