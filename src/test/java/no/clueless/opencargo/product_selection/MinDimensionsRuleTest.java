package no.clueless.opencargo.product_selection;

import no.clueless.opencargo.shared.cargo.Cargo;
import no.clueless.opencargo.shared.applicability.EvaluationResult;
import no.clueless.opencargo.product_selection.engine.MinDimensionsRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MinDimensionsRuleTest {
    public static Stream<Arguments> evaluate_should_return_satisfied_despite_rotation() {
        return Stream.of(
                Arguments.of(new BigDecimal("16"), new BigDecimal("10"), new BigDecimal("1")),
                Arguments.of(new BigDecimal("16"), new BigDecimal("1"), new BigDecimal("10")),
                Arguments.of(new BigDecimal("10"), new BigDecimal("16"), new BigDecimal("1")),
                Arguments.of(new BigDecimal("10"), new BigDecimal("1"), new BigDecimal("16")),
                Arguments.of(new BigDecimal("1"), new BigDecimal("16"), new BigDecimal("10")),
                Arguments.of(new BigDecimal("1"), new BigDecimal("10"), new BigDecimal("16"))
        );
    }

    public static Stream<Arguments> evaluate_should_return_unsatisfied_despite_rotation() {
        return Stream.of(
                Arguments.of(new BigDecimal("14"), new BigDecimal("10"), new BigDecimal("1")),
                Arguments.of(new BigDecimal("14"), new BigDecimal("1"), new BigDecimal("10")),
                Arguments.of(new BigDecimal("10"), new BigDecimal("14"), new BigDecimal("1")),
                Arguments.of(new BigDecimal("10"), new BigDecimal("1"), new BigDecimal("14")),
                Arguments.of(new BigDecimal("1"), new BigDecimal("14"), new BigDecimal("10")),
                Arguments.of(new BigDecimal("1"), new BigDecimal("10"), new BigDecimal("14"))
        );
    }

    @Test
    void throw_when_minWidth_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new MinDimensionsRule(mock(), null, null, null));
    }

    @Test
    void throw_when_minWidth_is_negative() {
        assertThrows(IllegalArgumentException.class, () -> new MinDimensionsRule(mock(), new BigDecimal("-1.0"), null, null));
    }

    @Test
    void throw_when_minLength_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new MinDimensionsRule(mock(), BigDecimal.ONE, null, null));
    }

    @Test
    void throw_when_minLength_is_negative() {
        assertThrows(IllegalArgumentException.class, () -> new MinDimensionsRule(mock(), BigDecimal.ONE, new BigDecimal("-1.0"), null));
    }

    @Test
    void throw_when_minHeight_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new MinDimensionsRule(mock(), BigDecimal.ONE, BigDecimal.ONE, null));
    }

    @Test
    void throw_when_minHeight_is_negative() {
        assertThrows(IllegalArgumentException.class, () -> new MinDimensionsRule(mock(), BigDecimal.ONE, BigDecimal.ONE, new BigDecimal("-1.0")));
    }

    @Test
    void do_not_throw_when_args_are_valid() {
        assertDoesNotThrow(() -> new MinDimensionsRule(mock(), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE));
    }

    @ParameterizedTest
    @MethodSource
    void evaluate_should_return_satisfied_despite_rotation(BigDecimal width, BigDecimal length, BigDecimal height) {
        var sut   = new MinDimensionsRule(mock(), new BigDecimal("15.0"), new BigDecimal("10.0"), new BigDecimal("1.0"));
        var cargo = mock(Cargo.class);
        when(cargo.getWidth()).thenReturn(width);
        when(cargo.getHeight()).thenReturn(height);
        when(cargo.getLength()).thenReturn(length);
        var query = mock(ProductSelectionQuery.class);
        when(query.getCargo()).thenReturn(cargo);

        var actual = sut.evaluate(query);

        assertEquals(EvaluationResult.satisfied(), actual);
    }

    @ParameterizedTest
    @MethodSource
    void evaluate_should_return_unsatisfied_despite_rotation(BigDecimal width, BigDecimal length, BigDecimal height) {
        var sut = new MinDimensionsRule(mock(), new BigDecimal("15.0"), new BigDecimal("10.0"), new BigDecimal("1.0"));
        var cargo = mock(Cargo.class);
        when(cargo.getWidth()).thenReturn(width);
        when(cargo.getHeight()).thenReturn(height);
        when(cargo.getLength()).thenReturn(length);
        var query = mock(ProductSelectionQuery.class);
        when(query.getCargo()).thenReturn(cargo);

        var actual = sut.evaluate(query);

        assertEquals(EvaluationResult.unsatisfied("Package is too small on at least one axis"), actual);
    }
}