package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.application.ports.input.FindProductsUseCase;

import java.util.Objects;

public class FindProductsAction extends ServletAction {
    private final FindProductsUseCase findProductsUseCase;

    public FindProductsAction(FindProductsUseCase findProductsUseCase) {
        this.findProductsUseCase = Objects.requireNonNull(findProductsUseCase, "findProductsUseCase cannot be null");
    }

    @Override
    public ServletActionResult process(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }

        var products = findProductsUseCase.findProducts();
        return new JsonResult<>(products);
    }
}
