package no.clueless.opencargo.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import no.clueless.opencargo.infrastructure.web.*;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class HealthWebAdapter extends WebAdapterBase {
    public HealthWebAdapter() {
        super("health");
    }

    @Override
    protected void registerActions(BiConsumer<HttpMethod, Map<String, WebAction>> actions) {
        actions.accept(HttpMethod.GET, Map.of(
                "/api-routes", new WebAction("api-routes", "Returns all available api routes") {
                    @Override
                    public WebResult execute(HttpServletRequest request) {
                        var payload = getActionKeys()
                                .stream()
                                .map(actionKey -> Map.entry(actionKey, getAction(actionKey)))
                                .collect(Collectors.groupingBy(entry -> entry.getKey().split(":")[0],
                                        Collectors.mapping(entry -> entry, Collectors.toMap(
                                                entry -> entry.getKey().split(":")[2],
                                                entry -> Map.of(
                                                        "method", entry.getKey().split(":")[1],
                                                        "name", entry.getValue().getName(),
                                                        "description", entry.getValue().getDescription()
                                                )
                                        ))
                                ));
                        return JsonResult.ok(payload);
                    }
                }));
    }
}
