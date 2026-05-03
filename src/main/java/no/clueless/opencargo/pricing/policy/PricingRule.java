package no.clueless.opencargo.pricing.policy;

import no.clueless.opencargo.shared.applicability.EvaluationResult;
import no.clueless.opencargo.pricing.PricingQuery;

public interface PricingRule {
    String getName();

    EvaluationResult evaluate(PricingQuery query);
}
