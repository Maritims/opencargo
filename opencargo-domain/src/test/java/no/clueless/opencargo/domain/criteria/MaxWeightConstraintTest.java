package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.physical.WeightUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MaxWeightConstraintTest {
    MaxWeightConstraint maxWeightConstraint;

    @BeforeEach
    void setUp() {
        maxWeightConstraint = new MaxWeightConstraint(new Weight(BigDecimal.valueOf(35), WeightUnit.KILOGRAM));
    }

    @Test
    void a_parcel_with_a_weight_within_the_maximum_should_satisfy_the_constraint() {
        // arrange
        var parcel = new Parcel(
                UUID.randomUUID(),
                mock(),
                new Weight(BigDecimal.valueOf(30), WeightUnit.KILOGRAM),
                Set.of(),
                mock()
        );

        // act
        var actual = maxWeightConstraint.evaluate(parcel);

        // assert
        assertTrue(actual.satisfied(), actual.reason());
    }

    @Test
    void a_parcel_with_a_weight_exceeding_the_maximum_should_not_satisfy_the_constraint() {
        // arrange
        var parcel = new Parcel(
                UUID.randomUUID(),
                mock(),
                new Weight(BigDecimal.valueOf(36), WeightUnit.KILOGRAM),
                Set.of(),
                mock()
        );

        // act
        var actual = maxWeightConstraint.evaluate(parcel);

        // assert
        assertFalse(actual.satisfied(), actual.reason());
    }
}