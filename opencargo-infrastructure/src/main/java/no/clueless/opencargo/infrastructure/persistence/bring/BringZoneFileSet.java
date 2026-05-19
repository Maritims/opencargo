package no.clueless.opencargo.infrastructure.persistence.bring;

import no.clueless.opencargo.domain.model.FreightProductId;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BringZoneFileSet {
    private final File                  mainZoneTableFile;
    private final File                        localZoneTableFile;
    private final Map<FreightProductId, File> zonePriceTableFileMap;
    private final Set<FreightProductId>       coveredFreightProductIds;

    public BringZoneFileSet(File mainZoneTableFile, File localZoneTableFile, Map<FreightProductId, File> zonePriceTableFileMap, Set<FreightProductId> coveredFreightProductIds) {
        if (coveredFreightProductIds == null || coveredFreightProductIds.isEmpty()) {
            throw new IllegalArgumentException("coveredFreightProductIds cannot be null or empty");
        }
        if (zonePriceTableFileMap == null || zonePriceTableFileMap.isEmpty()) {
            throw new IllegalArgumentException("zonePriceTableFile cannot be null or empty");
        }
        this.mainZoneTableFile        = Objects.requireNonNull(mainZoneTableFile, "mainZoneTableFile cannot be null");
        this.localZoneTableFile       = Objects.requireNonNull(localZoneTableFile, "localZoneTableFile cannot be null");
        this.zonePriceTableFileMap    = zonePriceTableFileMap;
        this.coveredFreightProductIds = coveredFreightProductIds;
    }

    public File getMainZoneTableFile() {
        return mainZoneTableFile;
    }

    public File getLocalZoneTableFile() {
        return localZoneTableFile;
    }

    public Map<FreightProductId, File> getZonePriceTableFileMap() {
        return zonePriceTableFileMap;
    }

    public Set<FreightProductId> getCoveredFreightProductIds() {
        return coveredFreightProductIds;
    }
}
