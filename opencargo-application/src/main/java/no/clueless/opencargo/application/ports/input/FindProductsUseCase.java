package no.clueless.opencargo.application.ports.input;

import no.clueless.opencargo.domain.model.FreightProduct;

import java.util.List;

public interface FindProductsUseCase {
    List<FreightProduct> findProducts();
}
