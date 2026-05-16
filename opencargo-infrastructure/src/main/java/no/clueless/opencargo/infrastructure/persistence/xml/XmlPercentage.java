package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import no.clueless.opencargo.domain.shared.DomainMapper;
import no.clueless.opencargo.domain.shared.Percentage;

import java.math.BigDecimal;

@XmlRootElement(name = "percentage")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlPercentage implements XmlAmountModifier, DomainMapper<Percentage> {
    @XmlValue
    private BigDecimal value;

    @Override
    public Percentage toDomain() {
        return new Percentage(value);
    }
}
