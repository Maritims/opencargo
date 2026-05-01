package no.clueless.opencargo.util;

import java.math.BigDecimal;
import java.util.Collection;

public final class ArgumentExceptionHelper {
    public static <T> T throwIfNull(T arg, String argName) {
        if (arg == null) {
            throw new IllegalArgumentException("The argument " + argName + " cannot be null");
        }
        return arg;
    }

    public static String throwIfNullOrBlank(String arg, String argName) {
        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("The argument " + argName + " cannot be null or blank");
        }
        return arg;
    }

    public static <T, C extends Collection<T>> C throwIfNullOrEmpty(C arg, String argName) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException("The argument " + argName + " cannot be null or empty");
        }
        return arg;
    }

    public static <T> T[]  throwIfNullOrEmpty(T[] arg, String argName) {
        if (arg == null || arg.length == 0) {
            throw new IllegalArgumentException("The argument " + argName + " cannot be null or empty");
        }
        return arg;
    }

    public static BigDecimal throwIfNullOrNegative(BigDecimal arg, String argName) {
        if(arg == null || arg.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The argument " + argName + " cannot be null or negative");
        }
        return arg;
    }

    public static int throwIfNegative(int arg, String argName) {
        if (arg < 0) {
            throw new IllegalArgumentException("The argument " + argName + " cannot be negative");
        }
        return arg;
    }

    public static BigDecimal throwIfNegative(BigDecimal arg, String argName) {
        if (arg != null && arg.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The argument " + argName + " cannot be negative");
        }
        return arg;
    }

    public static int throwIfZeroOrNegative(int arg, String argName) {
        if(arg <= 0) {
            throw new IllegalArgumentException("The argument " + argName + " must be positive");
        }
        return arg;
    }
}
