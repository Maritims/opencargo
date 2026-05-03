package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public abstract class WebAdapterBase extends HttpServlet {
    private static final Logger                 log            = LoggerFactory.getLogger(WebAdapterBase.class);
    private static final Pattern                PREFIX_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\-]+$");
    private static final Pattern                ACTION_PATTERN = Pattern.compile("^/[a-zA-Z0-9_\\-]+$");
    private static final Map<String, WebAction> actions        = new ConcurrentHashMap<>();
    private final        String                 prefix;

    public WebAdapterBase(String prefix) {
        this.prefix = ArgumentExceptionHelper.throwIfNullOrBlank(prefix, "prefix");

        if (!PREFIX_PATTERN.matcher(prefix).matches()) {
            throw new IllegalArgumentException("Prefix must match the pattern " + PREFIX_PATTERN + ", but was " + prefix);
        }
    }

    protected abstract void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions);

    protected final Set<String> getActionKeys() {
        return Set.copyOf(actions.keySet());
    }

    protected final WebAction getAction(String actionKey) {
        ArgumentExceptionHelper.throwIfNullOrBlank(actionKey, "actionKey");
        return actions.get(actionKey);
    }

    final void registerAction(HttpMethod method, String route, WebAction action) {
        if (route == null || route.isBlank()) {
            throw new IllegalStateException("An action route cannot be null or blank");
        }
        if (!ACTION_PATTERN.matcher(route).matches()) {
            throw new IllegalStateException("An action route must match the pattern " + ACTION_PATTERN + ", but was " + route);
        }

        var key = String.format("%s:%s:%s", prefix, method.name(), route);
        actions.put(key, action);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        log.info("Initializing web adapter with prefix {}", prefix);

        registerActions((httpMethod, map) -> map.forEach((key, value) -> registerAction(httpMethod, key, value)));
    }

    final void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArgumentExceptionHelper.throwIfNull(request, "request");
        ArgumentExceptionHelper.throwIfNull(response, "response");

        var method    = request.getMethod();
        var pathInfo  = request.getPathInfo();
        var actionKey = String.format("%s:%s:%s", prefix, method, pathInfo);

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