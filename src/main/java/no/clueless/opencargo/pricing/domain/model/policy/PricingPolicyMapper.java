package no.clueless.opencargo.pricing.domain.model.policy;

import no.clueless.opencargo.bindings.*;
import no.clueless.opencargo.pricing.domain.service.engine.GeographicPricingRule;
import no.clueless.opencargo.pricing.domain.service.engine.ProductRequirementRule;
import no.clueless.opencargo.pricing.domain.service.engine.WeightLimitRule;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.Population;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class PricingPolicyMapper {
    private final Map<Class<? extends PricingRuleDTO>, Function<PricingRuleDTO, ? extends PricingRule>> ruleMappers = new HashMap<>();

    public PricingPolicyMapper() {
        ruleMappers.put(GeographicPricingRuleDTO.class, (dto) -> GeographicPricingRule.from((GeographicPricingRuleDTO) dto));
        ruleMappers.put(ProductRequirementRuleDTO.class, (dto) -> ProductRequirementRule.from((ProductRequirementRuleDTO) dto));
        ruleMappers.put(WeightLimitRuleDTO.class, (dto) -> WeightLimitRule.from((WeightLimitRuleDTO) dto));
    }

    public Population<PricingRule, Set<PricingRule>> mapToPricingRules(PricingRuleListDTO dto) {
        return ArgumentExceptionHelper.throwIfNull(dto, "dto")
                .getGeographicPricingRuleOrProductRequirementRuleOrWeightLimitRule()
                .stream()
                .map(pricingRuleDTO -> ruleMappers.get(pricingRuleDTO.getClass()).apply(pricingRuleDTO))
                .collect(Population.collector(HashSet::new));
    }

    public Population<PricingPolicy, Set<PricingPolicy>> mapToPolicies(PricingPolicyListDTO dto) {
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
                .collect(Population.collector(HashSet::new));
    }

    private static final class SingletonHolder {
        private static final PricingPolicyMapper INSTANCE = new PricingPolicyMapper();
    }

    public static PricingPolicyMapper getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
