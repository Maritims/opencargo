package no.clueless.opencargo.domain.physical;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Weight {
    private final BigDecimal value;
    private final WeightUnit unit;

    private static final BigDecimal KILOGRAM_TO_GRAM = BigDecimal.valueOf(1000);

    public Weight(BigDecimal value, WeightUnit unit) {
        if(value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("value must be positive");
        }
        this.value = value;
        this.unit  = Objects.requireNonNull(unit);
    }

    public BigDecimal getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    public Weight toKilograms() {
        if (unit == WeightUnit.KILOGRAM) {
            return this;
        }

        var kgValue = value.divide(KILOGRAM_TO_GRAM, 3, RoundingMode.HALF_UP);
        return new Weight(kgValue, WeightUnit.KILOGRAM);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight = (Weight) o;
        return Objects.equals(value, weight.value) && unit == weight.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }

    @Override
    public String toString() {
        return "Weight{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }
}
