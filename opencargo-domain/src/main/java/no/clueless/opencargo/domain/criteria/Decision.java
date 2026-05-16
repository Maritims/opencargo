package no.clueless.opencargo.domain.criteria;

import java.util.Objects;

public class Decision {
    private final String  constraintName;
    private final boolean satisfied;
    private final String  reason;

    public Decision(String constraintName, boolean satisfied, String reason) {
        if (constraintName == null || constraintName.isBlank()) {
            throw new IllegalArgumentException("constraintName cannot be null or blank");
        }
        if(!satisfied && (reason == null || reason.isBlank())) {
            throw new IllegalArgumentException("reason cannot be null or blank when satisfied is false");
        }
        this.constraintName = constraintName;
        this.satisfied      = satisfied;
        this.reason         = reason;
    }

    public static Decision satisfied(String constraintName) {
        if (constraintName == null || constraintName.isBlank()) {
            throw new IllegalArgumentException("Constraint name cannot be null or blank");
        }
        return new Decision(constraintName, true, null);
    }

    public static Decision unsatisfied(String constraintName, String reason) {
        if (constraintName == null || constraintName.isBlank()) {
            throw new IllegalArgumentException("Constraint name cannot be null or blank");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason cannot be null or blank");
        }
        return new Decision(constraintName, false, reason);
    }

    public String getConstraintName() {
        return constraintName;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Decision decision = (Decision) o;
        return satisfied == decision.satisfied && Objects.equals(constraintName, decision.constraintName) && Objects.equals(reason, decision.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constraintName, satisfied, reason);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", constraintName, reason);
    }
}
