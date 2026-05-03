package no.clueless.opencargo.catalog.port.in;

import no.clueless.opencargo.catalog.domain.service.ConsignorService;
import no.clueless.opencargo.domain.model.Consignor;
import no.clueless.opencargo.shared.Population;

import java.util.Set;

public interface ListConsignorsUseCase {
    Population<Consignor, Set<Consignor>> listConsignors();

    static ConsignorService create() {
        return ConsignorService.getInstance();
    }
}
