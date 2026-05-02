package no.clueless.opencargo.applicability;

import java.util.Objects;

public final class ApplicabilityReports<T> {
    private final Iterable<T>   applicabilityReports;
    private final Rejections<T> rejections;

    public ApplicabilityReports(Iterable<T> applicabilityReports, Rejections<T> rejections) {
        if (applicabilityReports == null && rejections == null) {
            throw new IllegalArgumentException("applicabilityReports and rejections cannot both be null at the same time");
        }
        this.applicabilityReports = applicabilityReports;
        this.rejections           = rejections;
    }

    public Iterable<T> getApplicabilityReports() {
        return applicabilityReports;
    }

    public Rejections<T> getRejections() {
        return rejections;
    }

    public boolean isApplicable() {
        return rejections == null && applicabilityReports != null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApplicabilityReports<?> that = (ApplicabilityReports<?>) o;
        return Objects.equals(applicabilityReports, that.applicabilityReports) && Objects.equals(rejections, that.rejections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicabilityReports, rejections);
    }

    @Override
    public String toString() {
        return "ApplicabilityReports{" +
                "applicabilityReports=" + applicabilityReports +
                ", rejections=" + rejections +
                '}';
    }
}
