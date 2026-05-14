package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "max-length")
public class XmlMaxLengthConstraint extends XmlDistanceConstraint {
}
