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
        var baseParcelGirth  = parcel.getDimensions().getGirth().toBaseUnit();
        var baseParcelLength = parcel.getDimensions().getLength().toBaseUnit();
        var girthPlusLength  = baseParcelGirth.add(baseParcelLength);


        var satisfied = girthPlusLength.compareTo(maxLengthPlusGirth.toBaseUnit()) <= 0;
        return new Decision(getClass().getSimpleName(),
                satisfied,
                satisfied ? String.format("Combined length and girth %s is within allowed limit of %s", girthPlusLength, maxLengthPlusGirth)
                        : String.format("Combined length and girth %s is outside allowed limit of %s", girthPlusLength, maxLengthPlusGirth)
        );
    }
}
