package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.physical.WeightUnit;
import no.clueless.opencargo.domain.shared.AdrClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AnyConstraintTest {
    AnyConstraint anyConstraint;

    public static Stream<Arguments> provideParcelBelow35KgOrParcelContainingExplosive() {
        return  Stream.of(
                Arguments.of(new Parcel(
                        UUID.randomUUID(),
                        mock(),
                        new Weight(BigDecimal.valueOf(30), WeightUnit.KILOGRAM),
                        Set.of(),
                        mock(),
                        Set.of()
                )),
                Arguments.of(new Parcel(
                        UUID.randomUUID(),
                        mock(),
                        mock(),
                        Set.of(AdrClass.CLASS_1_EXPLOSIVES),
                        mock(),
                        Set.of()
                ))
        );
    }

    @BeforeEach
    void setUp() {
        anyConstraint = new AnyConstraint(
                new MaxWeightConstraint(new Weight(BigDecimal.valueOf(35), WeightUnit.KILOGRAM)),
                new AdrConstraint(AdrClass.CLASS_1_EXPLOSIVES)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParcelBelow35KgOrParcelContainingExplosive")
    void a_parcel_matching_any_contained_constraint_should_satisfy_the_constraint() {
        // arrange
        var parcel = new Parcel(UUID.randomUUID(), mock(), new Weight(BigDecimal.valueOf(35), WeightUnit.KILOGRAM), Set.of(), mock(), Set.of());

        // act
        var actual = anyConstraint.evaluate(parcel);

        // assert
        assertTrue(actual.isSatisfied(), actual.getReason());
    }

    @Test
    void a_parcel_not_matching_any_contained_constraint_should_not_satisfy_the_constraint() {
        // arrange
        var parcel = new Parcel(UUID.randomUUID(), mock(), new Weight(BigDecimal.valueOf(36), WeightUnit.KILOGRAM), Set.of(AdrClass.CLASS_2_GASES), mock(), Set.of());

        // act
        var actual = anyConstraint.evaluate(parcel);

        // assert
        assertFalse(actual.isSatisfied(), actual.getReason());
    }
}