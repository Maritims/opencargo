package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.Dimensions;

import java.util.Objects;

public class MinDimensionsConstraint extends Dimensions implements Constraint {
    public MinDimensionsConstraint(Dimensions minDimensions) {
        super(minDimensions.getWidth(), minDimensions.getWidth(), minDimensions.getHeight());
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        return this.fitsWithin(Objects.requireNonNull(parcel).dimensions()) ?
                Decision.isSatisfied(getClass().getSimpleName()) :
                Decision.unsatisfied(getClass().getSimpleName(), String.format("Parcel dimensions (%s) are smaller than minimum dimensions (%s)", parcel.dimensions(), this));
    }
}
