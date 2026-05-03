package no.clueless.opencargo.infrastructure;

import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentExceptionHelperTest {

    @Test
    void throwIfNull_should_throw_when_arg_is_null() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentExceptionHelper.throwIfNull(null, "test"));
    }

    @Test
    void throwIfNull_should_not_throw_when_arg_is_not_null() {
        assertDoesNotThrow(() -> ArgumentExceptionHelper.throwIfNull("", "test"));
    }

    @Test
    void throwIfNullOrBlank_should_throw_when_arg_is_null() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentExceptionHelper.throwIfNullOrBlank(null, "test"));
    }

    @Test
    void throwIfNullOrBlank_should_throw_when_arg_is_blank() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentExceptionHelper.throwIfNullOrBlank("", "test"));
    }

    @Test
    void throwIfNullOrBlank_should_not_throw_when_arg_is_not_null_nor_blank() {
        assertDoesNotThrow(() -> ArgumentExceptionHelper.throwIfNullOrBlank("foo", "test"));
    }

    @Test
    void throwIfNullOrEmpty_should_throw_when_arg_is_null() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentExceptionHelper.throwIfNullOrEmpty((Set) null, "test"));
    }

    @Test
    void throwIfNullOrEmpty_should_throw_when_arg_is_empty() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentExceptionHelper.throwIfNullOrEmpty(new HashSet<>(), "test"));
    }

    @Test
    void throwIfNullOrEmpty_should_not_throw_when_arg_is_null_nor_empty() {
        Set<String> set = new HashSet<>();
        set.add("foo");
        assertDoesNotThrow(() -> ArgumentExceptionHelper.throwIfNullOrEmpty(set, "test"));
    }

    @Test
    void throwIfNullOrNegative_should_throw_when_arg_is_null() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentExceptionHelper.throwIfNullOrNegative(null, "test"));
    }

    @Test
    void throwIfNullOrNegative_should_throw_when_arg_is_negative() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentExceptionHelper.throwIfNullOrNegative(new BigDecimal("-1.0"), "test"));
    }

    @Test
    void throwIfNullOrNegative_should_not_throw_when_arg_is_null_nor_negative() {
        assertDoesNotThrow(() -> ArgumentExceptionHelper.throwIfNullOrNegative(BigDecimal.ONE, "test"));
    }

    @Test
    void throwIfNegative_for_int_should_throw_when_arg_is_negative() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentExceptionHelper.throwIfNegative(-100, "test"));
    }

    @Test
    void throwIfNegative_for_int_should_not_throw_when_arg_is_not_negative() {
        assertDoesNotThrow(() -> ArgumentExceptionHelper.throwIfNegative(100, "test"));
    }

    @Test
    void throwIfNegative_for_BigDecimal_should_throw_when_arg_is_negative() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentExceptionHelper.throwIfNegative(new BigDecimal("-1.0"), "test"));
    }

    @Test
    void throwIfNegative_for_BigDecimal_should_not_throw_when_arg_is_null() {
        assertDoesNotThrow(() -> ArgumentExceptionHelper.throwIfNegative(null, "test"));
    }

    @Test
    void throwIfNegative_for_BigDecimal_should_not_throw_when_arg_is_not_negative() {
        assertDoesNotThrow(() -> ArgumentExceptionHelper.throwIfNegative(BigDecimal.ONE, "test"));
    }
}