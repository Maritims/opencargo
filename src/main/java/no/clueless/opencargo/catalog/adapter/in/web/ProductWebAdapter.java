package no.clueless.opencargo.catalog.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.catalog.port.in.CountProductsUseCase;
import no.clueless.opencargo.catalog.port.in.ListProductsUseCase;
import no.clueless.opencargo.infrastructure.web.*;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class ProductWebAdapter extends WebAdapterBase {
    private final CountProductsUseCase countProductsUseCase;
    private final ListProductsUseCase  listProductsUseCase;

    public ProductWebAdapter(CountProductsUseCase countProductsUseCase, ListProductsUseCase listProductsUseCase) {
        super("product-catalog");
        this.countProductsUseCase = ArgumentExceptionHelper.throwIfNull(countProductsUseCase, "countProductsUseCase");
        this.listProductsUseCase  = ArgumentExceptionHelper.throwIfNull(listProductsUseCase, "listProductsUseCase");
    }

    @SuppressWarnings("unused")
    public ProductWebAdapter() {
        this(CountProductsUseCase.create(), ListProductsUseCase.create());
    }

    @Override
    protected void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions) {
        actions.accept(HttpMethod.GET, Map.of(
                "/count-products", new WebAction("count-products", "Count all products") {
                    public WebResult execute(HttpServletRequest request) {
                        return JsonResult.ok(countProductsUseCase.countProducts());
                    }
                },
                "/list-products", new WebAction("list-products", "List all products") {
                    @Override
                    public WebResult execute(HttpServletRequest request) {
                        var products = listProductsUseCase.listProducts();
                        return JsonResult.ok(products == null ? Set.of() : products);
                    }
                }
        ));
    }
}
