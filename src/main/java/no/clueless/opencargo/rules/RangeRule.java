package no.clueless.opencargo.rules;

import no.clueless.opencargo.ProductQuery;
import no.clueless.opencargo.util.ArgumentExceptionHelper;
import no.clueless.opencargo.Cargo;

import java.math.BigDecimal;
import java.util.function.Function;

public class RangeRule extends RuleBase {
    private final Function<Cargo, BigDecimal> valueResolver;
    private final BigDecimal                  min;
    private final BigDecimal                  max;

    public RangeRule(RuleMetadata metadata, Function<Cargo, BigDecimal> valueResolver, BigDecimal min, BigDecimal max) {
        super(metadata);
        this.valueResolver = ArgumentExceptionHelper.throwIfNull(valueResolver, "valueResolver");

        if(min == null && max == null) {
            throw new IllegalArgumentException("Either min or max must be specified");
        }
        if(min != null && max != null && min.compareTo(max) > 0) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }

        this.min           = min;
        this.max           = max;
    }

    @Override
    public EvaluationResult evaluate(ProductQuery productQuery) {
        ArgumentExceptionHelper.throwIfNull(productQuery, "query");

        Cargo      cargo = productQuery.getCargo();
        BigDecimal value = valueResolver.apply(cargo);

        boolean satisfied = (min != null && min.compareTo(value) <= 0) || (max != null && max.compareTo(value) >= 0);
        if (satisfied) {
            return EvaluationResult.satisfied();
        }

        if (min == null) {
            return EvaluationResult.unsatisfied(String.format("The value %s is greater than the maximum value of %s", value, max));
        }

        if (max == null) {
            return EvaluationResult.unsatisfied(String.format("The value %s isl less than the minimum value of %s", value, min));
        }

        return EvaluationResult.unsatisfied("The value " + value + " is not within the range of " + min + " and " + max);
    }
}
