package no.clueless.opencargo.product_selection.port.in;

import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.domain.model.applicability.ApplicabilityReports;
import no.clueless.opencargo.product_selection.domain.service.RequestProductsService;

public interface RequestProductsUseCase {
    ApplicabilityReports<Product> requestProducts(RequestProductsCommand command);

    static RequestProductsUseCase create() {
        return RequestProductsService.getInstance();
    }
}
