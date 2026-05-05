package no.clueless.opencargo.pricing.adapter.xml_config;

import no.clueless.opencargo.bindings.*;
import no.clueless.opencargo.infrastructure.xml_config.XmlMapper;
import no.clueless.opencargo.pricing.domain.model.GeographicPricingRule;
import no.clueless.opencargo.pricing.domain.model.ProductRequirementRule;
import no.clueless.opencargo.pricing.domain.model.WeightLimitRule;
import no.clueless.opencargo.pricing.domain.model.policy.PricingPolicy;
import no.clueless.opencargo.pricing.domain.model.policy.PricingRule;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.stream.Collectors;

public class XmlPolicyMapper {
    protected PricingRule toPricingRule(PricingRuleDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");

        if (dto instanceof GeographicPricingRuleDTO) {
            var geographicRule = (GeographicPricingRuleDTO) dto;
            return new GeographicPricingRule(XmlMapper.toCountrySpecification(geographicRule.getCountrySpecification()));
        }
        if (dto instanceof WeightLimitRuleDTO) {
            var weightLimitRule = (WeightLimitRuleDTO) dto;
            return new WeightLimitRule(weightLimitRule.getMaxWeight());
        }
        if (dto instanceof ProductRequirementRuleDTO) {
            var productRequirementRule = (ProductRequirementRuleDTO) dto;
            return new ProductRequirementRule(productRequirementRule.getProduct().getId().intValue());
        }

        throw new IllegalArgumentException("Unknown pricing rule type: " + dto.getClass());
    }

    public PricingPolicy toPolicy(PricingPolicyDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");

        return new PricingPolicy(
                dto.getName(),
                dto.getPricingRules()
                        .getGeographicPricingRuleOrProductRequirementRuleOrWeightLimitRule()
                        .stream()
                        .map(this::toPricingRule)
                        .collect(Collectors.toSet()),
                null,
                dto.getBasePrice(),
                dto.getPriority()
        );
    }
}
