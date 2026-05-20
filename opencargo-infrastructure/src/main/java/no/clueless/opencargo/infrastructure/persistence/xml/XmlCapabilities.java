package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.criteria.AnyCapability;
import no.clueless.opencargo.domain.criteria.Capability;
import no.clueless.opencargo.domain.shared.DomainMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {})
public class XmlCapabilities implements DomainMapper<AnyCapability> {
    @XmlElements({
            @XmlElement(name = "handling-directives", type = XmlHandlingCapability.class)
    })
    private List<XmlCapability<Capability>> capabilities = new ArrayList<>();

    public List<XmlCapability<Capability>> getCapabilities() {
        return capabilities;
    }

    @Override
    public AnyCapability toDomain() {
        return capabilities.isEmpty() ? null : new AnyCapability(capabilities.stream()
                .map(XmlCapability::toDomain)
                .collect(Collectors.toList()));
    }
}
