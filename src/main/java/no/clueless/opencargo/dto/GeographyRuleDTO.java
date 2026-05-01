package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.geography.CountryConstraints;
import no.clueless.opencargo.rules.GeographyRule;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "geographyRule")
@XmlType(name = "geographyRule")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class GeographyRuleDTO extends RuleDTO {
    private List<CountryConstraintDTO> countryConstraints = new ArrayList<>();

    public GeographyRuleDTO() {
    }

    @XmlElementWrapper(name = "countryConstraints")
    @XmlElement(name = "countryConstraint")
    public List<CountryConstraintDTO> getCountryConstraints() {
        return countryConstraints;
    }

    public void setCountryConstraints(List<CountryConstraintDTO> items) {
        this.countryConstraints = items;
    }

    @Override
    public GeographyRule toDomain() {
        var countryConstraints = this.countryConstraints
                .stream()
                .map(CountryConstraintDTO::toDomain)
                .collect(CountryConstraints.collector());
        return new GeographyRule(getId(), getConsignorId(), getProductIds(), getNumber(), getName(), getPriority(), false, countryConstraints);
    }
}
