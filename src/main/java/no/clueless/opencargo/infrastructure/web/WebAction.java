package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface WebAction {
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
