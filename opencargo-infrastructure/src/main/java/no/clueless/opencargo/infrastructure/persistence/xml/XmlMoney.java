package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMoney {
    @XmlValue
    private BigDecimal amount;
    @XmlAttribute(name = "currency", required = true)
    private String     currencyCode;


    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
