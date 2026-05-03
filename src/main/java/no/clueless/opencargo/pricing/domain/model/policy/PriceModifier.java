package no.clueless.opencargo.pricing.domain.model.policy;

import no.clueless.opencargo.pricing.domain.service.engine.PricingQuery;

import java.math.BigDecimal;

public interface PriceModifier {
    String getName();

    boolean isApplicable(PricingQuery query);

    BigDecimal calculateDelta(PricingQuery query, BigDecimal amount);

    default BigDecimal modify(PricingQuery query, BigDecimal currentPrice) {
        return currentPrice.add(calculateDelta(query, currentPrice));
    }
}
