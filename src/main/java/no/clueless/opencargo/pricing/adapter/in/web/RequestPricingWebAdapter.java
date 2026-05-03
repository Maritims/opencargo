package no.clueless.opencargo.pricing.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.infrastructure.web.*;
import no.clueless.opencargo.pricing.port.in.RequestPricingUseCase;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.infrastructure.marshalling.JsonMarshaller;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

public class RequestPricingWebAdapter extends WebAdapterBase {
    private final RequestPricingUseCase requestPricingUseCase;

    public RequestPricingWebAdapter(RequestPricingUseCase requestPricingUseCase) {
        super("pricing");
        this.requestPricingUseCase = ArgumentExceptionHelper.throwIfNull(requestPricingUseCase, "requestPricingUseCase");
    }

    @SuppressWarnings("unused")
    public RequestPricingWebAdapter() {
        this(RequestPricingUseCase.create());
    }

    @Override
    protected void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions) {
        actions.accept(HttpMethod.POST, Map.of(
                "/request-pricing", new WebAction("request-pricing", "Request pricing for a cargo") {
                    @Override
                    public WebResult execute(HttpServletRequest request) throws IOException {
                        var webCommand     = JsonMarshaller.unmarshal(request.getInputStream(), RequestPricingWebCommand.class);
                        var command        = webCommand.toDomain();
                        var priceBreakdown = requestPricingUseCase.requestPricing(command);
                        return JsonResult.ok(priceBreakdown);
                    }
                }
        ));
    }
}
