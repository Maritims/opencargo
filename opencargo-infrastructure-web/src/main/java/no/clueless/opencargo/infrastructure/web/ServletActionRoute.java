package no.clueless.opencargo.infrastructure.web;

public final class ServletActionRoute {
    private final String        method;
    private final String        path;
    private final ServletAction action;

    ServletActionRoute(String method, String path, ServletAction action) {
        if(method == null) {
            throw new IllegalArgumentException("method cannot be null");
        }
        if(path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }
        if(action == null) {
            throw new IllegalArgumentException("action cannot be null");
        }

        switch (method) {
            case "GET":
            case "POST":
            case "PUT":
            case "DELETE":
                this.method = method;
                break;
            default:
                throw new IllegalArgumentException("method must be one of GET, POST, PUT or DELETE");
        }

        this.path   = path.startsWith("/") ? path : "/" + path;
        this.action = action;
    }

    static ServletActionRoute GET(String path, ServletAction action) {
        return new ServletActionRoute("GET", path, action);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getRouteKey() {
        return String.format("%s:%s", method, path);
    }

    public ServletAction getAction() {
        return action;
    }
}
