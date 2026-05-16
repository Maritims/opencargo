package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.criteria.Constraint;
import no.clueless.opencargo.domain.criteria.Decision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FreightProduct {
    private static final Logger                  log = LoggerFactory.getLogger(FreightProduct.class);
    private final        FreightProductId        id;
    private final        String                  name;
    private final        Map<String, Constraint> constraints;

    public FreightProduct(FreightProductId id, String name, List<Constraint> constraints) {
        this.id          = Objects.requireNonNull(id);
        this.name        = Objects.requireNonNull(name);
        this.constraints = constraints == null ? Map.of() : constraints.stream().collect(Collectors.toMap(
                (Constraint constraint) -> {
                    var foo = constraint.getClass().getSimpleName().replace("Constraint", "");
                    foo = foo.substring(0, 1).toLowerCase() + foo.substring(1);
                    return foo;
                },
                entry -> entry
        ));
    }

    public boolean isEligible(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        var decisions = constraints.values()
                .stream()
                .map(constraint -> constraint.evaluate(parcel))
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

    public String getName() {
        return name;
    }

    public Map<String, Constraint> getConstraints() {
        return constraints;
    }
}
