package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.infrastructure.config.ServiceLocator;

import java.io.IOException;
import java.util.Objects;

public class FreightProductApiServlet extends HttpServlet {
    private final ServletActionProcessor servletActionProcessor;

    public FreightProductApiServlet(ServletActionProcessor servletActionProcessor) {
        this.servletActionProcessor = Objects.requireNonNull(servletActionProcessor, "servletActionProcessor cannot be null");
    }

    @SuppressWarnings("unused")
    public FreightProductApiServlet() {
        this(ServiceLocator.getInstance().lookup(ServletActionProcessor.class).orElseThrow());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        servletActionProcessor.process(request, response);
    }
}
