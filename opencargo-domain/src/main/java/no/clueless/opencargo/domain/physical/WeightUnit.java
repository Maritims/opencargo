package no.clueless.opencargo.domain.physical;

import no.clueless.opencargo.domain.shared.Unit;

import java.math.BigDecimal;

public enum WeightUnit implements Unit {
    MILLIGRAM(new BigDecimal("1"), "MG"),
    GRAM(new BigDecimal("1000"), "G"),
    KILOGRAM(new BigDecimal("100000"), "KG");

    private final BigDecimal milligramMultiplier;
    private final String     symbol;

    WeightUnit(BigDecimal milligramMultiplier, String symbol) {
        this.milligramMultiplier = milligramMultiplier;
        this.symbol              = symbol;
    }

    @Override
    public BigDecimal getMultiplier() {
        return milligramMultiplier;
    }

    public String getSymbol() {
        return symbol;
    }
}
