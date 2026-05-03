package no.clueless.opencargo.infrastructure.marshalling;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JsonMarshaller {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T unmarshal(InputStream is, Class<T> clazz) {
        ArgumentExceptionHelper.throwIfNull(is, "is");
        ArgumentExceptionHelper.throwIfNull(clazz, "clazz");
        return objectMapper.convertValue(is, clazz);
    }

    public static <T> void marshal(T source, OutputStream os) throws IOException {
        ArgumentExceptionHelper.throwIfNull(source, "source");
        ArgumentExceptionHelper.throwIfNull(os, "os");
        objectMapper.writeValue(os, source);
    }
}
