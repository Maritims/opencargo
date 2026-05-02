package no.clueless.opencargo.rules.mapping;

import no.clueless.opencargo.bindings.LengthOrGirthRuleDTO;
import no.clueless.opencargo.bindings.RuleBaseDTO;
import no.clueless.opencargo.rules.LengthGirthRule;
import no.clueless.opencargo.rules.RuleMetadata;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

public class LengthOrGirthRuleMapper implements RuleMapper<LengthOrGirthRuleDTO, LengthGirthRule> {
    @Override
    public Class<LengthOrGirthRuleDTO> getDTOClass() {
        return LengthOrGirthRuleDTO.class;
    }

    @Override
    public LengthGirthRule mapToRule(RuleBaseDTO dto, String tagName, RuleMetadata metadata) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        ArgumentExceptionHelper.throwIfNullOrBlank(tagName, "tagName");
        ArgumentExceptionHelper.throwIfNull(metadata, "metadata");

        var castedDTO = getDTOClass().cast(dto);
        return new LengthGirthRule(metadata, castedDTO.getMaxLength(), castedDTO.getMaxLengthAndGirthCombined());
    }
}
