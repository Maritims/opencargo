package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.io.IOException;
import java.util.Objects;

public abstract class WebAction {
    private final String name;
    private final String description;

    protected WebAction(String name, String description) {
        this.name        = ArgumentExceptionHelper.throwIfNullOrBlank(name, "name");
        this.description = ArgumentExceptionHelper.throwIfNullOrBlank(description, "description");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract WebResult execute(HttpServletRequest request) throws IOException;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WebAction webAction = (WebAction) o;
        return Objects.equals(name, webAction.name) && Objects.equals(description, webAction.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "WebAction{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
