package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.criteria.AnyConstraint;
import no.clueless.opencargo.domain.criteria.Constraint;
import no.clueless.opencargo.domain.shared.AdrClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {})
public class XmlConstraints implements XmlConstraint<AnyConstraint> {
    @XmlElements({
            @XmlElement(name = "max-weight", type = XmlMaxWeightConstraint.class),
            @XmlElement(name = "max-length", type = XmlMaxLengthConstraint.class),
            @XmlElement(name = "max-length-plus-girth", type = XmlMaxLengthPlusGirthConstraint.class),
            @XmlElement(name = "max-dimensions", type = XmlMaxDimensionsConstraint.class),
            @XmlElement(name = "min-dimensions", type = XmlMinDimensionsConstraint.class),
            @XmlElement(name = "any", type = XmlConstraints.class),
            @XmlElement(name = "adr", type = AdrClass.class)
    })
    private List<XmlConstraint<Constraint>> constraints = new ArrayList<>();

    public List<XmlConstraint<Constraint>> getConstraints() {
        return constraints;
    }

    @Override
    public AnyConstraint toDomain() {
        return constraints.isEmpty() ? null : new AnyConstraint(constraints.stream()
                .map(XmlConstraint::toDomain)
                .collect(Collectors.toList()));
    }
}
