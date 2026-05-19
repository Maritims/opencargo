package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.shared.Money;

import java.util.Map;
import java.util.Objects;

public class FreightZonePrice {
    private final Weight              maxWeight;
    private final Map<Integer, Money> priceByZoneMap;

    public FreightZonePrice(Weight maxWeight, Map<Integer, Money> priceByZoneMap) {
        if (priceByZoneMap == null || priceByZoneMap.isEmpty()) {
            throw new IllegalArgumentException("priceByZoneMap cannot be null or empty");
        }
        this.maxWeight      = Objects.requireNonNull(maxWeight, "maxWeight cannot be null");
        this.priceByZoneMap = Map.copyOf(priceByZoneMap);
    }

    public Weight getMaxWeight() {
        return maxWeight;
    }

    public Map<Integer, Money> getPriceByZoneMap() {
        return Map.copyOf(priceByZoneMap);
    }

    public Money getPriceForZone(int zone) {
        return priceByZoneMap.get(zone);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FreightZonePrice that = (FreightZonePrice) o;
        return Objects.equals(maxWeight, that.maxWeight) && Objects.equals(priceByZoneMap, that.priceByZoneMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxWeight, priceByZoneMap);
    }

    @Override
    public String toString() {
        return maxWeight.toString() + " " + priceByZoneMap;
    }
}
