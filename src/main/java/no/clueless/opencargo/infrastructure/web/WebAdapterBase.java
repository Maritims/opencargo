package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public abstract class WebAdapterBase extends HttpServlet {
    private static final Pattern                ACTION_PATTERN = Pattern.compile("^/[a-zA-Z0-9_]+$");
    private final        Map<String, WebAction> actions        = new HashMap<>();

    public WebAdapterBase() {
    }

    protected abstract void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        registerActions((httpMethod, map) -> {
            for (var entry : actions.entrySet()) {
                if (entry.getKey() == null || entry.getKey().isBlank()) {
                    throw new IllegalStateException("An action route cannot be null or blank");
                }
                if (!ACTION_PATTERN.matcher(entry.getKey()).matches()) {
                    throw new IllegalStateException("An action route must match the pattern " + ACTION_PATTERN + ", but was " + entry.getKey());
                }

                actions.put(httpMethod.name() + ":" + entry.getKey(), entry.getValue());
            }
        });
    }

    final void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArgumentExceptionHelper.throwIfNull(request, "request");
        ArgumentExceptionHelper.throwIfNull(response, "response");

        var method    = request.getMethod();
        var pathInfo  = request.getPathInfo();
        var actionKey = String.format("%s:%s", method, pathInfo);

        if (!actions.containsKey(actionKey)) {
            throw new NoSuchElementException("No action registered for " + actionKey);
        }

        var action = actions.get(actionKey);
        var result = action.execute(request);

        result.render(response);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArgumentExceptionHelper.throwIfNull(request, "request");
        ArgumentExceptionHelper.throwIfNull(response, "response");

        try {
            processRequest(request, response);
        } catch (NoSuchElementException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}