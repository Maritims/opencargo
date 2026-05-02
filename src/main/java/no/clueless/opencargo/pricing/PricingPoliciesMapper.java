package no.clueless.opencargo.pricing;

import no.clueless.opencargo.bindings.*;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PricingPoliciesMapper {
    private final Map<Class<? extends PricingRuleDTO>, Function<PricingRuleDTO, ? extends PricingRule>> ruleMappers = new HashMap<>();

    public PricingPoliciesMapper() {
        ruleMappers.put(GeographicPricingRuleDTO.class, (dto) -> GeographicPricingRule.from((GeographicPricingRuleDTO) dto));
        ruleMappers.put(ProductRequirementRuleDTO.class, (dto) -> ProductRequirementRule.from((ProductRequirementRuleDTO) dto));
        ruleMappers.put(WeightLimitRuleDTO.class, (dto) -> WeightLimitRule.from((WeightLimitRuleDTO) dto));
    }

    public PricingRules mapToPricingRules(PricingRuleListDTO dto) {
        return ArgumentExceptionHelper.throwIfNull(dto, "dto")
                .getGeographicPricingRuleOrProductRequirementRuleOrWeightLimitRule()
                .stream()
                .map(pricingRuleDTO -> ruleMappers.get(pricingRuleDTO.getClass()).apply(pricingRuleDTO))
                .collect(PricingRules.collector());
    }

    public PricingPolicies mapToPolicies(PricingPolicyListDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");

        return dto.getPricingPolicy()
                .stream()
                .map(pricingPolicyDTO -> new PricingPolicy(
                        pricingPolicyDTO.getName(),
                        mapToPricingRules(pricingPolicyDTO.getPricingRules()),
                        null,
                        pricingPolicyDTO.getBasePrice(),
                        pricingPolicyDTO.getPriority()
                ))
                .collect(PricingPolicies.collector());
    }

    private static final class SingletonHolder {
        private static final PricingPoliciesMapper INSTANCE = new PricingPoliciesMapper();
    }

    public static PricingPoliciesMapper getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
