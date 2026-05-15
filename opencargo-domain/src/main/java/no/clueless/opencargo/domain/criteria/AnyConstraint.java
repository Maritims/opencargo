package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;

import java.util.List;

public class AnyConstraint implements Constraint {
    private final List<Constraint> constraints;

    public AnyConstraint(Constraint... constraints) {
        if (constraints == null || constraints.length == 0) {
            throw new IllegalArgumentException("constraints cannot be null or empty");
        }
        this.constraints = List.of(constraints);
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        return constraints.stream()
                .map(constraint -> constraint.evaluate(parcel))
                .filter(Decision::satisfied)
                .findFirst()
                .orElseGet(() -> new Decision(getClass().getSimpleName(), false, "None of the constraints were satisfied"));
    }
}
