package no.clueless.opencargo.rules;

import no.clueless.opencargo.Query;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

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
    public EvaluationResult evaluate(Query query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");

        var cargo  = query.getCargo();
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
}
