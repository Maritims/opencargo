package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlProduct {
    @XmlAttribute(required = true)
    private String         id;
    @XmlElement(required = true)
    private String         name;
    private XmlConstraints constraints;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public XmlConstraints getConstraints() {
        return constraints;
    }
}
