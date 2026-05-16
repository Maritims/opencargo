package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.model.FreightPrice;
import no.clueless.opencargo.domain.shared.DomainMapper;

import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlPrice implements DomainMapper<FreightPrice> {
    @XmlElement(name = "base-price", required = true)
    private XmlMoney           basePrice;
    private List<XmlSurcharge> surcharges;
    @XmlElement(name = "constraints", required = true)
    private XmlConstraints     constraints;

    public XmlMoney getBasePrice() {
        return basePrice;
    }

    public List<XmlSurcharge> getSurcharges() {
        return surcharges;
    }

    public XmlConstraints getConstraints() {
        return constraints;
    }

    @Override
    public FreightPrice toDomain() {
        var constraints = this.constraints.toDomain();
        return new FreightPrice(
                basePrice.toDomain(),
                surcharges == null ? null : surcharges.stream()
                        .map(XmlSurcharge::toDomain)
                        .collect(Collectors.toList()),
                constraints == null ? null : constraints.getConstraints()
        );
    }
}
