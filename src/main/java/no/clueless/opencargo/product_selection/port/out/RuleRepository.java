package no.clueless.opencargo.product_selection.port.out;

import no.clueless.opencargo.product_selection.adapter.xml_config.XmlRuleMapper;
import no.clueless.opencargo.product_selection.adapter.xml_config.XmlRuleRepository;
import no.clueless.opencargo.product_selection.domain.model.Rule;

import java.util.Set;

public interface RuleRepository {
    int getTotalCount();

    Set<Rule> getAll();

    static RuleRepository create() {
        return new XmlRuleRepository("rules.xml", new XmlRuleMapper());
    }
}
