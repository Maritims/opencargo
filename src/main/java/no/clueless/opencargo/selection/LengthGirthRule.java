package no.clueless.opencargo.selection;

import no.clueless.opencargo.applicability.EvaluationResult;
import no.clueless.opencargo.ProductQuery;
import no.clueless.opencargo.bindings.LengthOrGirthRuleDTO;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

import java.math.BigDecimal;

public class LengthGirthRule extends RuleBase implements SelectionRule {
    private final BigDecimal maxLength;
    private final BigDecimal maxLengthAndGirthCombined;

    public LengthGirthRule(RuleMetadata metadata, BigDecimal maxLength, BigDecimal maxLengthAndGirthCombined) {
        super(metadata);
        this.maxLength                 = ArgumentExceptionHelper.throwIfNullOrNegative(maxLength, "maxLength");
        this.maxLengthAndGirthCombined = ArgumentExceptionHelper.throwIfNullOrNegative(maxLengthAndGirthCombined, "maxLength");

        if (maxLength.compareTo(maxLengthAndGirthCombined) > 0) {
            throw new IllegalArgumentException("maxLength cannot be greater than maxLengthAndGirthCombined");
        }
    }

    @Override
    public EvaluationResult evaluate(ProductQuery productQuery) {
        ArgumentExceptionHelper.throwIfNull(productQuery, "query");

        var cargo  = productQuery.getCargo();
        var length = cargo.getLength();

        if (length.compareTo(maxLength) > 0) {
            var girth                  = new BigDecimal("2.0").multiply(cargo.getWidth().add(cargo.getHeight()));
            var lengthAndGirthCombined = length.add(girth);

            if (lengthAndGirthCombined.compareTo(maxLengthAndGirthCombined) > 0) {
                return EvaluationResult.unsatisfied(String.format("The length (%s) is greater than the max length (%s), and the length and girth (%s) combined (%s) is greater than the max length and girth combined (%s)", length, maxLength, girth, lengthAndGirthCombined, maxLengthAndGirthCombined));
            }
        }

        return EvaluationResult.satisfied();
    }

    public static LengthGirthRule from(LengthOrGirthRuleDTO dto, RuleMetadata metadata) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        ArgumentExceptionHelper.throwIfNull(metadata, "metadata");

        return new LengthGirthRule(metadata, dto.getMaxLength(), dto.getMaxLengthAndGirthCombined());
    }
}
