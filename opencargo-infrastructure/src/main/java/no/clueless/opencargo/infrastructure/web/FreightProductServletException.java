package no.clueless.opencargo.infrastructure.web;

public class FreightProductServletException extends RuntimeException {
    private final int statusCode;

    public FreightProductServletException(int statusCode, String message) {
        super(message);
        if (statusCode < 400 || statusCode > 500) {
            throw new IllegalArgumentException("statusCode must be between 400 and 500");
        }
        this.statusCode = statusCode;
    }

    public static FreightProductServletException badRequest(String message) {
        if (message == null || message.isBlank()) {
            throw new  IllegalArgumentException("message cannot be null or blank");
        }
        return new FreightProductServletException(400, message);
    }

    public static FreightProductServletException internalServerError(String message) {
        if (message == null || message.isBlank()) {
            throw new  IllegalArgumentException("message cannot be null or blank");
        }
        return new  FreightProductServletException(500, message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
