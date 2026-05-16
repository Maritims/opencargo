package no.clueless.opencargo.infrastructure.web.validation;

import no.clueless.opencargo.domain.physical.WeightUnit;
import no.clueless.opencargo.infrastructure.web.StatusAwareServletException;

public class WeightUnitValidator {
    public static WeightUnit validate(String name, String value) {
        try {
            return WeightUnit.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw StatusAwareServletException.badRequest("Parameter '" + name + "' is not a valid weight unit");
        }
    }
}
