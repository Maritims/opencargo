package no.clueless.opencargo.domain.shared;

import java.math.BigDecimal;
import java.util.Objects;

public class Measure<U extends Unit> {
    private final BigDecimal value;
    private final U          unit;

    public Measure(BigDecimal value, U unit) {
        this.value = Objects.requireNonNull(value);
        this.unit  = Objects.requireNonNull(unit);
    }

    public BigDecimal getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    public BigDecimal toBaseUnit() {
        return value.multiply(unit.getMultiplier());
    }

    public boolean isLessThanOrEqual(Measure<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }
        return toBaseUnit().compareTo(other.toBaseUnit()) <= 0;
    }

    @Override
    public String toString() {
        return value + " " + unit.getSymbol();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Measure<?> measure = (Measure<?>) o;
        return Objects.equals(value, measure.value) && Objects.equals(unit, measure.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
}
