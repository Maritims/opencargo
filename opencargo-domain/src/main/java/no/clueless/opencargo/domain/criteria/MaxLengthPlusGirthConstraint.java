package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.shared.Measure;

import java.util.Objects;

public class MaxLengthPlusGirthConstraint implements Constraint {
    private final Measure<DistanceUnit> maxLengthPlusGirth;

    public MaxLengthPlusGirthConstraint(Measure<DistanceUnit> maxLengthPlusGirth) {
        this.maxLengthPlusGirth = Objects.requireNonNull(maxLengthPlusGirth);
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        var baseParcelGirth  = parcel.dimensions().getGirth().toBaseUnit();
        var baseParcelLength = parcel.dimensions().getLength().toBaseUnit();
        var girthPlusLength  = baseParcelGirth.add(baseParcelLength);
        var satisfied        = girthPlusLength.compareTo(maxLengthPlusGirth.toBaseUnit()) <= 0;
        return satisfied ?
                Decision.satisfied(getClass().getSimpleName()) :
                Decision.unsatisfied(getClass().getSimpleName(), String.format("Combined length and girth %s is outside allowed limit of %s", girthPlusLength, maxLengthPlusGirth));
    }
}
