package no.clueless.opencargo.rules.mapping;

import no.clueless.opencargo.bindings.RuleBaseDTO;
import no.clueless.opencargo.rules.Rule;
import no.clueless.opencargo.rules.RuleMetadata;

public interface RuleMapper<TDTO extends RuleBaseDTO, TRule extends Rule> {
    Class<TDTO> getDTOClass();

    TRule mapToRule(RuleBaseDTO dto, String tagName, RuleMetadata metadata);
}
