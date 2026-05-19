package no.clueless.opencargo.infrastructure.persistence.postnord;

import no.clueless.opencargo.application.ports.output.FreightNetworkRepository;
import no.clueless.opencargo.domain.model.FreightNetwork;
import no.clueless.opencargo.domain.model.FreightProductId;

import java.util.Optional;

public class PostNordNetworkRepository implements FreightNetworkRepository {
    @Override
    public Optional<FreightNetwork> findByFreightProductId(FreightProductId productId) {
        return Optional.empty();
    }
}
