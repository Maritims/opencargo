package no.clueless.opencargo.domain.physical;

import no.clueless.opencargo.domain.shared.Measure;

import java.math.BigDecimal;

public class Weight extends Measure<WeightUnit> implements Comparable<Weight> {

    public Weight(BigDecimal value, WeightUnit unit) {
        super(value, unit);
    }

    @Override
    public int compareTo(Weight o) {
        return toBaseUnit().compareTo(o.toBaseUnit());
    }
}
