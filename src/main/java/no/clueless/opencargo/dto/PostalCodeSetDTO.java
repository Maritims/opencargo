package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.geography.PostalCodeConstraint;
import no.clueless.opencargo.domain.geography.PostalCodeSet;

import java.util.List;

@XmlType(name = "postalCodeSet")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PostalCodeSetDTO extends PostalCodeConstraintDTO {
    @XmlElementWrapper(name = "postalCodes")
    @XmlElement(name = "postalCode")
    private List<String> postalCodes;

    public PostalCodeSetDTO() {
    }

    public List<String> getPostalCodes() {
        return postalCodes;
    }

    @Override
    public PostalCodeConstraint toDomain() {
        return postalCodes.stream().collect(PostalCodeSet.collector());
    }
}
