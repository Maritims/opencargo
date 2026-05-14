package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;

@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class XmlDimensionsConstraint extends XmlConstraint {
    @XmlElement(required = true)
    private XmlWidthConstraint width;
    @XmlElement(required = true)
    private XmlLengthConstraint length;
    @XmlElement(required = true)
    private XmlHeightConstraint height;

    public XmlDistanceConstraint getWidth() {
        return width;
    }

    public XmlDistanceConstraint getLength() {
        return length;
    }

    public XmlDistanceConstraint getHeight() {
        return height;
    }
}
