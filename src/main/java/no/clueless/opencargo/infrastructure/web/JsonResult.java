package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.JsonMarshaller;

import java.io.IOException;

public class JsonResult<T> implements WebResult {
    private final int statusCode;
    private final T   content;

    public JsonResult(int statusCode, T content) {
        this.statusCode = ArgumentExceptionHelper.throwIfNegative(statusCode, "statusCode");
        this.content    = ArgumentExceptionHelper.throwIfNull(content, "content");
    }

    public static <T> JsonResult<T> ok(T content) {
        return new JsonResult<>(200, content);
    }

    @Override
    public void render(HttpServletResponse response) throws IOException {
        ArgumentExceptionHelper.throwIfNull(response, "response");

        response.setContentType("application/json");
        response.setStatus(statusCode);
        response.setCharacterEncoding("UTF-8");

        JsonMarshaller.marshal(content, response.getOutputStream());
    }
}
