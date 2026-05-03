package no.clueless.opencargo.product_selection.domain.srevice.engine;

import no.clueless.opencargo.domain.model.Cargo;
import no.clueless.opencargo.domain.model.applicability.EvaluationResult;
import no.clueless.opencargo.product_selection.domain.service.engine.LengthGirthRule;
import no.clueless.opencargo.product_selection.domain.service.engine.ProductSelectionQuery;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LengthGirthRuleTest {
    @Test
    void throw_when_maxLength_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new LengthGirthRule(mock(), null, null));
    }

    @Test
    void throw_when_maxLength_is_negative() {
        assertThrows(IllegalArgumentException.class, () -> new LengthGirthRule(mock(), new BigDecimal("-1.0"), null));
    }

    @Test
    void throw_when_maxLengthAndGirthCombined_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new LengthGirthRule(mock(), BigDecimal.ONE, null));
    }

    @Test
    void throw_when_maxLengthAndGirthCombined_is_negative() {
        assertThrows(IllegalArgumentException.class, () -> new LengthGirthRule(mock(), BigDecimal.ONE, new BigDecimal("-1.0")));
    }

    @Test
    void throw_when_maxLength_is_less_than_maxLengthAndGirthCombined() {
        assertThrows(IllegalArgumentException.class, () -> new LengthGirthRule(mock(), BigDecimal.TEN, BigDecimal.ONE));
    }

    @Test
    void do_not_throw_when_args_are_valid() {
        assertDoesNotThrow(() -> new LengthGirthRule(mock(), BigDecimal.ONE, BigDecimal.TEN));
    }

    @Test
    void evaluate_should_return_unsatisfied_when_length_exceeds_maximum() {
        var sut   = new LengthGirthRule(mock(), new BigDecimal("240.0"), new BigDecimal("360.0"));
        var cargo = mock(Cargo.class);
        when(cargo.getLength()).thenReturn(new BigDecimal("245.0"));
        when(cargo.getWidth()).thenReturn(new BigDecimal("245.0"));
        when(cargo.getHeight()).thenReturn(new BigDecimal("245.0"));
        var query = mock(ProductSelectionQuery.class);
        when(query.getCargo()).thenReturn(cargo);

        var actual = sut.evaluate(query);

        assertEquals(EvaluationResult.unsatisfied("The length (245.0) is greater than the max length (240.0), and the length and girth (980.00) combined (1225.00) is greater than the max length and girth combined (360.0)"), actual);
    }

    @Test
    void evaluate_should_return_satisfied_when_length_is_equal_to_maximum() {
        var sut = new LengthGirthRule(mock(), new BigDecimal("240.0"), new BigDecimal("360.0"));
        var cargo = mock(Cargo.class);
        when(cargo.getLength()).thenReturn(new BigDecimal("240.0"));
        var query = mock(ProductSelectionQuery.class);
        when(query.getCargo()).thenReturn(cargo);

        var actual = sut.evaluate(query);

        assertTrue(actual.isSatisfied());
    }

    @Test
    void evaluate_should_return_satisfied_when_length_is_less_than_maximum() {
        var sut = new LengthGirthRule(mock(), new BigDecimal("240.0"), new BigDecimal("360.0"));
        var cargo = mock(Cargo.class);
        when(cargo.getLength()).thenReturn(new BigDecimal("235.0"));
        var query = mock(ProductSelectionQuery.class);
        when(query.getCargo()).thenReturn(cargo);

        var actual = sut.evaluate(query);

        assertTrue(actual.isSatisfied());
    }

    @Test
    void evaluate_should_return_satisfied_when_length_plus_girth_is_equal_to_maximum() {
        var sut = new LengthGirthRule(mock(), new BigDecimal("240.0"), new BigDecimal("360.0"));
        var cargo = mock(Cargo.class);
        when(cargo.getLength()).thenReturn(new BigDecimal("260.0"));
        when(cargo.getWidth()).thenReturn(new BigDecimal("15.0"));
        when(cargo.getHeight()).thenReturn(new BigDecimal("15.0"));
        var query = mock(ProductSelectionQuery.class);
        when(query.getCargo()).thenReturn(cargo);

        var actual = sut.evaluate(query);

        assertEquals(EvaluationResult.satisfied(),  actual);
    }

    @Test
    void evaluate_should_return_satisfied_when_length_plus_girth_is_less_than_maximum() {
        var sut = new LengthGirthRule(mock(), new BigDecimal("240.0"), new BigDecimal("360.0"));
        var cargo = mock(Cargo.class);
        when(cargo.getLength()).thenReturn(new BigDecimal("235.0"));
        when(cargo.getWidth()).thenReturn(new BigDecimal("15.0"));
        when(cargo.getHeight()).thenReturn(new BigDecimal("15.0"));
        var query = mock(ProductSelectionQuery.class);
        when(query.getCargo()).thenReturn(cargo);

        var actual = sut.evaluate(query);

        assertEquals(EvaluationResult.satisfied(),  actual);
    }
}