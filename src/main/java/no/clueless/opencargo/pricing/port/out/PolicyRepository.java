package no.clueless.opencargo.pricing.port.out;

import no.clueless.opencargo.pricing.adapter.xml_config.XmlPolicyMapper;
import no.clueless.opencargo.pricing.adapter.xml_config.XmlPolicyRepository;
import no.clueless.opencargo.pricing.domain.model.policy.PricingPolicy;

import java.util.Set;

public interface PolicyRepository {
    int getTotalCount();

    Set<PricingPolicy> getAll();

    static PolicyRepository create() {
        return new XmlPolicyRepository(new XmlPolicyMapper());
    }
}
