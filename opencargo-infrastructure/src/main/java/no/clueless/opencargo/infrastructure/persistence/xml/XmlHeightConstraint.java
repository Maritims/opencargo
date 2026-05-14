package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "height")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlHeightConstraint extends XmlDistanceConstraint {
}
