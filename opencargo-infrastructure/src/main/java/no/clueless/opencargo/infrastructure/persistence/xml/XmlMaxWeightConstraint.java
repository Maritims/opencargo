package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.physical.WeightUnit;

import java.math.BigDecimal;

@XmlRootElement(name = "max-weight")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMaxWeightConstraint extends XmlConstraint {
    @XmlValue
    private BigDecimal maxWeight;
    @XmlAttribute(required = true)
    private WeightUnit unit;

    public BigDecimal getMaxWeight() {
        return maxWeight;
    }

    public WeightUnit getUnit() {
        return unit;
    }
}
