package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.domain.shared.ServiceLocator;

import java.io.IOException;
import java.util.Objects;

public final class ProcessorServlet extends HttpServlet {
    private final ServletActionProcessor servletActionProcessor;

    public ProcessorServlet(ServletActionProcessor servletActionProcessor) {
        this.servletActionProcessor = Objects.requireNonNull(servletActionProcessor, "servletActionProcessor cannot be null");
    }

    @SuppressWarnings("unused")
    public ProcessorServlet() {
        this(ServiceLocator.getInstance().lookup(ServletActionProcessor.class).orElseThrow());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        servletActionProcessor.process(request, response);
    }
}
