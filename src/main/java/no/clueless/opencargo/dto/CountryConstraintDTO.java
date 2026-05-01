package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.geography.CountryCode;
import no.clueless.opencargo.domain.geography.CountryConstraint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "countryConstraint")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlSeeAlso({PostalCodeRangeDTO.class, PostalCodeSetDTO.class})
public class CountryConstraintDTO implements DTO<CountryConstraint> {
    private String countryCode;

    private List<PostalCodeConstraintDTO> items = new ArrayList<>();

    public CountryConstraintDTO() {}

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @XmlElementWrapper(name = "postalCodeConstraints")
    @XmlElementRef
    public List<PostalCodeConstraintDTO> getPostalCodeConstraints() {
        return items;
    }

    public void setPostalCodeConstraints(List<PostalCodeConstraintDTO> items) {
        this.items = items;
    }

    @Override
    public CountryConstraint toDomain() {
        var postalCodeConstraints = getPostalCodeConstraints().stream()
                .map(PostalCodeConstraintDTO::toDomain)
                .collect(Collectors.toSet());
        return new CountryConstraint(new CountryCode(countryCode), postalCodeConstraints);
    }
}
