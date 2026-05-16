package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@FunctionalInterface
public interface ServletActionResult {
    void render(HttpServletResponse response) throws IOException;
}
