package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.rules.Rules;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "rules")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlSeeAlso({GeographyRuleDTO.class, LengthGirthRuleDTO.class, MinDimensionsRuleDTO.class, WeightRuleDTO.class})
public class RuleListDTO implements DTO<Rules> {
    private List<RuleDTO> rules = new ArrayList<>();

    public RuleListDTO() {
    }

    @XmlElementRef
    public List<RuleDTO> getRules() {
        return rules;
    }

    public void setRules(List<RuleDTO> rules) {
        this.rules = rules;
    }

    @Override
    public Rules toDomain() {
        return rules.stream()
                .map(DTO::toDomain)
                .collect(Rules.collector());
    }
}
