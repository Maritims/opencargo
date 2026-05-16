package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface ServletAction {
    ServletActionResult process(HttpServletRequest request);
}
