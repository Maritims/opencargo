package no.clueless.opencargo.rules;

import no.clueless.opencargo.util.ArgumentExceptionHelper;
import no.clueless.opencargo.Cargo;
import no.clueless.opencargo.Query;

import java.math.BigDecimal;
import java.util.Set;

public abstract class ScalarRuleBase extends RuleBase implements ScalarRule {
    private final BigDecimal min;
    private final BigDecimal max;

    protected ScalarRuleBase(int id, Integer consignorId, Set<Integer> productIds, String number, String name, int priority, boolean isTerminal, BigDecimal min, BigDecimal max) {
        super(id, consignorId, productIds, number, name, priority, isTerminal);
        this.min = min;
        this.max = max;
    }

    @Override
    public EvaluationResult evaluate(Query query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");

        Cargo      cargo = query.getCargo();
        BigDecimal value = resolveValue(cargo);

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
