package no.clueless.opencargo.pricing.domain.service.engine;

import no.clueless.opencargo.domain.model.applicability.EvaluationResult;
import no.clueless.opencargo.bindings.WeightLimitRuleDTO;
import no.clueless.opencargo.pricing.domain.model.policy.PricingRule;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.math.BigDecimal;
import java.util.Objects;

public class WeightLimitRule implements PricingRule {
    private final BigDecimal maxWeight;

    public WeightLimitRule(BigDecimal maxWeight) {
        this.maxWeight = ArgumentExceptionHelper.throwIfNullOrNegative(maxWeight, "maxWeight");
    }

    @Override
    public String getName() {
        return "WeightLimitRule: " + maxWeight;
    }

    @Override
    public EvaluationResult evaluate(PricingQuery query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");

        return query.getCargo().getWeight().compareTo(maxWeight) > 0 ?
                EvaluationResult.unsatisfied(String.format("The weight (%s) is greater than the max weight (%s)", query.getCargo().getWeight(), maxWeight)) :
                EvaluationResult.satisfied();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WeightLimitRule that = (WeightLimitRule) o;
        return Objects.equals(maxWeight, that.maxWeight);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maxWeight);
    }

    @Override
    public String toString() {
        return "WeightLimitRule{" +
                "maxWeight=" + maxWeight +
                '}';
    }

    public static WeightLimitRule from(WeightLimitRuleDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        return new WeightLimitRule(dto.getMaxWeight());
    }
}
