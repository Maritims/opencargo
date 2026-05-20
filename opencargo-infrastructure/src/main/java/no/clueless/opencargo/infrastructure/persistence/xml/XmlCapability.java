package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlTransient;
import no.clueless.opencargo.domain.criteria.Capability;
import no.clueless.opencargo.domain.shared.DomainMapper;

@XmlTransient
public interface XmlCapability<T extends Capability> extends DomainMapper<T> {
}
