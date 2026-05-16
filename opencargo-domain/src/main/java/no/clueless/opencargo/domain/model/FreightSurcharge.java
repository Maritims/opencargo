package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.criteria.Constraint;
import no.clueless.opencargo.domain.shared.PriceModifier;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FreightSurcharge {
    private final String        reason;
    private final PriceModifier modifier;
    private final Map<String, Constraint> constraints;

    public FreightSurcharge(String reason, PriceModifier modifier, List<Constraint> constraints) {
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or empty");
        }
        this.reason      = reason;
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

    public String getReason() {
        return reason;
    }

    public PriceModifier getModifier() {
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
