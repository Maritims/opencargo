package no.clueless.opencargo.infrastructure.web;

public class StatusAwareServletException extends RuntimeException {
    private final int statusCode;

    public StatusAwareServletException(int statusCode, String message) {
        super(message);
        if (statusCode < 400 || statusCode > 500) {
            throw new IllegalArgumentException("statusCode must be between 400 and 500");
        }
        this.statusCode = statusCode;
    }

    public static StatusAwareServletException badRequest(String message) {
        if (message == null || message.isBlank()) {
            throw new  IllegalArgumentException("message cannot be null or blank");
        }
        return new StatusAwareServletException(400, message);
    }

    public static StatusAwareServletException internalServerError(String message) {
        if (message == null || message.isBlank()) {
            throw new  IllegalArgumentException("message cannot be null or blank");
        }
        return new StatusAwareServletException(500, message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
