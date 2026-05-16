package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.XmlRootElement;
import no.clueless.opencargo.domain.criteria.MinDimensionsConstraint;
import no.clueless.opencargo.domain.physical.Dimensions;
import no.clueless.opencargo.domain.shared.Measure;

@XmlRootElement
public class XmlMinDimensionsConstraint extends XmlDimensionsConstraint<MinDimensionsConstraint> {
    @Override
    public MinDimensionsConstraint toDomain() {
        return new MinDimensionsConstraint(new Dimensions(
                new Measure<>(getWidth().getValue(), getWidth().getUnit()),
                new Measure<>(getLength().getValue(), getLength().getUnit()),
                new Measure<>(getHeight().getValue(), getHeight().getUnit())
        ));
    }
}
