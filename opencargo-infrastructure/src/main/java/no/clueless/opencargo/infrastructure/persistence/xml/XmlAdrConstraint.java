package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import no.clueless.opencargo.domain.shared.AdrClass;

@XmlRootElement(name = "adr")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAdrConstraint extends XmlConstraint {
    @XmlValue
    private AdrClass value;
}
