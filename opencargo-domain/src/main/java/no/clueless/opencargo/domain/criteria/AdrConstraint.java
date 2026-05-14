package no.clueless.opencargo.domain.criteria;

import no.clueless.opencargo.domain.shared.AdrClass;
import no.clueless.opencargo.domain.model.Parcel;

import java.util.Set;

public class AdrConstraint implements Constraint {
    private final Set<AdrClass> supportedClasses;

    public AdrConstraint(Set<AdrClass> supportedClasses) {
        if (supportedClasses == null || supportedClasses.isEmpty()) {
            throw new IllegalArgumentException("supportedClasses cannot be null or empty");
        }
        this.supportedClasses = Set.copyOf(supportedClasses);
    }

    @Override
    public Decision evaluate(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        if (parcel.getAdrClasses() == null || parcel.getAdrClasses().isEmpty() || parcel.getAdrClasses().contains(AdrClass.NONE)) {
            return new Decision(getClass().getSimpleName(), true, "No ADR class specified");
        }

        return supportedClasses.containsAll(parcel.getAdrClasses()) ?
                new Decision(getClass().getSimpleName(), true, "ADR classes are supported") :
                new Decision(getClass().getSimpleName(), false, "ADR classes not supported");
    }

    @Override
    public boolean isSatisfiedBy(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        if (parcel.getAdrClasses() == null || parcel.getAdrClasses().isEmpty() || parcel.getAdrClasses().contains(AdrClass.NONE)) {
            return true;
        }
        return supportedClasses.containsAll(parcel.getAdrClasses());
    }
}
