package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.physical.Destination;
import no.clueless.opencargo.domain.physical.Dimensions;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.shared.AdrClass;
import no.clueless.opencargo.domain.shared.HandlingDirective;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Parcel {
    private final UUID                   id;
    private final Dimensions             dimensions;
    private final Weight                 weight;
    private final Set<AdrClass>          adrClasses;
    private final Destination            destination;
    private final Set<HandlingDirective> handlingDirectives;

    public Parcel(UUID id, Dimensions dimensions, Weight weight, Set<AdrClass> adrClasses, Destination destination, Set<HandlingDirective> handlingDirectives) {
        this.id                 = Objects.requireNonNull(id, "id cannot be null");
        this.dimensions         = Objects.requireNonNull(dimensions, "dimensions cannot be null");
        this.weight             = Objects.requireNonNull(weight, "weight cannot be null");
        this.adrClasses         = Objects.requireNonNull(adrClasses, "adrClasses cannot be null");
        this.destination        = Objects.requireNonNull(destination, "destination cannot be null");
        this.handlingDirectives = handlingDirectives == null ? Set.of() : Set.copyOf(handlingDirectives);
    }

    public UUID getId() {
        return id;
    }

    public Dimensions dimensions() {
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

    public Dimensions getDimensions() {
        return dimensions;
    }

    public Set<HandlingDirective> getHandlingDirectives() {
        return handlingDirectives;
    }
}
