package no.clueless.opencargo.product_selection.domain.service;

import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.product_selection.domain.service.engine.ProductSelectionQuery;
import no.clueless.opencargo.product_selection.domain.service.engine.RuleEngine;
import no.clueless.opencargo.product_selection.port.in.RequestProductsCommand;
import no.clueless.opencargo.product_selection.port.in.RequestProductsUseCase;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.domain.model.applicability.ApplicabilityReports;

public class RequestProductsService implements RequestProductsUseCase {
    private final RuleEngine ruleEngine;

    public RequestProductsService(RuleEngine ruleEngine) {
        this.ruleEngine = ArgumentExceptionHelper.throwIfNull(ruleEngine, "ruleEngine");
    }

    @Override
    public ApplicabilityReports<Product> requestProducts(RequestProductsCommand command) {
        ArgumentExceptionHelper.throwIfNull(command, "command");
        return ruleEngine.resolve(new ProductSelectionQuery(command.getCargo(), command.getDestination()));
    }

    private static final class SingletonHolder {
        private static final RequestProductsService INSTANCE = new RequestProductsService(RuleEngine.getInstance());
    }

    public static RequestProductsService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
