package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.shared.PostalCode;
import no.clueless.opencargo.domain.shared.graph.UndirectedMatrix;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FreightNetwork {
    private final UndirectedMatrix<PostalCode>     mainZoneTable;
    private final Map<PostalCode, FreightTerminal> freightTerminals;

    public FreightNetwork(UndirectedMatrix<PostalCode> mainZoneTable, Map<PostalCode, FreightTerminal> freightTerminals) {
        if (freightTerminals == null || freightTerminals.isEmpty()) {
            throw new IllegalArgumentException("freightTerminals cannot be null or empty");
        }
        this.mainZoneTable    = Objects.requireNonNull(mainZoneTable, "mainZoneTable cannot be null");
        this.freightTerminals = freightTerminals;
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
}
