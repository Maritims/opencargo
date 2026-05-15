package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.shared.Measure;

import java.util.Objects;

public class MaxLengthConstraint implements Constraint {
    private final Measure<DistanceUnit> maxLength;

    public MaxLengthConstraint(Measure<DistanceUnit> maxLength) {
        this.maxLength = Objects.requireNonNull(maxLength);
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        var satisfied = Objects.requireNonNull(parcel)
                .dimensions()
                .getLength()
                .isLessThanOrEqual(maxLength);
        return satisfied ? Decision.satisfied(getClass().getSimpleName()) : Decision.unsatisfied(getClass().getSimpleName(), String.format("Length %s is outside allowed limit of %s", parcel.dimensions().getLength(), maxLength));
    }
}
