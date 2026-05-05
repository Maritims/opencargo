package no.clueless.opencargo.pricing.port.out;

import no.clueless.opencargo.pricing.adapter.xml_config.XmlRuleRepository;
import no.clueless.opencargo.pricing.domain.model.policy.PricingRule;

import java.util.Set;

public interface RuleRepository {
    int getTotalCount();

    Set<PricingRule> getAll();

    static RuleRepository create() {
        return new XmlRuleRepository();
    }
}
