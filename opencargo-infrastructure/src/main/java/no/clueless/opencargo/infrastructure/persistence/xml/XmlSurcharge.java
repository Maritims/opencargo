package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.model.FreightSurcharge;
import no.clueless.opencargo.domain.model.SurchargeModifier;
import no.clueless.opencargo.domain.shared.DomainMapper;

@XmlRootElement(name = "surcharge")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSurcharge implements DomainMapper<FreightSurcharge> {
    @XmlElement(required = true)
    private String            reason;
    @XmlElements({
            @XmlElement(name = "money", type = XmlMoney.class),
            @XmlElement(name = "percentage", type = XmlPercentage.class)
    })
    private XmlAmountModifier value;
    @XmlElement(required = true)
    private XmlConstraints    constraints;

    public String getReason() {
        return reason;
    }

    public XmlAmountModifier getValue() {
        return value;
    }

    public XmlConstraints getConstraints() {
        return constraints;
    }

    @Override
    public FreightSurcharge toDomain() {
        SurchargeModifier modifier;
        if (value instanceof XmlMoney) {
            modifier = SurchargeModifier.money(((XmlMoney) value).toDomain());
        } else if (value instanceof XmlPercentage) {
            modifier = SurchargeModifier.percentage(((XmlPercentage) value).toDomain());
        } else {
            throw new IllegalStateException("unknown amount modifier type");
        }
        return new FreightSurcharge(reason, modifier, constraints.toDomain().getConstraints());
    }
}
