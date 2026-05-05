package no.clueless.opencargo.pricing.adapter.xml_config;

import no.clueless.opencargo.pricing.domain.model.policy.PricingRule;
import no.clueless.opencargo.pricing.port.out.RuleRepository;

import java.util.Set;

public class XmlRuleRepository implements RuleRepository {
    @Override
    public int getTotalCount() {
        return 0;
    }

    @Override
    public Set<PricingRule> getAll() {
        return Set.of();
    }
}
