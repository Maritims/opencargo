package no.clueless.opencargo.domain.shared;

import java.math.BigDecimal;

public class Percentage {
    private final BigDecimal factor;

    public Percentage(BigDecimal factor) {
        if (factor == null || factor.compareTo(BigDecimal.ZERO) < 0 || factor.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("factor must be between 0 and 1");
        }
        this.factor = factor;
    }

    public BigDecimal getFactor() {
        return factor;
    }
}
