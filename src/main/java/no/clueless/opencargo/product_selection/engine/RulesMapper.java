package no.clueless.opencargo.product_selection.engine;

import jakarta.xml.bind.JAXBElement;
import no.clueless.opencargo.bindings.*;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.Population;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class RulesMapper {
    private final Map<String, BiFunction<RuleBaseDTO, RuleMetadata, ? extends Rule>> ruleMappers = new HashMap<>();

    public RulesMapper() {
        ruleMappers.put("geographyRule", (dto, metadata) -> GeographyRule.from((GeographyRuleDTO) dto, metadata));
        ruleMappers.put("lengthOrGirthRule", (dto, metadata) -> LengthGirthRule.from((LengthOrGirthRuleDTO) dto, metadata));
        ruleMappers.put("minDimensionsRule", (dto, metadata) -> MinDimensionsRule.from((MinDimensionsRuleDTO) dto, metadata));
        ruleMappers.put("weightRule", (dto, metadata) -> RangeRule.from((RangeRuleDTO) dto, metadata, "weightRule"));
        ruleMappers.put("widthRule", (dto, metadata) -> RangeRule.from((RangeRuleDTO) dto, metadata, "widthRule"));
        ruleMappers.put("lengthRule", (dto, metadata) -> RangeRule.from((RangeRuleDTO) dto, metadata, "lengthRule"));
        ruleMappers.put("heightRule", (dto, metadata) -> RangeRule.from((RangeRuleDTO) dto, metadata, "heightRule"));
    }

    RuleMetadata toRuleMetadata(RuleBaseDTO ruleBaseDTO) {
        ArgumentExceptionHelper.throwIfNull(ruleBaseDTO, "ruleBaseDTO");
        return new RuleMetadata(
                ruleBaseDTO.getId(),
                ruleBaseDTO.getNumber(),
                ruleBaseDTO.getName(),
                ruleBaseDTO.getPriority(),
                ruleBaseDTO.getTargets()
                        .getConsignorOrProduct()
                        .stream()
                        .filter(consignorOrProduct -> consignorOrProduct.getName().getLocalPart().equals("consignor"))
                        .map(JAXBElement::getValue)
                        .map(IdentifiableEntityDTO::getId)
                        .collect(Collectors.toSet()),
                ruleBaseDTO.getTargets()
                        .getConsignorOrProduct()
                        .stream()
                        .filter(consignorOrProduct -> consignorOrProduct.getName().getLocalPart().equals("product"))
                        .map(JAXBElement::getValue)
                        .map(IdentifiableEntityDTO::getId)
                        .collect(Collectors.toSet())
        );
    }

    Rule mapToRule(JAXBElement<? extends RuleBaseDTO> element) {
        ArgumentExceptionHelper.throwIfNull(element, "element");
        var ruleBaseDTO = element.getValue();
        var tagName     = element.getName().getLocalPart();

        if (!ruleMappers.containsKey(tagName)) {
            throw new IllegalStateException("No rule mapper registered for " + ruleBaseDTO.getClass().getName());
        }

        var ruleMapper = ruleMappers.get(tagName);
        if (ruleMapper == null) {
            throw new IllegalStateException("Encountered null value in the rule mapper registry for class " + ruleBaseDTO.getClass().getName());
        }

        return ruleMapper.apply(ruleBaseDTO, toRuleMetadata(ruleBaseDTO));
    }

    public Population<Rule, Set<Rule>> mapToRules(RuleListDTO ruleListDTO) {
        return ArgumentExceptionHelper.throwIfNull(ruleListDTO, "ruleListDTO")
                .getGeographyRuleOrWeightRuleOrWidthRule()
                .stream()
                .map(this::mapToRule)
                .collect(Population.collector(HashSet::new));
    }

    private static final class SingletonHolder {
        private static final RulesMapper INSTANCE = new RulesMapper();
    }

    public static RulesMapper getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
