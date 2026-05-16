package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.application.ports.input.FindEligibleProductsUseCase;

import java.io.IOException;
import java.util.Map;

public class ServletActionProcessor {
    private final Map<String, ServletAction> servletActions;

    public ServletActionProcessor(FindEligibleProductsUseCase findEligibleProductsUseCase) {
        if (findEligibleProductsUseCase == null) {
            throw new IllegalArgumentException("findEligibleProductsUseCase cannot be null");
        }

        this.servletActions = Map.of(
                "GET:/find-eligible-products", new FindEligibleProductsAction(findEligibleProductsUseCase)
        );
    }

    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var path = request.getPathInfo();
        if (path == null || path.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        var servletActionKey = String.format("%s:%s", request.getMethod(), path);
        if(!servletActions.containsKey(servletActionKey)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No route for method " + request.getMethod() + " and path " + path + " exists");
            return;
        }

        var servletAction = servletActions.get(servletActionKey);
        if (servletAction == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A null route exists for " + request.getMethod() + " and path " + request.getPathInfo());
            return;
        }

        try {
            servletAction.process(request).render(response);
        } catch (FreightProductServletException e) {
            new ErrorResult(e.getStatusCode(), e.getMessage()).render(response);
        }
    }
}
