package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.Dimensions;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.shared.Measure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MinDimensionsConstraintTest {
    MinDimensionsConstraint minDimensionsConstraint;

    @BeforeEach
    void setUp() {
        minDimensionsConstraint = new MinDimensionsConstraint(new Dimensions(
                new Measure<>(BigDecimal.valueOf(15), DistanceUnit.CENTIMETER),
                new Measure<>(BigDecimal.valueOf(10), DistanceUnit.CENTIMETER),
                new Measure<>(BigDecimal.valueOf(1), DistanceUnit.CENTIMETER)
        ));
    }

    @Test
    void a_parcel_with_dimensions_exceeding_the_minimum_should_satisfy_the_constraint() {
        // arrange
        var parcel = new Parcel(
                UUID.randomUUID(),
                new Dimensions(
                        new Measure<>(BigDecimal.valueOf(16.0), DistanceUnit.CENTIMETER),
                        new Measure<>(BigDecimal.valueOf(15.0), DistanceUnit.CENTIMETER),
                        new Measure<>(BigDecimal.valueOf(1.0), DistanceUnit.CENTIMETER)
                ),
                mock(),
                Set.of(),
                mock(),
                Set.of()
        );

        // act
        var actual = minDimensionsConstraint.evaluate(parcel);

        // assert
        assertTrue(actual.isSatisfied(), actual.getReason());
    }

    @Test
    void a_parcel_with_dimensions_below_the_minimum_should_not_satisfy_the_constraint() {
        // arrange
        var parcel = new Parcel(
                UUID.randomUUID(),
                new Dimensions(
                        new Measure<>(BigDecimal.valueOf(14.0), DistanceUnit.CENTIMETER),
                        new Measure<>(BigDecimal.valueOf(10.0), DistanceUnit.CENTIMETER),
                        new Measure<>(BigDecimal.valueOf(1.0), DistanceUnit.CENTIMETER)
                ),
                mock(),
                Set.of(),
                mock(),
                Set.of()
        );

        // act
        var actual =  minDimensionsConstraint.evaluate(parcel);

        // assert
        assertFalse(actual.isSatisfied(), actual.getReason());
    }
}