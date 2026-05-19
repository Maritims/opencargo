package no.clueless.opencargo.application.ports.output;

import no.clueless.opencargo.domain.model.FreightNetwork;
import no.clueless.opencargo.domain.model.FreightProductId;

import java.util.Optional;

public interface FreightNetworkRepository {
    Optional<FreightNetwork> findByFreightProductId(FreightProductId productId);
}
