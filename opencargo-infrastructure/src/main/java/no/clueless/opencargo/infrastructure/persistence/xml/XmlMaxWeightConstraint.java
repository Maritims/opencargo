package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.criteria.MaxWeightConstraint;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.physical.WeightUnit;

import java.math.BigDecimal;

@XmlRootElement(name = "max-weight")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMaxWeightConstraint implements XmlConstraint<MaxWeightConstraint> {
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

    @Override
    public MaxWeightConstraint toDomain() {
        return new MaxWeightConstraint(new Weight(maxWeight, unit));
    }
}
