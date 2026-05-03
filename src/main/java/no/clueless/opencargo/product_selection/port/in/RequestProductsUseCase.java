package no.clueless.opencargo.product_selection.port.in;

import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.domain.model.applicability.ApplicabilityReports;

public interface RequestProductsUseCase {
    ApplicabilityReports<Product> requestProducts(RequestProductsCommand command);
}
