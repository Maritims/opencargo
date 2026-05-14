package no.clueless.opencargo.domain.physical;

import no.clueless.opencargo.domain.shared.Unit;

import java.math.BigDecimal;

public enum DistanceUnit implements Unit {
    MILLIMETER(new BigDecimal("1"), "mm"),
    CENTIMETER(new BigDecimal("10"), "cm"),
    METER(new BigDecimal("1000"), "m");

    private final BigDecimal millimeterMultiplier;
    private final String     symbol;

    DistanceUnit(BigDecimal millimeterMultiplier, String symbol) {
        this.millimeterMultiplier = millimeterMultiplier;
        this.symbol               = symbol;
    }

    @Override
    public BigDecimal getMultiplier() {
        return millimeterMultiplier;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
