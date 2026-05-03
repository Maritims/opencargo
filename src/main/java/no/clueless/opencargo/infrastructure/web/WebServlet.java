package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;
import no.clueless.opencargo.selection.RuleEngine;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebServlet extends HttpServlet {
    private final Map<String, WebAction> commands = new ConcurrentHashMap<>();
    private final RuleEngine             ruleEngine;

    public WebServlet(RuleEngine ruleEngine) {
        this.ruleEngine = ArgumentExceptionHelper.throwIfNull(ruleEngine, "ruleEngine");
    }

    @Override
    public void init(ServletConfig config) {
        commands.put("POST:/product-query", new ProductQueryAction(ruleEngine));
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArgumentExceptionHelper.throwIfNull(request, "request");
        ArgumentExceptionHelper.throwIfNull(response, "response");

        var pathInfo = request.getPathInfo();
        var method   = request.getMethod();
        var command  = commands.get(String.format("%s:%s", method, pathInfo));

        if (command == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        command.execute(request, response);
    }
}
