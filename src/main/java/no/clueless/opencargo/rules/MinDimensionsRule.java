package no.clueless.opencargo.rules;

import no.clueless.opencargo.Query;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinDimensionsRule extends RuleBase implements Rule {
    private final BigDecimal minWidth;
    private final BigDecimal minLength;
    private final BigDecimal minHeight;

    public MinDimensionsRule(int id, Integer consignorId, Set<Integer> productIds, String number, String name, int priority, boolean isTerminal, BigDecimal minWidth, BigDecimal minLength, BigDecimal minHeight) {
        super(id, consignorId, productIds, number, name, priority, isTerminal);
        this.minWidth  = ArgumentExceptionHelper.throwIfNullOrNegative(minWidth, "minWidth");
        this.minLength = ArgumentExceptionHelper.throwIfNullOrNegative(minLength, "minLength");
        this.minHeight = ArgumentExceptionHelper.throwIfNullOrNegative(minHeight, "minHeight");
    }

    @Override
    public EvaluationResult evaluate(Query query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");

        var cargo    = query.getCargo();
        var required = Stream.of(minWidth, minLength, minHeight).sorted().collect(Collectors.toList());
        var actual   = Stream.of(cargo.getWidth(), cargo.getLength(), cargo.getHeight()).sorted().collect(Collectors.toList());

        for(var i = 0; i < 3; i++) {
            if(actual.get(i).compareTo(required.get(i)) < 0) {
                return EvaluationResult.unsatisfied("Package is too small on at least one axis");
            }
        }

        return EvaluationResult.satisfied();
    }
}
