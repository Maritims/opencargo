package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.physical.DistanceUnit;

import java.math.BigDecimal;

@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class XmlDistance {
    @XmlValue
    private BigDecimal   value;
    @XmlAttribute(required = true)
    private DistanceUnit unit;

    public BigDecimal getValue() {
        return value;
    }

    public DistanceUnit getUnit() {
        return unit;
    }
}
