package no.clueless.opencargo.pricing;

import no.clueless.opencargo.PricingQuery;
import no.clueless.opencargo.EvaluationResult;

public interface PricingRule {
    String getName();

    EvaluationResult evaluate(PricingQuery query);
}
