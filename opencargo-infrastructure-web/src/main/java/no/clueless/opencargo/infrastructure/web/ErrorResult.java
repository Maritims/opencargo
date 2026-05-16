package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

public class ErrorResult implements ServletActionResult {
    private final int    statusCode;
    private final String reason;

    public ErrorResult(int statusCode, String reason) {
        if (statusCode < 400 || statusCode > 500) {
            throw new IllegalArgumentException("statusCode must be between 400 and 500");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("reason cannot be null or blank");
        }
        this.statusCode = statusCode;
        this.reason     = reason;
    }

    @Override
    public void render(HttpServletResponse response) throws IOException {
        Objects.requireNonNull(response, "response must not be null").sendError(statusCode, reason);
    }
}
