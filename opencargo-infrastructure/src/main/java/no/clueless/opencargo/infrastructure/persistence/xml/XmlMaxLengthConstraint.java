package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import no.clueless.opencargo.domain.criteria.MaxLengthConstraint;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.shared.Measure;

import java.math.BigDecimal;

@XmlRootElement(name = "max-length")
public class XmlMaxLengthConstraint implements XmlConstraint<MaxLengthConstraint> {
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

    @Override
    public MaxLengthConstraint toDomain() {
        return new MaxLengthConstraint(new Measure<>(getValue(), getUnit()));
    }
}
