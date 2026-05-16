package no.clueless.opencargo.infrastructure.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class JsonResult<T> implements ServletActionResult {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger       log    = LoggerFactory.getLogger(JsonResult.class);
    private final        T            payload;

    public JsonResult(T payload) {
        this.payload = Objects.requireNonNull(payload, "payload cannot be null");
    }

    @Override
    public void render(HttpServletResponse response) throws IOException {
        if (response == null) {
            throw new IllegalArgumentException("response cannot be null");
        }

        String json;
        try {
            json = MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize payload", e);
            throw FreightProductServletException.internalServerError("Failed to serialize payload");
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
