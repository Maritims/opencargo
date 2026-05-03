package no.clueless.opencargo.catalog.port.in;

import no.clueless.opencargo.catalog.domain.service.ConsignorService;

public interface CountConsignorsUseCase {
    int countConsignors();

    static ConsignorService create() {
        return ConsignorService.getInstance();
    }
}
