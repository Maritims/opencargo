package no.clueless.opencargo.infrastructure.web.validation;

import no.clueless.opencargo.infrastructure.web.StatusAwareServletException;

import java.util.List;

public class CommaSeparatedStringValidator {
    public static List<String> validate(String name, String value) {
        var shortCodes = value.split(",");
        if (shortCodes.length == 0) {
            throw StatusAwareServletException.badRequest("Parameter '" + name + "' must contain comma-separated ADR class short codes");
        }
        return List.of(shortCodes);
    }
}
