package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {})
public class XmlProduct {
    @XmlAttribute(required = true)
    private String          id;
    @XmlAttribute(required = true)
    private String          carrierId;
    @XmlElement(required = true)
    private String          name;
    @XmlElement(required = true)
    private XmlConstraints  constraints;
    @XmlElement(required = true)
    private XmlPrice        price;
    @XmlElement(required = true)
    private XmlCapabilities capabilities;

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

    public XmlCapabilities getCapabilities() {
        return capabilities;
    }
}
