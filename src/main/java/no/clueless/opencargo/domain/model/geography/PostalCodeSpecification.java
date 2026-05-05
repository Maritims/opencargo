package no.clueless.opencargo.domain.model.geography;

public interface PostalCodeSpecification {
    /**
     * Checks if this constraint permits the given postal code.
     */
    boolean contains(PostalCode postalCode);
}
