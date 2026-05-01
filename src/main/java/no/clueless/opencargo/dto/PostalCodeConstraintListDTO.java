package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "postalCodeConstraints")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PostalCodeConstraintListDTO {
    private List<PostalCodeConstraintDTO>  postalCodeConstraints = new ArrayList<>();

    public PostalCodeConstraintListDTO() {}

    @XmlElementRef
    public List<PostalCodeConstraintDTO> getPostalCodeConstraints() {
        return postalCodeConstraints;
    }

    @SuppressWarnings("unused")
    public void setPostalCodeConstraints(List<PostalCodeConstraintDTO> postalCodeConstraints) {
        this.postalCodeConstraints = postalCodeConstraints;
    }
}
