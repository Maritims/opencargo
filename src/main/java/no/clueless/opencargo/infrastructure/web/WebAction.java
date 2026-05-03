package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface WebAction {
    WebResult execute(HttpServletRequest request) throws IOException;
}
