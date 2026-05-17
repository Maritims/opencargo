package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.shared.PostalCode;

import java.util.*;
import java.util.stream.Collectors;

public class FreightTerminal {
    private final PostalCode                           postalCode;
    private final LinkedHashMap<PostalCode, LocalZone> zones;

    public FreightTerminal(PostalCode postalCode, Set<LocalZone> zones) {
        if (zones == null || zones.isEmpty()) {
            throw new IllegalArgumentException("zones cannot be null or empty");
        }
        this.postalCode = Objects.requireNonNull(postalCode, "postalCode cannot be null");
        this.zones      = zones.stream().collect(Collectors.toMap(LocalZone::getPostalCode, zone -> zone, (a, b) -> a, LinkedHashMap::new));
    }

    public static LinkedHashSet<FreightTerminal> of(Map<PostalCode, LinkedHashSet<LocalZone>> zones) {
        if (zones == null || zones.isEmpty()) {
            throw new IllegalArgumentException("zones cannot be null or empty");
        }
        return zones.entrySet()
                .stream()
                .map(entry -> new FreightTerminal(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(FreightTerminal::getPostalCode))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public Map<PostalCode, LocalZone> getZones() {
        return zones;
    }

    public boolean covers(PostalCode postalCode) {
        if (postalCode == null) {
            throw new IllegalArgumentException("postalCode cannot be null");
        }
        return zones.containsKey(postalCode);
    }

    public int getInboundZones(PostalCode postalCode) {
        if (postalCode == null) {
            throw new IllegalArgumentException("postalCode cannot be null");
        }
        return Objects.equals(this.postalCode, postalCode) ? 0 : Optional.ofNullable(zones.get(postalCode))
                .map(LocalZone::getInboundZones)
                .orElseThrow(() -> new IllegalStateException("Freight terminal " + getPostalCode() + " does not cover " + postalCode));
    }

    public int getOutboundZones(PostalCode postalCode) {
        if (postalCode == null) {
            throw new IllegalArgumentException("postalCode cannot be null");
        }
        return Objects.equals(this.postalCode, postalCode) ? 0 : Optional.ofNullable(zones.get(postalCode))
                .map(LocalZone::getOutboundZones)
                .orElseThrow(() -> new IllegalStateException("Freight terminal " + getPostalCode() + " does not cover " + postalCode));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FreightTerminal that = (FreightTerminal) o;
        return Objects.equals(postalCode, that.postalCode) && Objects.equals(zones, that.zones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, zones);
    }

    @Override
    public String toString() {
        return "FreightTerminal{" +
                "postalCode=" + postalCode +
                ", zones=" + zones +
                '}';
    }
}
