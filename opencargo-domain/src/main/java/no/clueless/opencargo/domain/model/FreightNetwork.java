package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.shared.Money;
import no.clueless.opencargo.domain.shared.PostalCode;
import no.clueless.opencargo.domain.shared.graph.UndirectedMatrix;

import java.util.*;

public class FreightNetwork {
    private final UndirectedMatrix<PostalCode>                 mainZoneTable;
    private final Map<PostalCode, FreightTerminal>             freightTerminals;
    private final Map<FreightProductId, Set<FreightZonePrice>> zonePrices;

    public FreightNetwork(UndirectedMatrix<PostalCode> mainZoneTable, Map<PostalCode, FreightTerminal> freightTerminals, Map<FreightProductId, Set<FreightZonePrice>> zonePrices) {
        if (freightTerminals == null || freightTerminals.isEmpty()) {
            throw new IllegalArgumentException("freightTerminals cannot be null or empty");
        }
        if (zonePrices == null || zonePrices.isEmpty()) {
            throw new IllegalArgumentException("zonePrices cannot be null or empty");
        }
        this.mainZoneTable    = Objects.requireNonNull(mainZoneTable, "mainZoneTable cannot be null");
        this.freightTerminals = Map.copyOf(freightTerminals);
        this.zonePrices       = Map.copyOf(zonePrices);
    }

    public int getMainZonesBetween(FreightTerminal from, FreightTerminal to) {
        if (from == null) {
            throw new IllegalArgumentException("from cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("to cannot be null");
        }
        return mainZoneTable.getDistance(from.getPostalCode(), to.getPostalCode());
    }

    public int getTotalZonesBetween(PostalCode from, PostalCode to) {
        if (from == null) {
            throw new IllegalArgumentException("from cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("to cannot be null");
        }

        var fromTerminal = Optional.ofNullable(freightTerminals.get(from))
                .orElseGet(() -> freightTerminals.values()
                        .stream()
                        .filter(freightTerminal -> freightTerminal.covers(from))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No terminal covering postal code " + from + " was found")));

        var toTerminal = Optional.ofNullable(freightTerminals.get(to))
                .orElseGet(() -> freightTerminals.values()
                        .stream()
                        .filter(freightTerminal -> freightTerminal.covers(to))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No terminal covering postal code " + to + " was found")));

        var mainZonesBetween = getMainZonesBetween(fromTerminal, toTerminal);
        var totalZones       = mainZonesBetween + fromTerminal.getInboundZones(from) + toTerminal.getOutboundZones(to);

        return Math.max(totalZones, 1);
    }

    public Optional<Money> getZonePrice(FreightProductId freightProductId, Weight weight, int zone) {
        if (freightProductId == null) {
            throw new IllegalArgumentException("freightProductId cannot be null");
        }
        if (weight == null) {
            throw new IllegalArgumentException("weight cannot be null");
        }
        if (zone <= 0) {
            throw new IllegalArgumentException("zone must be positive");
        }

        var freightZonePrices = zonePrices.get(freightProductId);
        if (freightZonePrices == null || freightZonePrices.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(zonePrices.get(freightProductId))
                .flatMap(freightZones -> freightZones.stream()
                        .filter(zonePrice -> weight.isLessThanOrEqual(zonePrice.getMaxWeight()))
                        .map(freightZone -> freightZone.getPriceForZone(zone))
                        .findFirst()
                );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FreightNetwork that = (FreightNetwork) o;
        return Objects.equals(mainZoneTable, that.mainZoneTable) && Objects.equals(freightTerminals, that.freightTerminals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainZoneTable, freightTerminals);
    }

    @Override
    public String toString() {
        return "FreightNetwork{" +
                "mainZoneTable=" + mainZoneTable +
                ", freightTerminals=" + freightTerminals +
                '}';
    }
}
