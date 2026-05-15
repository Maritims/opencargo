package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.Dimensions;

import java.util.Objects;

public class MaxDimensionsConstraint implements Constraint {
    private final Dimensions maxDimensions;

    public MaxDimensionsConstraint(Dimensions maxDimensions) {
        this.maxDimensions = Objects.requireNonNull(maxDimensions);
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        return Objects.requireNonNull(parcel).dimensions().fitsWithin(maxDimensions) ?
                Decision.satisfied(getClass().getSimpleName()) :
                Decision.unsatisfied(parcel.getClass().getSimpleName(), "Package does not fit within the maximum dimensions");
    }
}
