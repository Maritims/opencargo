package no.clueless.opencargo.rules;

import no.clueless.opencargo.Cargo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScalarRuleTest {
    public static Stream<Arguments> resolveValue() {
        BigDecimal weight = new BigDecimal("1.0");
        BigDecimal width  = new BigDecimal("2.0");
        BigDecimal length = new BigDecimal("3.0");
        BigDecimal height = new BigDecimal("4.0");
        Cargo      cargo  = new Cargo(weight, width, length, height, new BigDecimal("5.0"));

        return Stream.of(
                Arguments.of(new WeightRule(1, null, null, "foo", "bar", 0, false, BigDecimal.ONE, BigDecimal.TEN), cargo, weight, "Expected the cargo's weight to be returned"),
                Arguments.of(new WidthRule(1, null, null, "foo", "bar", 0, false, BigDecimal.ONE, BigDecimal.TEN), cargo, width, "Expected the cargo's width to be returned"),
                Arguments.of(new HeightRule(1, null, null, "foo", "bar", 0, false, BigDecimal.ONE, BigDecimal.TEN), cargo, height, "Expected the cargo's height to be returned"),
                Arguments.of(new LengthRule(1, null, null, "foo", "bar", 0, false, BigDecimal.ONE, BigDecimal.TEN), cargo, length, "Expected the cargo's length to be returned")
        );
    }

    @ParameterizedTest
    @MethodSource
    void resolveValue(ScalarRule rule, Cargo cargo, BigDecimal expected, String message) {
        // arrange
        // act
        BigDecimal actual = rule.resolveValue(cargo);

        // assert
        assertEquals(expected, actual, message);
    }
}