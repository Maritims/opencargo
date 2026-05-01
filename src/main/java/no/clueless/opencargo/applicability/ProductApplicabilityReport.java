package no.clueless.opencargo.applicability;

import no.clueless.opencargo.domain.Products;

import java.util.Objects;

public final class ProductApplicabilityReport {
    private final Products   applicabilityReports;
    private final Rejections rejections;

    public ProductApplicabilityReport(Products applicabilityReports, Rejections rejections) {
        if (applicabilityReports == null && rejections == null) {
            throw new IllegalArgumentException("applicabilityReports and rejections cannot both be null at the same time");
        }
        this.applicabilityReports = applicabilityReports;
        this.rejections           = rejections;
    }

    public Products getApplicabilityReports() {
        return applicabilityReports;
    }

    public Rejections getRejections() {
        return rejections;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductApplicabilityReport that = (ProductApplicabilityReport) o;
        return Objects.equals(applicabilityReports, that.applicabilityReports) && Objects.equals(rejections, that.rejections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicabilityReports, rejections);
    }

    @Override
    public String toString() {
        return "ProductApplicabilityReport{" +
                "applicabilityReports=" + applicabilityReports +
                ", rejections=" + rejections +
                '}';
    }
}
