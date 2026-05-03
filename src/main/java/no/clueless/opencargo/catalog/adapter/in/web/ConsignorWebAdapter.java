package no.clueless.opencargo.catalog.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.catalog.port.in.CountConsignorsUseCase;
import no.clueless.opencargo.catalog.port.in.ListConsignorsUseCase;
import no.clueless.opencargo.infrastructure.web.*;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class ConsignorWebAdapter extends WebAdapterBase {
    private final CountConsignorsUseCase countConsignorsUseCase;
    private final ListConsignorsUseCase  listConsignorsUseCase;

    public ConsignorWebAdapter(CountConsignorsUseCase countConsignorsUseCase, ListConsignorsUseCase listConsignorsUseCase) {
        super("consignor-catalog");
        this.countConsignorsUseCase = ArgumentExceptionHelper.throwIfNull(countConsignorsUseCase, "countConsignorsUseCase");
        this.listConsignorsUseCase  = ArgumentExceptionHelper.throwIfNull(listConsignorsUseCase, "listConsignorsUseCase");
    }

    @SuppressWarnings("unused")
    public ConsignorWebAdapter() {
        this(CountConsignorsUseCase.create(), ListConsignorsUseCase.create());
    }

    @Override
    protected void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions) {
        actions.accept(HttpMethod.GET, Map.of(
                "/count-consignors", new WebAction("count-consignors", "Count all consignors") {
                    @Override
                    public WebResult execute(HttpServletRequest request) {
                        return JsonResult.ok(countConsignorsUseCase.countConsignors());
                    }
                },
                "/list-consignors", new WebAction("list-consignors", "List all consignors") {
                    @Override
                    public WebResult execute(HttpServletRequest request) {
                        var consignors = listConsignorsUseCase.listConsignors();
                        return JsonResult.ok(consignors == null ? Set.of() : consignors);
                    }
                }
        ));
    }
}
