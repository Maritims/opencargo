package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.physical.Destination;
import no.clueless.opencargo.domain.physical.Dimensions;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.shared.AdrClass;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Parcel {
    private final UUID       id;
    private final Dimensions dimensions;
    private final Weight        weight;
    private final Set<AdrClass> adrClasses;
    private final Destination   destination;

    public Parcel(UUID id, Dimensions dimensions, Weight weight, Set<AdrClass> adrClasses, Destination destination) {
        this.id          = Objects.requireNonNull(id);
        this.dimensions  = Objects.requireNonNull(dimensions);
        this.weight      = Objects.requireNonNull(weight);
        this.adrClasses  = Objects.requireNonNull(adrClasses);
        this.destination = Objects.requireNonNull(destination);
    }

    public UUID getId() {
        return id;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public Weight getWeight() {
        return weight;
    }

    public Set<AdrClass> getAdrClasses() {
        return adrClasses;
    }

    public Destination getDestination() {
        return destination;
    }
}
