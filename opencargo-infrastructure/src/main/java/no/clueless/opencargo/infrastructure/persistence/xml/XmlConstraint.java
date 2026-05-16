package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlTransient;
import no.clueless.opencargo.domain.criteria.Constraint;
import no.clueless.opencargo.domain.shared.DomainMapper;

@XmlTransient
public interface XmlConstraint<T extends Constraint> extends DomainMapper<T> {
}
