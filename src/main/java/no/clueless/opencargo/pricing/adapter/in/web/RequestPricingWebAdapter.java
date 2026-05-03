package no.clueless.opencargo.pricing.adapter.in.web;

import no.clueless.opencargo.infrastructure.web.*;
import no.clueless.opencargo.pricing.port.in.RequestPricingUseCase;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.JsonMarshaller;

import java.util.Map;
import java.util.function.BiConsumer;

public class RequestPricingWebAdapter extends WebAdapterBase {
    private final RequestPricingUseCase requestPricingUseCase;

    public RequestPricingWebAdapter(RequestPricingUseCase requestPricingUseCase) {
        this.requestPricingUseCase = ArgumentExceptionHelper.throwIfNull(requestPricingUseCase, "requestPricingUseCase");
    }

    @Override
    protected void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions) {
        actions.accept(HttpMethod.POST, Map.of(
                "/request-pricing", request -> {
                    var webCommand     = JsonMarshaller.unmarshal(request.getInputStream(), RequestPricingWebCommand.class);
                    var command        = webCommand.toDomain();
                    var priceBreakdown = requestPricingUseCase.requestPricing(command);
                    return JsonResult.ok(priceBreakdown);
                }
        ));
    }
}
