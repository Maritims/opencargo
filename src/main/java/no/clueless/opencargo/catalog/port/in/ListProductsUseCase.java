package no.clueless.opencargo.catalog.port.in;

import no.clueless.opencargo.catalog.domain.service.ProductService;
import no.clueless.opencargo.domain.model.Products;

public interface ListProductsUseCase {
    Products listProducts();

    static ListProductsUseCase create() {
        return ProductService.getInstance();
    }
}
