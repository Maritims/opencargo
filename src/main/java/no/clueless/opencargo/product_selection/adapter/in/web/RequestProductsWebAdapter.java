package no.clueless.opencargo.product_selection.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.infrastructure.marshalling.JsonMarshaller;
import no.clueless.opencargo.infrastructure.web.*;
import no.clueless.opencargo.product_selection.port.in.RequestProductsUseCase;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

public class RequestProductsWebAdapter extends WebAdapterBase {
    private final RequestProductsUseCase requestProductsUseCase;

    public RequestProductsWebAdapter(RequestProductsUseCase requestProductsUseCase) {
        super("product-selection");
        this.requestProductsUseCase = ArgumentExceptionHelper.throwIfNull(requestProductsUseCase, "requestProductsUseCase");
    }

    @SuppressWarnings("unused")
    public RequestProductsWebAdapter() {
        this(RequestProductsUseCase.create());
    }

    @Override
    protected void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions) {
        actions.accept(HttpMethod.POST, Map.of(
                "/request-products", new WebAction("request-products", "Request products for a cargo") {
                    @Override
                    public WebResult execute(HttpServletRequest request) throws IOException {
                        var webCommand     = JsonMarshaller.unmarshal(request.getInputStream(), RequestProductsWebCommand.class);
                        var command        = webCommand.toDomain();
                        var products       = requestProductsUseCase.requestProducts(command);
                        return JsonResult.ok(products);
                    }
                }
        ));
    }
}
