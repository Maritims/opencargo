package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class JsonAction<T> implements WebAction {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final        Class<T>     targetClass;

    protected JsonAction(Class<T> targetClass) {
        this.targetClass = ArgumentExceptionHelper.throwIfNull(targetClass, "targetClass");
    }

    protected T getBody(HttpServletRequest request) throws IOException {
        ArgumentExceptionHelper.throwIfNull(request, "request");
        return OBJECT_MAPPER.readValue(request.getReader(), targetClass);
    }
}
