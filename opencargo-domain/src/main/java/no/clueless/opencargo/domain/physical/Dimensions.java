package no.clueless.opencargo.domain.physical;

import no.clueless.opencargo.domain.shared.Measure;

import java.math.BigDecimal;
import java.util.Objects;

public class Dimensions {
    private final Measure<DistanceUnit> width;
    private final Measure<DistanceUnit> length;
    private final Measure<DistanceUnit> height;

    public Dimensions(Measure<DistanceUnit> width, Measure<DistanceUnit> length, Measure<DistanceUnit> height) {
        this.width  = Objects.requireNonNull(width);
        this.length = Objects.requireNonNull(length);
        this.height = Objects.requireNonNull(height);
    }

    public Measure<DistanceUnit> getWidth() {
        return width;
    }

    public Measure<DistanceUnit> getLength() {
        return length;
    }

    public Measure<DistanceUnit> getHeight() {
        return height;
    }

    public BigDecimal getCubicVolumeInMillimeters() {
        return length.toBaseUnit()
                .multiply(width.toBaseUnit())
                .multiply(height.toBaseUnit());
    }

    public Measure<VolumeUnit> getVolume() {
        return new Measure<>(getCubicVolumeInMillimeters(), VolumeUnit.CUBIC_MILLIMETER);
    }

    public Measure<DistanceUnit> getGirth() {
        var girthMm = width.toBaseUnit().add(height.toBaseUnit()).multiply(BigDecimal.valueOf(2));
        return new Measure<>(girthMm, DistanceUnit.MILLIMETER);
    }

    public Measure<DistanceUnit> getLengthPlusGirth() {
        var totalMm = getLength()
                .toBaseUnit()
                .add(getGirth().toBaseUnit());
        return new Measure<>(totalMm, DistanceUnit.MILLIMETER);
    }

    @Override
    public String toString() {
        return String.format("%s x %s x %s", length, width, height);
    }
}
