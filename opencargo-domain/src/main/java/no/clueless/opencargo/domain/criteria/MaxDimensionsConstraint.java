package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.Dimensions;

import java.util.Objects;

public class MaxDimensionsConstraint extends Dimensions implements Constraint {
    public MaxDimensionsConstraint(Dimensions maxDimensions) {
        super(maxDimensions.getWidth(), maxDimensions.getLength(), maxDimensions.getHeight());
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        return Objects.requireNonNull(parcel).dimensions().fitsWithin(this) ?
                Decision.satisfied(getClass().getSimpleName()) :
                Decision.unsatisfied(parcel.getClass().getSimpleName(), "Package does not fit within the maximum dimensions");
    }
}
