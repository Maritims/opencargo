package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.geography.PostalCode;
import no.clueless.opencargo.domain.geography.PostalCodeConstraint;
import no.clueless.opencargo.domain.geography.PostalCodeRange;

@XmlRootElement(name = "postalCodeRange")
@XmlType(name = "postalCodeRange")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PostalCodeRangeDTO extends PostalCodeConstraintDTO {
    private String start;
    private String end;

    public PostalCodeRangeDTO() {
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public PostalCodeConstraint toDomain() {
        return new PostalCodeRange(new PostalCode(getStart()), new PostalCode(getEnd()));
    }
}
