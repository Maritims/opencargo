package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;
import no.clueless.opencargo.domain.shared.AdrClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdrConstraintTest {

    public static Stream<Arguments> provideNullOrEmptyAdrClassesForParcel() {
        return Stream.of(
                Arguments.of((Set<AdrClass>) null),
                Arguments.of(Set.of())
        );
    }

    @ParameterizedTest
    @MethodSource("provideNullOrEmptyAdrClassesForParcel")
    void a_parcel_without_adr_classes_should_satisfy_the_constraint(Set<AdrClass> adrClasses) {
        // arrange
        var parcel = mock(Parcel.class);
        when(parcel.getAdrClasses()).thenReturn(adrClasses);
        var sut = new AdrConstraint(Set.of(AdrClass.CLASS_1_EXPLOSIVES));

        // act
        var actual = sut.evaluate(parcel);

        // assert
        assertTrue(actual.isSatisfied());
    }

    @ParameterizedTest
    @ValueSource(strings = {"CLASS_1_EXPLOSIVES", "CLASS_2_GASES"})
    void a_parcel_matching_only_some_of_the_adr_classes_should_satisfy_the_constraint(String adrClassName) {
        // arrange
        var parcel = mock(Parcel.class);
        when(parcel.getAdrClasses()).thenReturn(Set.of(AdrClass.valueOf(adrClassName)));
        var sut = new AdrConstraint(Set.of(AdrClass.CLASS_1_EXPLOSIVES, AdrClass.CLASS_2_GASES));

        // act
        var actual = sut.evaluate(parcel);

        // assert
        assertTrue(actual.isSatisfied(), actual.getReason());
    }

    @Test
    void a_parcel_matching_all_of_the_adr_classes_should_satisfy_the_constraint() {
        // arrange
        var parcel = mock(Parcel.class);
        when(parcel.getAdrClasses()).thenReturn(Set.of(AdrClass.CLASS_1_EXPLOSIVES, AdrClass.CLASS_2_GASES));
        var sut = new AdrConstraint(Set.of(AdrClass.CLASS_1_EXPLOSIVES, AdrClass.CLASS_2_GASES));

        // act
        var actual = sut.evaluate(parcel);

        // assert
        assertTrue(actual.isSatisfied(), actual.getReason());
    }

    @Test
    void isSatisfiedBy() {
    }
}