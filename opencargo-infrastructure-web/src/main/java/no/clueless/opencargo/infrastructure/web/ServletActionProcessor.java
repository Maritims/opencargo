package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ServletActionProcessor {
    private final Map<String, ServletAction> servletActions;

    public ServletActionProcessor(Set<ServletActionRoute> servletActionRoutes) {
        this(Objects.requireNonNull(servletActionRoutes, "servletActionRoutes cannot be null").toArray(ServletActionRoute[]::new));
    }

    public ServletActionProcessor(ServletActionRoute... servletActionRoutes) {
        if (servletActionRoutes == null || servletActionRoutes.length == 0) {
            throw new IllegalArgumentException("servletActionRoutes must not be null or empty");
        }

        this.servletActions = Arrays.stream(servletActionRoutes)
                .collect(Collectors.toMap(
                        servletActionRoute -> String.format("%s:%s", servletActionRoute.getMethod(), servletActionRoute.getPath()),
                        ServletActionRoute::getAction
                ));
    }

    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var path = request.getPathInfo();
        if (path == null || path.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        var servletActionKey = String.format("%s:%s", request.getMethod(), path);
        if (!servletActions.containsKey(servletActionKey)) {
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
        } catch (StatusAwareServletException e) {
            new ErrorResult(e.getStatusCode(), e.getMessage()).render(response);
        }
    }
}
