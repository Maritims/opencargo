package no.clueless.opencargo.domain.geography;

public interface PostalCodeConstraint {
    /**
     * Checks if the given postal code is permitted by this constraint.
     */
    boolean contains(PostalCode postalCode);
}
