package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.application.ports.input.FindPriceUseCase;
import no.clueless.opencargo.domain.model.FreightProductId;

import java.util.Objects;

public class FindPriceAction extends ServletAction {
    private final FindPriceUseCase findPriceUseCase;

    public FindPriceAction(FindPriceUseCase findPriceUseCase) {
        this.findPriceUseCase = Objects.requireNonNull(findPriceUseCase, "findPriceUseCase cannot be null");
    }

    @Override
    ServletActionResult process(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }

        var freightProductId = getValidConvertedParamOrThrow(request, "productId", (name, value) -> {
            var param = request.getParameter(name);
            if (param == null || param.isBlank()) {
                throw StatusAwareServletException.badRequest("Parameter '" + name + "' cannot be null or blank");
            }
            return new FreightProductId(param);
        });
        var freightPrices = findPriceUseCase.findPrice(freightProductId);
        return new JsonResult<>(freightPrices);
    }
}
