package no.clueless.opencargo.pricing;

import no.clueless.opencargo.EvaluationResult;
import no.clueless.opencargo.PricingQuery;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;

public class ProductRequirementRule implements PricingRule {
    private final int requiredProductId;

    public ProductRequirementRule(int requiredProductId) {
        this.requiredProductId = ArgumentExceptionHelper.throwIfNegative(requiredProductId, "requiredProductId");
    }

    @Override
    public String getName() {
        return "ProductRequirementRule: " + requiredProductId;
    }

    @Override
    public EvaluationResult evaluate(PricingQuery query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");

        return query.getProductIds().contains(requiredProductId) ?
                EvaluationResult.satisfied() :
                EvaluationResult.unsatisfied(String.format("The query does not contain the required product id %s", requiredProductId));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductRequirementRule that = (ProductRequirementRule) o;
        return requiredProductId == that.requiredProductId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(requiredProductId);
    }

    @Override
    public String toString() {
        return "ProductRequirementRule{" +
                "requiredProductId=" + requiredProductId +
                '}';
    }
}
