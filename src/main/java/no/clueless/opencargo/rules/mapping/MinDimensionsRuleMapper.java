package no.clueless.opencargo.rules.mapping;

import no.clueless.opencargo.bindings.MinDimensionsRuleDTO;
import no.clueless.opencargo.bindings.RuleBaseDTO;
import no.clueless.opencargo.rules.MinDimensionsRule;
import no.clueless.opencargo.rules.RuleMetadata;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

public class MinDimensionsRuleMapper implements RuleMapper<MinDimensionsRuleDTO, MinDimensionsRule> {
    @Override
    public Class<MinDimensionsRuleDTO> getDTOClass() {
        return MinDimensionsRuleDTO.class;
    }

    @Override
    public MinDimensionsRule mapToRule(RuleBaseDTO dto, String tagName, RuleMetadata metadata) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        ArgumentExceptionHelper.throwIfNullOrBlank(tagName, "tagName");
        ArgumentExceptionHelper.throwIfNull(metadata, "metadata");

        var castedDTO = getDTOClass().cast(dto);
        return new MinDimensionsRule(metadata, castedDTO.getMinWidth(), castedDTO.getMinLength(), castedDTO.getMinHeight());
    }
}
