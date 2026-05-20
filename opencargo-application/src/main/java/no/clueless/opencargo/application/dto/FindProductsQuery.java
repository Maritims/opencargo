package no.clueless.opencargo.application.dto;

import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.physical.WeightUnit;

import java.math.BigDecimal;
import java.util.List;

/**
 * The query object for finding products.
 */
public class FindProductsQuery {
    /**
     * The weight of the parcel.
     */
    private final BigDecimal             weight;
    /**
     * The unit of the weight.
     */
    private final WeightUnit             weightUnit;
    /**
     * The width of the parcel.
     */
    private final BigDecimal             width;
    /**
     * The length of the parcel.
     */
    private final BigDecimal             length;
    /**
     * The height of the parcel.
     */
    private final BigDecimal             height;
    /**
     * The unit of the distance.
     */
    private final DistanceUnit           distanceUnit;
    /**
     * The list of adr class short codes.
     */
    private final List<String>           adrClassShortCodes;
    /**
     * The list of acceptable carrier ids for the parcel. Overrides {@link #unacceptableCarrierIds} when both are set.
     */
    private final List<String>           acceptableCarrierIds;
    /**
     * The list of unacceptable carrier ids for the parcel. Ignored when {@link #acceptableCarrierIds} is not empty.
     */
    private final List<String>           unacceptableCarrierIds;
    /**
     * The list of required handling directives for the parcel.
     */
    private final List<String> requiredHandlingDirectives;

    public FindProductsQuery(
            BigDecimal weight,
            WeightUnit weightUnit,
            BigDecimal width,
            BigDecimal length,
            BigDecimal height,
            DistanceUnit distanceUnit,
            List<String> adrClassShortCodes,
            List<String> acceptableCarrierIds,
            List<String> unacceptableCarrierIds,
            List<String> requiredHandlingDirectives
    ) {
        this.weight                     = weight;
        this.weightUnit                 = weightUnit;
        this.width                      = width;
        this.length                     = length;
        this.height                     = height;
        this.distanceUnit               = distanceUnit;
        this.adrClassShortCodes         = adrClassShortCodes;
        this.acceptableCarrierIds       = acceptableCarrierIds;
        this.unacceptableCarrierIds     = unacceptableCarrierIds;
        this.requiredHandlingDirectives = requiredHandlingDirectives;
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

    public List<String> getAcceptableCarrierIds() {
        return acceptableCarrierIds;
    }

    public List<String> getUnacceptableCarrierIds() {
        return unacceptableCarrierIds;
    }

    public List<String> getRequiredHandlingDirectives() {
        return requiredHandlingDirectives;
    }
}
