package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;

import java.util.List;
import java.util.Objects;

public class AnyConstraint implements Constraint {
    private final List<Constraint> constraints;

    public AnyConstraint(Constraint... constraints) {
        if (constraints == null || constraints.length == 0) {
            throw new IllegalArgumentException("constraints cannot be null or empty");
        }
        this.constraints = List.of(constraints);
    }

    public AnyConstraint(List<Constraint> constraints) {
        if (constraints == null || constraints.isEmpty()) {
            throw new IllegalArgumentException("constraints cannot be null or empty");
        }
        this.constraints = List.copyOf(constraints);
    }

    public List<Constraint> getConstraints() {
        return List.copyOf(constraints);
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        return constraints.stream()
                .map(constraint -> constraint.evaluate(parcel))
                .filter(Decision::isSatisfied)
                .findFirst()
                .orElseGet(() -> new Decision(getClass().getSimpleName(), false, "None of the constraints were satisfied"));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AnyConstraint that = (AnyConstraint) o;
        return Objects.equals(constraints, that.constraints);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(constraints);
    }

    @Override
    public String toString() {
        return "AnyConstraint{" +
                "constraints=" + constraints +
                '}';
    }
}
