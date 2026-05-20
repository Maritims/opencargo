package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.application.dto.FindProductsQuery;
import no.clueless.opencargo.application.ports.input.FindEligibleProductsUseCase;
import no.clueless.opencargo.infrastructure.web.validation.BigDecimalValidator;
import no.clueless.opencargo.infrastructure.web.validation.CommaSeparatedStringValidator;
import no.clueless.opencargo.infrastructure.web.validation.DistanceUnitValidator;
import no.clueless.opencargo.infrastructure.web.validation.WeightUnitValidator;

import java.util.Objects;

public class FindEligibleProductsAction extends ServletAction {
    private final FindEligibleProductsUseCase findEligibleProductsUseCase;

    public FindEligibleProductsAction(FindEligibleProductsUseCase findEligibleProductsUseCase) {
        this.findEligibleProductsUseCase = Objects.requireNonNull(findEligibleProductsUseCase, "findEligibleProductsUseCase cannot be null");
    }

    protected FindProductsQuery mapToQuery(HttpServletRequest request) throws StatusAwareServletException {
        if (request == null) {
            throw new IllegalArgumentException("request must not be null");
        }

        var weightParamValue           = getValidConvertedParamOrThrow(request, "weight", BigDecimalValidator::validate);
        var weightUnit                 = getValidConvertedParamOrThrow(request, "weightUnit", WeightUnitValidator::validate);
        var width                      = getValidConvertedParamOrThrow(request, "width", BigDecimalValidator::validate);
        var length                     = getValidConvertedParamOrThrow(request, "length", BigDecimalValidator::validate);
        var height                     = getValidConvertedParamOrThrow(request, "height", BigDecimalValidator::validate);
        var distanceUnit               = getValidConvertedParamOrThrow(request, "distanceUnit", DistanceUnitValidator::validate);
        var adrClassShortCodes         = getValidConvertedParamOrThrow(request, "adrClassShortCodes", CommaSeparatedStringValidator::validate);
        var acceptableCarrierIds       = getValidConvertedParamOrThrow(request, "acceptableCarrierIds", CommaSeparatedStringValidator::validate);
        var unacceptableCarrierIds     = getValidConvertedParamOrThrow(request, "unacceptableCarrierIds", CommaSeparatedStringValidator::validate);
        var requiredHandlingDirectives = getValidConvertedParamOrThrow(request, "requiredHandlingDirectives", CommaSeparatedStringValidator::validate);

        return new FindProductsQuery(
                weightParamValue,
                weightUnit,
                width,
                length,
                height,
                distanceUnit,
                adrClassShortCodes,
                acceptableCarrierIds,
                unacceptableCarrierIds,
                requiredHandlingDirectives
        );
    }

    @Override
    public ServletActionResult process(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }
        if (!"GET".equals(request.getMethod())) {
            return new ErrorResult(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Only GET is supported");
        }

        var result = findEligibleProductsUseCase.findEligibleProducts(mapToQuery(request));
        return new JsonResult<>(result);
    }
}
