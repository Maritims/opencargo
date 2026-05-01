package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import no.clueless.opencargo.domain.geography.PostalCodeConstraint;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlSeeAlso({PostalCodeRangeDTO.class, PostalCodeSetDTO.class})
public abstract class PostalCodeConstraintDTO implements DTO<PostalCodeConstraint> {
    public PostalCodeConstraintDTO() {
    }
}
