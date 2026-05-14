package no.clueless.opencargo.domain.criteria;

public class Decision {
    private final String  constraintName;
    private final boolean satisfied;
    private final String  reason;

    public Decision(String constraintName, boolean satisfied, String reason) {
        this.constraintName = constraintName;
        this.satisfied      = satisfied;
        this.reason         = reason;
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
}
