package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.shared.AdrClass;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {})
public class XmlConstraints extends XmlConstraint {
    @XmlElements({
            @XmlElement(name = "max-weight", type = XmlMaxWeightConstraint.class),
            @XmlElement(name = "max-length", type = XmlMaxLengthConstraint.class),
            @XmlElement(name = "max-length-plus-girth", type = XmlMaxLengthPlusGirthConstraint.class),
            @XmlElement(name = "max-dimensions", type = XmlMaxDimensionsConstraint.class),
            @XmlElement(name = "min-dimensions", type = XmlMinDimensionsConstraint.class),
            @XmlElement(name = "any", type = XmlConstraints.class),
            @XmlElement(name = "adr", type = AdrClass.class)
    })
    private List<XmlConstraint> constraints = new ArrayList<>();

    public List<XmlConstraint> getConstraints() {
        return constraints;
    }
}
