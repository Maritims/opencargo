package no.clueless.opencargo.product_selection.domain.service;

import no.clueless.opencargo.bindings.RuleListDTO;
import no.clueless.opencargo.infrastructure.marshalling.XmlMarshaller;
import no.clueless.opencargo.product_selection.domain.service.engine.Rule;
import no.clueless.opencargo.product_selection.domain.service.engine.RulesMapper;
import no.clueless.opencargo.product_selection.port.in.CountRulesUseCase;
import no.clueless.opencargo.product_selection.port.in.ListRulesUseCase;
import no.clueless.opencargo.shared.Population;

import java.util.Set;

public class RulesService implements CountRulesUseCase, ListRulesUseCase {
    private RuleListDTO ruleListDTO;

    private RuleListDTO getRuleListDTO() {
        if (ruleListDTO == null) {
            ruleListDTO = XmlMarshaller.unmarshalResourceSilently("rules.xml", RuleListDTO.class);
        }
        return ruleListDTO;
    }

    @Override
    public int countRules() {
        var ruleListDTO = getRuleListDTO();
        return ruleListDTO == null || ruleListDTO.getGeographyRuleOrWeightRuleOrWidthRule() == null ? 0 : ruleListDTO.getGeographyRuleOrWeightRuleOrWidthRule().size();
    }

    @Override
    public Population<Rule, Set<Rule>> listRules() {
        var ruleListDTO = getRuleListDTO();
        return ruleListDTO == null || ruleListDTO.getGeographyRuleOrWeightRuleOrWidthRule() == null ? null : RulesMapper.getInstance().mapToRules(ruleListDTO);
    }

    private static final class SingletonHolder {
        private static final RulesService INSTANCE = new RulesService();
    }

    public static RulesService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
