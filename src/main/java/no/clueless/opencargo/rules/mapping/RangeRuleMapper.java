package no.clueless.opencargo.rules.mapping;

import no.clueless.opencargo.Cargo;
import no.clueless.opencargo.bindings.RangeRuleDTO;
import no.clueless.opencargo.bindings.RuleBaseDTO;
import no.clueless.opencargo.rules.RangeRule;
import no.clueless.opencargo.rules.RuleMetadata;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.math.BigDecimal;
import java.util.function.Function;

public class RangeRuleMapper implements RuleMapper<RangeRuleDTO, RangeRule> {
    @Override
    public Class<RangeRuleDTO> getDTOClass() {
        return RangeRuleDTO.class;
    }

    @Override
    public RangeRule mapToRule(RuleBaseDTO dto, String tagName, RuleMetadata metadata) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        ArgumentExceptionHelper.throwIfNullOrBlank(tagName, "tagName");
        ArgumentExceptionHelper.throwIfNull(metadata, "metadata");

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
                throw new IllegalArgumentException("Unknown RuleBaseDTO type: " + dto.getClass());
        }

        var castedDTO = getDTOClass().cast(dto);
        return new RangeRule(metadata, valueResolver, castedDTO.getMinInclusive(), castedDTO.getMaxExclusive());
    }
}
