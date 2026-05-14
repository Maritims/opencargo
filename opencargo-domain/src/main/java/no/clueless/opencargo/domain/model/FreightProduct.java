package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.criteria.Constraint;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FreightProduct {
    private final UUID             id;
    private final String           name;
    private final List<Constraint> constraints;

    public FreightProduct(UUID id, String name, List<Constraint> constraints) {
        this.id          = Objects.requireNonNull(id);
        this.name        = Objects.requireNonNull(name);
        this.constraints = constraints == null ? List.of() : List.copyOf(constraints);
    }

    public boolean isEligible(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        return constraints.stream().allMatch(c -> c.isSatisfiedBy(parcel));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }
}
