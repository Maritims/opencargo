package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "freight-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlProductCatalog {
    @XmlElement(name = "product")
    private List<XmlProduct> products;

    public List<XmlProduct> getProducts() {
        return products;
    }
}
