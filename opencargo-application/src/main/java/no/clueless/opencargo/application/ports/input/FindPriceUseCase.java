package no.clueless.opencargo.application.ports.input;

import no.clueless.opencargo.domain.model.FreightPrice;
import no.clueless.opencargo.domain.model.FreightProductId;

import java.util.List;

public interface FindPriceUseCase {
    List<FreightPrice> findPrice(FreightProductId productId);
}
