package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.application.dto.FindProductsQuery;
import no.clueless.opencargo.application.ports.input.FindEligibleProductsUseCase;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.physical.WeightUnit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class FindEligibleProductsAction implements ServletAction {
    private final FindEligibleProductsUseCase findEligibleProductsUseCase;

    public FindEligibleProductsAction(FindEligibleProductsUseCase findEligibleProductsUseCase) {
        this.findEligibleProductsUseCase = Objects.requireNonNull(findEligibleProductsUseCase, "findEligibleProductsUseCase cannot be null");
    }

    static final BiFunction<String, String, BigDecimal>   VALIDATING_BIG_DECIMAL_CONVERTER           = (paramName, paramValue) -> {
        try {
            return new BigDecimal(paramValue);
        } catch (NumberFormatException e) {
            throw FreightProductServletException.badRequest("Parameter '" + paramName + "' is not a valid number");
        }
    };
    static final BiFunction<String, String, WeightUnit>   VALIDATING_WEIGHT_UNIT_CONVERTER           = (paramName, paramValue) -> {
        try {
            return WeightUnit.valueOf(paramValue);
        } catch (IllegalArgumentException e) {
            throw FreightProductServletException.badRequest("Parameter '" + paramName + "' is not a valid weight unit");
        }
    };
    static final BiFunction<String, String, DistanceUnit> VALIDATING_DISTANCE_UNIT_CONVERTER         = (paramName, paramValue) -> {
        try {
            return DistanceUnit.valueOf(paramValue);
        } catch (IllegalArgumentException e) {
            throw FreightProductServletException.badRequest("Parameter '" + paramName + "' is not a valid distance unit");
        }
    };
    static final BiFunction<String, String, List<String>> VALIDATING_COMMA_SEPARATED_STRING_SPLITTER = (paramName, paramValue) -> {
        var shortCodes = paramValue.split(",");
        if (shortCodes.length == 0) {
            throw FreightProductServletException.badRequest("Parameter '" + paramName + "' must contain comma-separated ADR class short codes");
        }
        return List.of(shortCodes);
    };

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
            throw FreightProductServletException.badRequest("Parameter '" + paramName + "' cannot be null or blank");
        }
        return converter.apply(paramName, paramValue);
    }

    protected FindProductsQuery mapToQuery(HttpServletRequest request) throws FreightProductServletException {
        if (request == null) {
            throw new IllegalArgumentException("request must not be null");
        }

        var weightParamValue   = getValidConvertedParamOrThrow(request, "weight", VALIDATING_BIG_DECIMAL_CONVERTER);
        var weightUnit         = getValidConvertedParamOrThrow(request, "weightUnit", VALIDATING_WEIGHT_UNIT_CONVERTER);
        var width              = getValidConvertedParamOrThrow(request, "width", VALIDATING_BIG_DECIMAL_CONVERTER);
        var length             = getValidConvertedParamOrThrow(request, "length", VALIDATING_BIG_DECIMAL_CONVERTER);
        var height             = getValidConvertedParamOrThrow(request, "height", VALIDATING_BIG_DECIMAL_CONVERTER);
        var distanceUnit       = getValidConvertedParamOrThrow(request, "distanceUnit", VALIDATING_DISTANCE_UNIT_CONVERTER);
        var adrClassShortCodes = getValidConvertedParamOrThrow(request, "adrClassShortCodes", VALIDATING_COMMA_SEPARATED_STRING_SPLITTER);
        return new FindProductsQuery(weightParamValue, weightUnit, width, length, height, distanceUnit, adrClassShortCodes);
    }

    @Override
    public ServletActionResult process(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }
        if (!"GET".equals(request.getMethod())) {
            return new ErrorResult(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Only GET is supported");
        }

        var result = findEligibleProductsUseCase.findForCriteria(mapToQuery(request));
        return new JsonResult<>(result);
    }
}
