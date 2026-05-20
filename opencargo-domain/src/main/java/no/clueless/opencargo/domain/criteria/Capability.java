package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;

public interface Capability {
    Decision canHandle(Parcel parcel);
}
