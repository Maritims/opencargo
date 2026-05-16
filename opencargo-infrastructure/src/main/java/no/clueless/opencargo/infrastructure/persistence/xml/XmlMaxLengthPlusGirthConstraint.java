package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlRootElement;
import no.clueless.opencargo.domain.criteria.MaxLengthPlusGirthConstraint;
import no.clueless.opencargo.domain.shared.Measure;

@XmlRootElement(name = "max-length-plus-girth")
public class XmlMaxLengthPlusGirthConstraint extends XmlDistance implements XmlConstraint<MaxLengthPlusGirthConstraint> {
    @Override
    public MaxLengthPlusGirthConstraint toDomain() {
        return new MaxLengthPlusGirthConstraint(new Measure<>(getValue(), getUnit()));
    }
}
