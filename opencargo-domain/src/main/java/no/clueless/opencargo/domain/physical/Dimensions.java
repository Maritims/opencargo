package no.clueless.opencargo.domain.physical;

import no.clueless.opencargo.domain.shared.Measure;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
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

    public Measure<DistanceUnit> calculateGirth() {
        var sides = new BigDecimal[]{width.toBaseUnit(), length.toBaseUnit(), height.toBaseUnit()};
        Arrays.sort(sides);
        var shortest      = sides[0];
        var otherShortest = sides[1];
        var longest       = sides[2];
        var girth = longest.add(shortest.multiply(BigDecimal.valueOf(2)))
                .add(otherShortest.multiply(BigDecimal.valueOf(2)))
                .divide(width.getUnit().getMultiplier(), RoundingMode.HALF_UP);

        return new Measure<>(girth, width.getUnit());
    }

    public boolean fitsWithin(Dimensions other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }

        var sides      = new BigDecimal[]{width.toBaseUnit(), length.toBaseUnit(), height.toBaseUnit()};
        var otherSides = new BigDecimal[]{other.width.toBaseUnit(), other.length.toBaseUnit(), other.height.toBaseUnit()};

        Arrays.sort(sides);
        Arrays.sort(otherSides);

        return sides[0].compareTo(otherSides[0]) <= 0 &&
                sides[1].compareTo(otherSides[1]) <= 0 &&
                sides[2].compareTo(otherSides[2]) <= 0;
    }

    @Override
    public String toString() {
        return String.format("%s x %s x %s", length, width, height);
    }
}
