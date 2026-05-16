package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.criteria.Constraint;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FreightSurcharge {
    private final String                  description;
    private final SurchargeModifier       modifier;
    private final Map<String, Constraint> constraints;

    public FreightSurcharge(String description, SurchargeModifier modifier, List<Constraint> constraints) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or empty");
        }
        this.description = description;
        this.modifier    = Objects.requireNonNull(modifier, "modifier cannot be null");
        this.constraints = constraints == null ? Map.of() : constraints.stream().collect(Collectors.toMap(
                (Constraint constraint) -> {
                    var key = constraint.getClass().getSimpleName().replace("Constraint", "");
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    return key;
                },
                entry -> entry
        ));
    }

    public String getDescription() {
        return description;
    }

    public SurchargeModifier getModifier() {
        return modifier;
    }

    public Map<String, Constraint> getConstraints() {
        return constraints;
    }

    public boolean isApplicable(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        return constraints.entrySet().stream().allMatch(entry -> entry.getValue().isSatisfiedBy(parcel));
    }
}
