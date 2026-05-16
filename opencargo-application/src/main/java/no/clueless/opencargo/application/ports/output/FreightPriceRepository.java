package no.clueless.opencargo.application.ports.output;

import no.clueless.opencargo.domain.model.FreightPrice;

import java.util.List;

public interface FreightPriceRepository {
    List<FreightPrice> findAll();
}
