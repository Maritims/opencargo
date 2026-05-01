package no.clueless.opencargo.applicability;

import no.clueless.opencargo.domain.Product;
import no.clueless.opencargo.rules.EvaluationResult;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ApplicabilityReport {
    private final Product product;
    private final boolean applicable;
    private final Set<String> rejectionReasons;

    public ApplicabilityReport(Product product, Set<EvaluationResult> evaluationResults) {
        this.product          = ArgumentExceptionHelper.throwIfNull(product, "product");
        this.rejectionReasons = ArgumentExceptionHelper.throwIfNullOrEmpty(evaluationResults, "evaluationResults")
                .stream()
                .filter(evaluationResult -> !evaluationResult.isSatisfied())
                .map(EvaluationResult::getReason)
                .collect(Collectors.toSet());
        this.applicable       = rejectionReasons.isEmpty();
    }

    public Product getProduct() {
        return product;
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
        ApplicabilityReport that = (ApplicabilityReport) o;
        return applicable == that.applicable && Objects.equals(product, that.product) && Objects.equals(rejectionReasons, that.rejectionReasons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, applicable, rejectionReasons);
    }

    @Override
    public String toString() {
        return "ApplicabilityReport{" +
                "product=" + product +
                ", applicable=" + applicable +
                ", rejectionReasons=" + rejectionReasons +
                '}';
    }
}
