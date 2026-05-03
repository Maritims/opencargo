package no.clueless.opencargo.pricing.domain.model.policy;

import no.clueless.opencargo.shared.applicability.EvaluationResult;
import no.clueless.opencargo.pricing.domain.service.engine.PricingQuery;

public interface PricingRule {
    String getName();

    EvaluationResult evaluate(PricingQuery query);
}
