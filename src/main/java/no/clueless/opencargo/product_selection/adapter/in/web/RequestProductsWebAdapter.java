package no.clueless.opencargo.product_selection.adapter.in.web;

import no.clueless.opencargo.infrastructure.marshalling.JsonMarshaller;
import no.clueless.opencargo.infrastructure.web.HttpMethod;
import no.clueless.opencargo.infrastructure.web.JsonResult;
import no.clueless.opencargo.infrastructure.web.WebAction;
import no.clueless.opencargo.infrastructure.web.WebAdapterBase;
import no.clueless.opencargo.product_selection.port.in.RequestProductsUseCase;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Map;
import java.util.function.BiConsumer;

public class RequestProductsWebAdapter extends WebAdapterBase {
    private final RequestProductsUseCase requestProductsUseCase;

    public RequestProductsWebAdapter(RequestProductsUseCase requestProductsUseCase) {
        this.requestProductsUseCase = ArgumentExceptionHelper.throwIfNull(requestProductsUseCase, "requestProductsUseCase");
    }

    @Override
    protected void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions) {
        actions.accept(HttpMethod.POST, Map.of(
                "/request-products", request -> {
                    var webCommand     = JsonMarshaller.unmarshal(request.getInputStream(), RequestProductsWebCommand.class);
                    var command        = webCommand.toDomain();
                    var products       = requestProductsUseCase.requestProducts(command);
                    return JsonResult.ok(products);
                }
        ));
    }
}
