package no.clueless.opencargo.domain.criteria;

import java.util.List;

public class ProductEligibilityResult {
    private final String         productName;
    private final boolean        eligible;
    private final List<Decision> decisionTrail;

    public ProductEligibilityResult(String productName, boolean eligible, List<Decision> decisionTrail) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("productName cannot be null or empty");
        }
        if (decisionTrail == null || decisionTrail.isEmpty()) {
            throw new IllegalArgumentException("decisionTrail cannot be null or empty");
        }
        this.productName   = productName;
        this.eligible      = eligible;
        this.decisionTrail = decisionTrail;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isEligible() {
        return eligible;
    }

    public List<Decision> getDecisionTrail() {
        return decisionTrail;
    }
}
