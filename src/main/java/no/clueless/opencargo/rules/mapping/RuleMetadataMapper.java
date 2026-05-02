package no.clueless.opencargo.rules.mapping;

import jakarta.xml.bind.JAXBElement;
import no.clueless.opencargo.bindings.IdentifiableEntityDTO;
import no.clueless.opencargo.bindings.RuleBaseDTO;
import no.clueless.opencargo.rules.RuleMetadata;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.stream.Collectors;

public class RuleMetadataMapper {
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
}
