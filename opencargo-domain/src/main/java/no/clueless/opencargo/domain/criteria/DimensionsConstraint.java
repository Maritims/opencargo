package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.Dimensions;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DimensionsConstraint implements Constraint {
    private final Dimensions dimensions;
    private final boolean    mustBeAtLeast;

    public DimensionsConstraint(Dimensions dimensions, boolean mustBeAtLeast) {
        this.dimensions    = Objects.requireNonNull(dimensions);
        this.mustBeAtLeast = mustBeAtLeast;
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }

        var parcelBaseWidth  = parcel.getDimensions().getWidth().toBaseUnit();
        var parcelBaseLength = parcel.getDimensions().getLength().toBaseUnit();
        var parcelBaseHeight = parcel.getDimensions().getHeight().toBaseUnit();
        var parcelDimensions = Stream.of(parcelBaseWidth, parcelBaseLength, parcelBaseHeight)
                .sorted()
                .collect(Collectors.toList());

        var minBaseWidth  = dimensions.getWidth().toBaseUnit();
        var minBaseLength = dimensions.getLength().toBaseUnit();
        var minBaseHeight = dimensions.getHeight().toBaseUnit();
        var minDimensions = Stream.of(minBaseWidth, minBaseLength, minBaseHeight)
                .sorted()
                .collect(Collectors.toList());

        for(var i = 0; i < 3; i++) {
            var comparison = parcelDimensions.get(i).compareTo(minDimensions.get(i));
            if(mustBeAtLeast && comparison < 0) {
                return new Decision(getClass().getSimpleName(), false, "Package is too small on at least one axis");
            }
            if (!mustBeAtLeast && comparison > 0) {
                return new Decision(getClass().getSimpleName(), false, "Package is too big on at least one axis");
            }
        }

        return new Decision(getClass().getSimpleName(), true, "Package is at least as big as the minimum dimensions");
    }
}
