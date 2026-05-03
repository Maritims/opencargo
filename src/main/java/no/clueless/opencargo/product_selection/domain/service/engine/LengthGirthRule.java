package no.clueless.opencargo.product_selection.domain.service.engine;

import no.clueless.opencargo.domain.model.applicability.EvaluationResult;
import no.clueless.opencargo.bindings.LengthOrGirthRuleDTO;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.math.BigDecimal;

public class LengthGirthRule extends RuleBase implements Rule {
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
    public EvaluationResult evaluate(ProductSelectionQuery productSelectionQuery) {
        ArgumentExceptionHelper.throwIfNull(productSelectionQuery, "query");

        var cargo  = productSelectionQuery.getCargo();
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
