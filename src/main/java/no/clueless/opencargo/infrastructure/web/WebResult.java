package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface WebResult {
    void render(HttpServletResponse response) throws IOException;
}
