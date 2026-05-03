package no.clueless.opencargo.product_selection.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.infrastructure.marshalling.JsonMarshaller;
import no.clueless.opencargo.infrastructure.web.*;
import no.clueless.opencargo.product_selection.port.in.CountRulesUseCase;
import no.clueless.opencargo.product_selection.port.in.ListRulesUseCase;
import no.clueless.opencargo.product_selection.port.in.RequestProductsUseCase;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class ProductSelectionWebAdapter extends WebAdapterBase {
    private final CountRulesUseCase      countRulesUseCase;
    private final ListRulesUseCase       listRulesUseCase;
    private final RequestProductsUseCase requestProductsUseCase;

    public ProductSelectionWebAdapter(CountRulesUseCase countRulesUseCase, ListRulesUseCase listRulesUseCase, RequestProductsUseCase requestProductsUseCase) {
        super("product-selection");
        this.countRulesUseCase      = ArgumentExceptionHelper.throwIfNull(countRulesUseCase, "countRulesUseCase");
        this.listRulesUseCase       = ArgumentExceptionHelper.throwIfNull(listRulesUseCase, "listRulesUseCase");
        this.requestProductsUseCase = ArgumentExceptionHelper.throwIfNull(requestProductsUseCase, "requestProductsUseCase");
    }

    @SuppressWarnings("unused")
    public ProductSelectionWebAdapter() {
        this(CountRulesUseCase.create(), ListRulesUseCase.create(), RequestProductsUseCase.create());
    }

    @Override
    protected void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions) {
        actions.accept(HttpMethod.GET, Map.of(
                "/count-rules", new WebAction("count-rules", "Count all rules") {
                    @Override
                    public WebResult execute(HttpServletRequest request) {
                        return JsonResult.ok(countRulesUseCase.countRules());
                    }
                },
                "/list-rules", new WebAction("rules", "Lists all rules") {
                    @Override
                    public WebResult execute(HttpServletRequest request) {
                        var rules = listRulesUseCase.listRules();
                        return rules == null ? JsonResult.ok(Set.of()) : JsonResult.ok(rules);
                    }
                }
        ));

        actions.accept(HttpMethod.POST, Map.of(
                "/request-products", new WebAction("request-products", "Request products for a cargo") {
                    @Override
                    public WebResult execute(HttpServletRequest request) throws IOException {
                        var webCommand = JsonMarshaller.unmarshal(request.getInputStream(), RequestProductsWebCommand.class);
                        var command    = webCommand.toDomain();
                        var products   = requestProductsUseCase.requestProducts(command);
                        return JsonResult.ok(products);
                    }
                }
        ));
    }
}
