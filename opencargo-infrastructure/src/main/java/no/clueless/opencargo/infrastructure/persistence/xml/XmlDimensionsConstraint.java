package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.criteria.Constraint;

@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class XmlDimensionsConstraint<T extends Constraint> implements XmlConstraint<T> {
    @XmlElement(required = true)
    private XmlWidth  width;
    @XmlElement(required = true)
    private XmlLength length;
    @XmlElement(required = true)
    private XmlHeight height;

    public XmlWidth getWidth() {
        return width;
    }

    public XmlLength getLength() {
        return length;
    }

    public XmlHeight getHeight() {
        return height;
    }
}
