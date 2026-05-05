package no.clueless.opencargo.product_selection.adapter.xml_config;

import jakarta.xml.bind.JAXBElement;
import no.clueless.opencargo.bindings.BaseRuleType;
import no.clueless.opencargo.bindings.CountrySpecificationType;
import no.clueless.opencargo.bindings.GeographyRuleType;
import no.clueless.opencargo.bindings.RangeRuleType;
import no.clueless.opencargo.domain.model.Cargo;
import no.clueless.opencargo.domain.model.geography.*;
import no.clueless.opencargo.infrastructure.xml_config.XmlMapper;
import no.clueless.opencargo.product_selection.domain.model.Rule;
import no.clueless.opencargo.product_selection.domain.model.GeographyRule;
import no.clueless.opencargo.product_selection.domain.model.RangeRule;
import no.clueless.opencargo.product_selection.domain.model.RuleMetadata;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XmlRuleMapper {
    RuleMetadata toRuleMetadata(BaseRuleType rule) {
        ArgumentExceptionHelper.throwIfNull(rule, "rule");
        var consignorIds = new HashSet<Integer>();
        var productIds   = new HashSet<Integer>();

        rule.getTargets().getConsignorOrProduct().forEach(target -> {
            switch (target.getName().getLocalPart()) {
                case "consignor":
                    consignorIds.add(target.getValue().getId().intValue());
                    break;
                case "product":
                    productIds.add(target.getValue().getId().intValue());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown target type: " + target.getName().getLocalPart());
            }
        });

        return new RuleMetadata(
                rule.getId().intValue(),
                rule.getNumber(),
                rule.getName(),
                rule.getPriority(),
                consignorIds,
                productIds);
    }

    public GeographyRule toGeographyRule(GeographyRuleType rule) {
        ArgumentExceptionHelper.throwIfNull(rule, "rule");
        var metadata = toRuleMetadata(rule);

        var countrySpecifications = rule.getCountrySpecification()
                .stream()
                .map(XmlMapper::toCountrySpecification)
                .collect(Collectors.toSet());

        return new GeographyRule(metadata, countrySpecifications);
    }

    public RangeRule toRangeRule(RangeRuleType rule, String elementName) {
        ArgumentExceptionHelper.throwIfNull(rule, "rule");
        ArgumentExceptionHelper.throwIfNullOrBlank(elementName, "elementName");
        var                         metadata = toRuleMetadata(rule);
        Function<Cargo, BigDecimal> valueResolver;

        switch (elementName) {
            case "weight":
                valueResolver = Cargo::getWeight;
                break;
            case "width":
                valueResolver = Cargo::getWidth;
                break;
            case "length":
                valueResolver = Cargo::getLength;
                break;
            case "height":
                valueResolver = Cargo::getHeight;
                break;
            default:
                throw new IllegalArgumentException("Unknown elementName: " + elementName);
        }

        return new RangeRule(metadata, valueResolver, rule.getMin(), rule.getMax());
    }

    public Rule toDomain(JAXBElement<? extends BaseRuleType> element) {
        ArgumentExceptionHelper.throwIfNull(element, "element");

        var value = element.getValue();

        if (value instanceof GeographyRuleType) {
            return toGeographyRule((GeographyRuleType) value);
        }
        if (value instanceof RangeRuleType) {
            return toRangeRule((RangeRuleType) value, element.getName().getLocalPart());
        }
        throw new IllegalArgumentException("Unknown rule type: " + value.getClass());
    }
}
