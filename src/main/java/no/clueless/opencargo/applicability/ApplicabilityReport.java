package no.clueless.opencargo.applicability;

import no.clueless.opencargo.EvaluationResult;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ApplicabilityReport<T> {
    private final T           subject;
    private final boolean     applicable;
    private final Set<String> rejectionReasons;

    public ApplicabilityReport(T subject, Set<EvaluationResult> evaluationResults) {
        this.subject          = ArgumentExceptionHelper.throwIfNull(subject, "entity");
        this.rejectionReasons = ArgumentExceptionHelper.throwIfNullOrEmpty(evaluationResults, "evaluationResults")
                .stream()
                .filter(evaluationResult -> !evaluationResult.isSatisfied())
                .map(EvaluationResult::getReason)
                .collect(Collectors.toSet());
        this.applicable       = rejectionReasons.isEmpty();
    }

    public T getSubject() {
        return subject;
    }

    public boolean isApplicable() {
        return applicable;
    }

    public Set<String> getRejectionReasons() {
        return rejectionReasons;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApplicabilityReport<?> that = (ApplicabilityReport<?>) o;
        return applicable == that.applicable && Objects.equals(subject, that.subject) && Objects.equals(rejectionReasons, that.rejectionReasons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, applicable, rejectionReasons);
    }

    @Override
    public String toString() {
        return "ApplicabilityReport{" +
                "subject=" + subject +
                ", applicable=" + applicable +
                ", rejectionReasons=" + rejectionReasons +
                '}';
    }
}
