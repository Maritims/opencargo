package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.Weight;

import java.util.Objects;

public class MaxWeightConstraint extends Weight implements Constraint {
    public MaxWeightConstraint(Weight maxWeight) {
        super(maxWeight.getValue(), maxWeight.getUnit());
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        var satisfied = parcel.getWeight()
                .toKilograms()
                .getValue()
                .compareTo(this.toKilograms().getValue()) <= 0;
        return satisfied ? Decision.satisfied(getClass().getSimpleName()) : Decision.unsatisfied(getClass().getSimpleName(), String.format("Weight %s exceeds the limit of %s", parcel.getWeight(), this));
    }

    @Override
    public boolean isSatisfiedBy(Parcel parcel) {
        return Objects.requireNonNull(parcel)
                .getWeight()
                .toKilograms()
                .getValue()
                .compareTo(this.toKilograms().getValue()) <= 0;
    }
}
