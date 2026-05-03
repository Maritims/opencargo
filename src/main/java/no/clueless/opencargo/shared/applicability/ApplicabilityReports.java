package no.clueless.opencargo.shared.applicability;

import no.clueless.opencargo.shared.Population;

import java.util.Objects;
import java.util.Set;

public final class ApplicabilityReports<T> {
    private final Population<T, Set<T>>                       approvals;
    private final Population<Rejection<T>, Set<Rejection<T>>> rejections;

    public ApplicabilityReports(Population<T, Set<T>> approvals, Population<Rejection<T>, Set<Rejection<T>>> rejections) {
        if (approvals == null && rejections == null) {
            throw new IllegalArgumentException("applicabilityReports and rejections cannot both be null at the same time");
        }
        this.approvals  = approvals;
        this.rejections = rejections;
    }

    public Population<T, Set<T>> getApprovals() {
        return approvals;
    }

    public Population<Rejection<T>, Set<Rejection<T>>> getRejections() {
        return rejections;
    }

    public boolean isApplicable() {
        return rejections == null && approvals != null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApplicabilityReports<?> that = (ApplicabilityReports<?>) o;
        return Objects.equals(approvals, that.approvals) && Objects.equals(rejections, that.rejections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(approvals, rejections);
    }

    @Override
    public String toString() {
        return "ApplicabilityReports{" +
                "applicabilityReports=" + approvals +
                ", rejections=" + rejections +
                '}';
    }
}
