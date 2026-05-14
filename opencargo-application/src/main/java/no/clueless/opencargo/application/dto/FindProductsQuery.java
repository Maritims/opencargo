package no.clueless.opencargo.application.dto;

import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.physical.WeightUnit;

import java.math.BigDecimal;
import java.util.List;

public class FindProductsQuery {
    private final BigDecimal   weight;
    private final WeightUnit   weightUnit;
    private final BigDecimal   width;
    private final BigDecimal   length;
    private final BigDecimal   height;
    private final DistanceUnit distanceUnit;
    private final List<String> adrClassShortCodes;

    public FindProductsQuery(BigDecimal weight, WeightUnit weightUnit, BigDecimal width, BigDecimal length, BigDecimal height, DistanceUnit distanceUnit, List<String> adrClassShortCodes) {
        this.weight             = weight;
        this.weightUnit         = weightUnit;
        this.width              = width;
        this.length             = length;
        this.height             = height;
        this.distanceUnit       = distanceUnit;
        this.adrClassShortCodes = adrClassShortCodes;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getLength() {
        return length;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public List<String> getAdrClassShortCodes() {
        return adrClassShortCodes;
    }
}
