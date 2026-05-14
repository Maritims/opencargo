package no.clueless.opencargo.domain.physical;

import no.clueless.opencargo.domain.shared.Unit;

import java.math.BigDecimal;

public enum VolumeUnit implements Unit {
    CUBIC_MILLIMETER(new BigDecimal("1"), "mm³"),
    CUBIC_METER(new BigDecimal("1000000000"), "m³");

    private final BigDecimal multiplier;
    private final String     symbol;

    VolumeUnit(BigDecimal multiplier, String symbol) {
        this.multiplier = multiplier;
        this.symbol     = symbol;
    }

    @Override
    public BigDecimal getMultiplier() {
        return multiplier;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
