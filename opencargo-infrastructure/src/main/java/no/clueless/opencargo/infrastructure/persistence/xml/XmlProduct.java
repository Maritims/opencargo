package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlProduct {
    @XmlAttribute(required = true)
    private String         id;
    @XmlAttribute(required = true)
    private String         carrierId;
    @XmlElement(required = true)
    private String         name;
    private XmlConstraints constraints;
    @XmlElement(required = true)
    private XmlPrice       price;

    public String getId() {
        return id;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public String getName() {
        return name;
    }

    public XmlConstraints getConstraints() {
        return constraints;
    }

    public XmlPrice getPrice() {
        return price;
    }
}
