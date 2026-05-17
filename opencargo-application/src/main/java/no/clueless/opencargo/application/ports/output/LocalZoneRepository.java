package no.clueless.opencargo.application.ports.output;

import no.clueless.opencargo.domain.model.LocalZone;
import no.clueless.opencargo.domain.shared.PostalCode;

import java.util.Optional;

public interface LocalZoneRepository {
    Optional<LocalZone> findByTerminalPostalCode(PostalCode postalCode);

    Optional<LocalZone> findByEntryPostalCode(PostalCode postalCode);
}
