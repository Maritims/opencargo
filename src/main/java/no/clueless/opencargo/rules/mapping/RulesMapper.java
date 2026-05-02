package no.clueless.opencargo.rules.mapping;

import jakarta.xml.bind.JAXBElement;
import no.clueless.opencargo.bindings.*;
import no.clueless.opencargo.rules.*;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.HashMap;
import java.util.Map;

public class RulesMapper {
    private final Map<Class<? extends RuleBaseDTO>, RuleMapper<?, ?>> ruleMappers = new HashMap<>();
    private final RuleMetadataMapper                                  ruleMetadataMapper;

    public RulesMapper(RuleMetadataMapper ruleMetadataMapper) {
        this.ruleMetadataMapper = ArgumentExceptionHelper.throwIfNull(ruleMetadataMapper, "ruleMetadataMapper");

        registerRuleMapper(GeographyRuleDTO.class, new GeographyRuleMapper());
        registerRuleMapper(LengthOrGirthRuleDTO.class, new LengthOrGirthRuleMapper());
        registerRuleMapper(MinDimensionsRuleDTO.class, new MinDimensionsRuleMapper());
        registerRuleMapper(RangeRuleDTO.class, new RangeRuleMapper());
    }

    <TDTO extends RuleBaseDTO, TRule extends Rule> void registerRuleMapper(Class<TDTO> dtoClass, RuleMapper<TDTO, TRule> ruleMapper) {
        ruleMappers.put(
                ArgumentExceptionHelper.throwIfNull(dtoClass, "dtoClass"),
                ArgumentExceptionHelper.throwIfNull(ruleMapper, "ruleMapper")
        );
    }

    Rule mapToRule(JAXBElement<? extends RuleBaseDTO> element) {
        ArgumentExceptionHelper.throwIfNull(element, "element");
        var ruleBaseDTO = element.getValue();

        if (!ruleMappers.containsKey(ruleBaseDTO.getClass())) {
            throw new IllegalStateException("No rule mapper registered for " + ruleBaseDTO.getClass().getName());
        }

        var ruleMapper = ruleMappers.get(ruleBaseDTO.getClass());
        if (ruleMapper == null) {
            throw new IllegalStateException("Encountered null value in the rule mapper registry for class " + ruleBaseDTO.getClass().getName());
        }

        return ruleMapper.mapToRule(ruleBaseDTO, element.getName().getLocalPart(), ruleMetadataMapper.toRuleMetadata(ruleBaseDTO));
    }

    public Rules mapToRules(RuleListDTO ruleListDTO) {
        return ArgumentExceptionHelper.throwIfNull(ruleListDTO, "ruleListDTO")
                .getGeographyRuleOrWeightRuleOrWidthRule()
                .stream()
                .map(this::mapToRule)
                .collect(Rules.collector());
    }

    private static final class SingletonHolder {
        private static final RulesMapper INSTANCE = new RulesMapper(new RuleMetadataMapper());
    }

    public static RulesMapper getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
