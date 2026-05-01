package no.clueless.opencargo.rules;

import no.clueless.opencargo.Cargo;

import java.math.BigDecimal;

public interface ScalarRule extends Rule {
    @SuppressWarnings("unused")
    BigDecimal getMin();

    @SuppressWarnings("unused")
    BigDecimal getMax();

    BigDecimal resolveValue(Cargo cargo);
}
