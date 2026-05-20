package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;

import java.util.List;

public class AnyCapability implements Capability {
    private final List<Capability> capabilities;

    public AnyCapability(List<Capability> capabilities) {
        if (capabilities == null || capabilities.isEmpty()) {
            throw new IllegalArgumentException("capabilities cannot be null or empty");
        }
        this.capabilities = List.copyOf(capabilities);
    }

    public List<Capability> getCapabilities() {
        return capabilities;
    }

    @Override
    public Decision canHandle(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        return capabilities.stream()
                .map(capability -> capability.canHandle(parcel))
                .filter(Decision::isSatisfied)
                .findFirst()
                .orElse(null);
    }
}
