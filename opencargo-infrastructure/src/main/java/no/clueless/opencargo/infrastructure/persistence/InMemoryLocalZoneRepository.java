package no.clueless.opencargo.infrastructure.persistence;

import no.clueless.opencargo.application.ports.output.LocalZoneRepository;
import no.clueless.opencargo.domain.model.LocalZone;
import no.clueless.opencargo.domain.shared.PostalCode;

import java.util.List;
import java.util.Optional;

public class InMemoryLocalZoneRepository implements LocalZoneRepository {
    private final List<LocalZone> localZones;

    public InMemoryLocalZoneRepository() {
        localZones = List.of(
                new LocalZone(new PostalCode("3110"), 0, 0),
                new LocalZone(new PostalCode("6409"), 0, 1)
        );
    }

    @Override
    public Optional<LocalZone> findByTerminalPostalCode(PostalCode postalCode) {
        if (postalCode == null) {
            throw new IllegalArgumentException("postalCode cannot be null");
        }
        return localZones.stream()
                .filter(localZone -> localZone.getPostalCode().equals(postalCode))
                .findFirst();
    }

    @Override
    public Optional<LocalZone> findByEntryPostalCode(PostalCode postalCode) {
        if (postalCode == null) {
            throw new IllegalArgumentException("postalCode cannot be null");
        }
        return null;
        /*return localZones.stream()
                .filter(localZone -> localZone.getZoneCoverage().containsKey(postalCode))
                .findFirst();*/
    }
}
