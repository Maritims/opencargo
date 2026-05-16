package no.clueless.opencargo.infrastructure.web.validation;

import no.clueless.opencargo.infrastructure.web.StatusAwareServletException;

import java.math.BigDecimal;

public class BigDecimalValidator {
    public static BigDecimal validate(String name, String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw StatusAwareServletException.badRequest("Parameter '" + name + "' is not a valid number");
        }
    }
}
