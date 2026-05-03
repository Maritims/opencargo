package no.clueless.opencargo.product_selection.ports;

import no.clueless.opencargo.catalog.Product;
import no.clueless.opencargo.product_selection.ProductSelectionQuery;
import no.clueless.opencargo.shared.applicability.ApplicabilityReports;

public interface RequestSelectableProductsPort {
    ApplicabilityReports<Product> findSelectableProducts(ProductSelectionQuery query);
}
