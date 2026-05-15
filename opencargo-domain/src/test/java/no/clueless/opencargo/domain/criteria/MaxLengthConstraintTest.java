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

class MaxLengthConstraintTest {
    MaxLengthConstraint maxLengthConstraint;

    @BeforeEach
    void setUp() {
        maxLengthConstraint = new MaxLengthConstraint(new Measure<>(BigDecimal.valueOf(50), DistanceUnit.CENTIMETER));
    }

    @Test
    void a_parcel_with_length_within_the_maximum_should_satisfy_the_constraint() {
        // arrange
        var parcel = new Parcel(
                UUID.randomUUID(),
                new Dimensions(
                        new Measure<>(BigDecimal.valueOf(60), DistanceUnit.CENTIMETER),
                        new Measure<>(BigDecimal.valueOf(50), DistanceUnit.CENTIMETER),
                        new Measure<>(BigDecimal.valueOf(44), DistanceUnit.CENTIMETER)
                ),
                mock(),
                Set.of(),
                mock()
        );

        // act
        var actual =  maxLengthConstraint.evaluate(parcel);

        // assert
        assertTrue(actual.satisfied(), actual.reason());
    }

    @Test
    void a_parcel_with_length_exceeding_the_maximum_should_not_satisfy_the_constraint() {
        // arrange
        var parcel = new Parcel(
                UUID.randomUUID(),
                new Dimensions(
                        new Measure<>(BigDecimal.valueOf(60), DistanceUnit.CENTIMETER),
                        new Measure<>(BigDecimal.valueOf(51), DistanceUnit.CENTIMETER),
                        new Measure<>(BigDecimal.valueOf(44), DistanceUnit.CENTIMETER)
                ),
                mock(),
                Set.of(),
                mock()
        );

        // act
        var actual =  maxLengthConstraint.evaluate(parcel);

        // assert
        assertFalse(actual.satisfied(), actual.reason());
    }
}