package no.clueless.opencargo.catalog.port.in;

import no.clueless.opencargo.catalog.domain.service.ProductService;

public interface CountProductsUseCase {
    int countProducts();

    static CountProductsUseCase create() {
        return ProductService.getInstance();
    }
}
