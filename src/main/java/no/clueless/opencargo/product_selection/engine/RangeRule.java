package no.clueless.opencargo.product_selection.engine;

import no.clueless.opencargo.shared.applicability.EvaluationResult;
import no.clueless.opencargo.bindings.RangeRuleDTO;
import no.clueless.opencargo.shared.cargo.Cargo;
import no.clueless.opencargo.product_selection.ProductSelectionQuery;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

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
    public EvaluationResult evaluate(ProductSelectionQuery productSelectionQuery) {
        ArgumentExceptionHelper.throwIfNull(productSelectionQuery, "query");

        Cargo      cargo = productSelectionQuery.getCargo();
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

    public static RangeRule from(RangeRuleDTO dto, RuleMetadata metadata, String tagName) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        ArgumentExceptionHelper.throwIfNull(metadata, "metadata");
        ArgumentExceptionHelper.throwIfNullOrBlank(tagName, "tagName");

        Function<Cargo, BigDecimal> valueResolver;

        switch (tagName) {
            case "weightRule":
                valueResolver = Cargo::getWeight;
                break;
            case "widthRule":
                valueResolver = Cargo::getWidth;
                break;
            case "lengthRule":
                valueResolver = Cargo::getLength;
                break;
            case "heightRule":
                valueResolver = Cargo::getHeight;
                break;
            default:
                throw new IllegalArgumentException("Unknown tagName: " + tagName);
        }

        return new RangeRule(metadata, valueResolver, dto.getMinInclusive(), dto.getMaxExclusive());
    }
}
