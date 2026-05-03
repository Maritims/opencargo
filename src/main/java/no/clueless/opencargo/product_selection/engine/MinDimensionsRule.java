package no.clueless.opencargo.product_selection.engine;

import no.clueless.opencargo.shared.applicability.EvaluationResult;
import no.clueless.opencargo.bindings.MinDimensionsRuleDTO;
import no.clueless.opencargo.product_selection.ProductSelectionQuery;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinDimensionsRule extends RuleBase implements Rule {
    private final BigDecimal minWidth;
    private final BigDecimal minLength;
    private final BigDecimal minHeight;

    public MinDimensionsRule(RuleMetadata metadata, BigDecimal minWidth, BigDecimal minLength, BigDecimal minHeight) {
        super(metadata);
        this.minWidth  = ArgumentExceptionHelper.throwIfNullOrNegative(minWidth, "minWidth");
        this.minLength = ArgumentExceptionHelper.throwIfNullOrNegative(minLength, "minLength");
        this.minHeight = ArgumentExceptionHelper.throwIfNullOrNegative(minHeight, "minHeight");
    }

    @Override
    public EvaluationResult evaluate(ProductSelectionQuery productSelectionQuery) {
        ArgumentExceptionHelper.throwIfNull(productSelectionQuery, "query");

        var cargo    = productSelectionQuery.getCargo();
        var required = Stream.of(minWidth, minLength, minHeight).sorted().collect(Collectors.toList());
        var actual   = Stream.of(cargo.getWidth(), cargo.getLength(), cargo.getHeight()).sorted().collect(Collectors.toList());

        for(var i = 0; i < 3; i++) {
            if(actual.get(i).compareTo(required.get(i)) < 0) {
                return EvaluationResult.unsatisfied("Package is too small on at least one axis");
            }
        }

        return EvaluationResult.satisfied();
    }

    public static MinDimensionsRule from(MinDimensionsRuleDTO dto, RuleMetadata metadata) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        ArgumentExceptionHelper.throwIfNull(metadata, "metadata");

        return new MinDimensionsRule(metadata, dto.getMinWidth(), dto.getMinLength(), dto.getMinHeight());
    }
}
