package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.criteria.Capability;
import no.clueless.opencargo.domain.criteria.Constraint;
import no.clueless.opencargo.domain.criteria.Decision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FreightProduct {
    private static final Logger                  log = LoggerFactory.getLogger(FreightProduct.class);
    private final        FreightProductId        id;
    private final        CarrierId               carrierId;
    private final        String                  name;
    private final        Map<String, Constraint> constraints;
    private final        FreightPrice            freightPrice;
    private final        Map<String, Capability> capabilities;

    public FreightProduct(FreightProductId id, CarrierId carrierId, String name, List<Constraint> constraints, FreightPrice freightPrice, List<Capability> capabilities) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.id           = Objects.requireNonNull(id, "id cannot be null");
        this.carrierId    = Objects.requireNonNull(carrierId, "carrierId cannot be null");
        this.name         = name;
        this.constraints  = constraints == null ? Map.of() : constraints.stream().collect(Collectors.toMap(
                (Constraint constraint) -> {
                    var key = constraint.getClass().getSimpleName().replace("Constraint", "");
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    return key;
                },
                entry -> entry
        ));
        this.freightPrice = Objects.requireNonNull(freightPrice, "freightPrice cannot be null");
        this.capabilities = capabilities == null ? Map.of() : capabilities.stream().collect(Collectors.toMap(
                (Capability capability) -> {
                    var key = capability.getClass().getSimpleName().replace("Capability", "");
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    return key;
                },
                entry -> entry
        ));
    }

    public boolean isEligible(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        var constraintDecisions = constraints.values()
                .stream()
                .map(constraint -> constraint.evaluate(parcel))
                .collect(Collectors.toList());
        var capabilityDecisions = capabilities.values()
                .stream()
                .map(capability -> capability.canHandle(parcel))
                .collect(Collectors.toList());

        var decisions = Stream.of(constraintDecisions, capabilityDecisions)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        log.info("Decisions: {}", decisions.stream()
                .filter(decision -> !decision.isSatisfied())
                .map(Object::toString)
                .collect(Collectors.joining(", ")));

        return decisions.stream()
                .allMatch(Decision::isSatisfied);
    }

    public FreightProductId getId() {
        return id;
    }

    public CarrierId getCarrierId() {
        return carrierId;
    }

    public String getName() {
        return name;
    }

    public Map<String, Constraint> getConstraints() {
        return constraints;
    }

    public FreightPrice getFreightPrice() {
        return freightPrice;
    }

    public Map<String, Capability> getCapabilities() {
        return capabilities;
    }
}
