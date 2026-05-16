package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.shared.Measure;

public class MaxLengthPlusGirthConstraint extends Measure<DistanceUnit> implements Constraint {
    public MaxLengthPlusGirthConstraint(Measure<DistanceUnit> maxLengthPlusGirth) {
        super(maxLengthPlusGirth.getValue(), maxLengthPlusGirth.getUnit());
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        var baseParcelGirth  = parcel.dimensions().calculateGirth().toBaseUnit();
        var baseParcelLength = parcel.dimensions().getLength().toBaseUnit();
        var girthPlusLength  = baseParcelGirth.add(baseParcelLength);
        var satisfied        = girthPlusLength.compareTo(this.toBaseUnit()) <= 0;
        return satisfied ?
                Decision.isSatisfied(getClass().getSimpleName()) :
                Decision.unsatisfied(getClass().getSimpleName(), String.format("Combined length and girth %s is outside allowed limit of %s", girthPlusLength, this));
    }
}
