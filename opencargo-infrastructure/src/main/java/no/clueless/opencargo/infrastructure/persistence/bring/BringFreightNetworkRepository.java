package no.clueless.opencargo.infrastructure.persistence.bring;

import no.clueless.opencargo.application.ports.output.FreightNetworkRepository;
import no.clueless.opencargo.domain.model.FreightNetwork;
import no.clueless.opencargo.domain.model.FreightProductId;

import java.util.*;
import java.util.stream.Collectors;

public class BringFreightNetworkRepository implements FreightNetworkRepository {
    private final Map<FreightProductId, FreightNetwork> freightNetworks;

    public BringFreightNetworkRepository(
            BringMainZoneTableParser mainZoneTableParser,
            BringLocalZoneTableParser localZoneTableParser,
            BringZonePriceTableParser zonePriceTableParser,
            Set<BringZoneFileSet> zoneTableFilePairs
    ) {
        if (mainZoneTableParser == null) {
            throw new IllegalArgumentException("mainZoneTableParser cannot be null");
        }
        if (localZoneTableParser == null) {
            throw new IllegalArgumentException("localZoneTableParser cannot be null");
        }
        if (zonePriceTableParser == null) {
            throw new IllegalArgumentException("zonePriceTableParser cannot be null");
        }
        if (zoneTableFilePairs == null || zoneTableFilePairs.isEmpty()) {
            throw new IllegalArgumentException("zoneTableFilePairs cannot be null or empty");
        }

        var freightNetworkMap = new HashMap<FreightProductId, FreightNetwork>();
        for (var zoneTableFilePair : zoneTableFilePairs) {
            var mainZoneMatrix   = mainZoneTableParser.parseMainZoneTable(zoneTableFilePair.getMainZoneTableFile());
            var freightTerminals = localZoneTableParser.parseLocalZoneTable(zoneTableFilePair.getLocalZoneTableFile());
            var zonePriceMap = zoneTableFilePair.getZonePriceTableFileMap()
                    .entrySet()
                    .stream()
                    .map(entry -> Map.entry(entry.getKey(), zonePriceTableParser.parseZonePriceTable(entry.getValue())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            var freightNetwork = new FreightNetwork(mainZoneMatrix, freightTerminals, zonePriceMap);

            zoneTableFilePair.getCoveredFreightProductIds().forEach(coveredFreightProductId -> freightNetworkMap.put(coveredFreightProductId, freightNetwork));
        }
        this.freightNetworks = Map.copyOf(freightNetworkMap);
    }

    @Override
    public Optional<FreightNetwork> findByFreightProductId(FreightProductId productId) {
        if (productId == null) {
            throw new IllegalArgumentException("productId cannot be null");
        }
        if (!freightNetworks.containsKey(productId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(freightNetworks.get(productId));
    }
}
