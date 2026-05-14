package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "length")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlLengthConstraint extends XmlDistanceConstraint {
}
