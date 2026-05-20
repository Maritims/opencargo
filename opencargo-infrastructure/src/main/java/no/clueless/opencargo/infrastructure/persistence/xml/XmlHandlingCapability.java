package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.criteria.HandlingCapability;
import no.clueless.opencargo.domain.shared.HandlingDirective;

import java.util.Set;

@XmlRootElement(name = "handling-directives")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlHandlingCapability implements XmlCapability<HandlingCapability> {
    @XmlElement(name = "directive")
    private Set<HandlingDirective> directives;

    public Set<HandlingDirective> getDirectives() {
        return directives;
    }

    @Override
    public HandlingCapability toDomain() {
        return new HandlingCapability(directives);
    }
}
