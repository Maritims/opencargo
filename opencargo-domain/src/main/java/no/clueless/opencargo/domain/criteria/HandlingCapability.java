package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.shared.HandlingDirective;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HandlingCapability implements Capability {
    private final Set<HandlingDirective> handlingDirectives;

    public HandlingCapability(Set<HandlingDirective> handlingDirectives) {
        if (handlingDirectives == null || handlingDirectives.isEmpty()) {
            throw new IllegalArgumentException("handlingDirectives cannot be null or empty");
        }
        this.handlingDirectives = handlingDirectives;
    }

    @Override
    public Decision canHandle(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        if(parcel.getHandlingDirectives().isEmpty()) {
            return Decision.satisfied(getClass().getSimpleName());
        }

        var unsupportedHandlingDirectives = parcel.getHandlingDirectives()
                .stream()
                .filter(handlingDirective -> !handlingDirectives.contains(handlingDirective))
                .collect(Collectors.toCollection(HashSet::new));
        return unsupportedHandlingDirectives.isEmpty() ? Decision.satisfied(getClass().getSimpleName()) : Decision.unsatisfied(getClass().getSimpleName(), String.format("Parcel has unsupported handling directives: %s", unsupportedHandlingDirectives));
    }
}
