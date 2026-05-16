package no.clueless.opencargo.infrastructure.web.validation;

import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.infrastructure.web.StatusAwareServletException;

public class DistanceUnitValidator {
    public static DistanceUnit validate(String name, String value) {
        try {
            return DistanceUnit.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw StatusAwareServletException.badRequest("Parameter '" + name + "' is not a valid distance unit");
        }
    }
}
