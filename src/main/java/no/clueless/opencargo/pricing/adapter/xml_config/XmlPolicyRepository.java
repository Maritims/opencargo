package no.clueless.opencargo.pricing.adapter.xml_config;

import no.clueless.opencargo.pricing.domain.model.policy.PricingPolicy;
import no.clueless.opencargo.pricing.port.out.PolicyRepository;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Set;

public class XmlPolicyRepository implements PolicyRepository {
    private final XmlPolicyMapper policyMapper;

    public XmlPolicyRepository(XmlPolicyMapper policyMapper) {
        this.policyMapper = ArgumentExceptionHelper.throwIfNull(policyMapper, "policyMapper");
    }

    @Override
    public int getTotalCount() {
        return 0;
    }

    @Override
    public Set<PricingPolicy> getAll() {
        return Set.of();
    }
}
