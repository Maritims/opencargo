package no.clueless.opencargo.infrastructure.config;

import java.io.InputStream;

public interface AppConfig {
    InputStream getResourceAsStream(String path);
}
