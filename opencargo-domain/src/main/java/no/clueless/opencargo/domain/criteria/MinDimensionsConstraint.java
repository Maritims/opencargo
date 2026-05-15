package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.Dimensions;

import java.util.Objects;

public class MinDimensionsConstraint implements Constraint {
    private final Dimensions minDimensions;

    public MinDimensionsConstraint(Dimensions minDimensions) {
        this.minDimensions = Objects.requireNonNull(minDimensions);
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        return minDimensions.fitsWithin(Objects.requireNonNull(parcel).dimensions()) ?
                Decision.satisfied(getClass().getSimpleName()) :
                Decision.unsatisfied(getClass().getSimpleName(), "Package does not fit within the minimum dimensions");
    }
}
