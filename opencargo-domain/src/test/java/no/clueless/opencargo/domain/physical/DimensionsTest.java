package no.clueless.opencargo.domain.physical;

import no.clueless.opencargo.domain.shared.Measure;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DimensionsTest {

    @Test
    void calculateGirth() {
        // arrange
        var sut = new Dimensions(
                new Measure<>(BigDecimal.valueOf(60), DistanceUnit.CENTIMETER),
                new Measure<>(BigDecimal.valueOf(50), DistanceUnit.CENTIMETER),
                new Measure<>(BigDecimal.valueOf(44), DistanceUnit.CENTIMETER)
        );
        var expected = new Measure<>(BigDecimal.valueOf(248), DistanceUnit.CENTIMETER);

        // act
        var actual = sut.calculateGirth();

        // assert
        assertEquals(expected, actual);
    }
}