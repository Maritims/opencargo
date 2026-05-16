package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;

import java.util.function.BiFunction;

public abstract class ServletAction {
    protected <T> T getValidConvertedParamOrThrow(HttpServletRequest request, String paramName, BiFunction<String, String, T> converter) {
        if (request == null) {
            throw new IllegalArgumentException("request must not be null");
        }
        if (paramName == null || paramName.isBlank()) {
            throw new IllegalArgumentException("paramName must not be null or blank");
        }
        if (converter == null) {
            throw new IllegalArgumentException("converter must not be null");
        }

        var paramValue = request.getParameter(paramName);
        if (paramValue == null || paramValue.isBlank()) {
            throw StatusAwareServletException.badRequest("Parameter '" + paramName + "' cannot be null or blank");
        }
        return converter.apply(paramName, paramValue);
    }

    abstract ServletActionResult process(HttpServletRequest request);
}
