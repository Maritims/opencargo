package no.clueless.opencargo.rules;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;

public final class EvaluationResult {
    private final boolean isSatisfied;
    private final String  reason;

    public EvaluationResult(boolean isSatisfied, String reason) {
        this.isSatisfied = isSatisfied;
        this.reason      = ArgumentExceptionHelper.throwIfNullOrBlank(reason, "reason");
    }

    public static EvaluationResult satisfied() {
        return new EvaluationResult(true, "Criteria met.");
    }

    public static EvaluationResult satisfied(String reason) {
        return new EvaluationResult(true, reason);
    }

    public static EvaluationResult unsatisfied(String reason) {
        return new EvaluationResult(false, reason);
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EvaluationResult that = (EvaluationResult) o;
        return isSatisfied == that.isSatisfied && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSatisfied, reason);
    }

    @Override
    public String toString() {
        return "EvaluationResult{" +
                "isSatisfied=" + isSatisfied +
                ", reason='" + reason + '\'' +
                '}';
    }
}
