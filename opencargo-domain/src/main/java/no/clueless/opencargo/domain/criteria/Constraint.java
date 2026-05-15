package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.model.Parcel;

public interface Constraint {
    Decision evaluate(Parcel parcel);

    default boolean isSatisfiedBy(Parcel parcel) {
        return evaluate(parcel).satisfied();
    }
}
