package no.clueless.opencargo.domain.geography;

import no.clueless.opencargo.bindings.PostalCodeRangeSpecificationDTO;
import no.clueless.opencargo.bindings.PostalCodeSetSpecificationDTO;
import no.clueless.opencargo.bindings.PostalCodeSpecificationDTO;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

public interface PostalCodeSpecification {
    /**
     * Checks if this constraint permits the given postal code.
     */
    boolean contains(PostalCode postalCode);

    static PostalCodeSpecification from(PostalCodeSpecificationDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");

        if (dto instanceof PostalCodeRangeSpecificationDTO) {
            return PostalCodeRange.from((PostalCodeRangeSpecificationDTO) dto);
        }
        if (dto instanceof PostalCodeSetSpecificationDTO) {
            return PostalCodes.from((PostalCodeSetSpecificationDTO) dto);
        }

        throw new IllegalArgumentException("Unknown type: " + dto.getClass());
    }
}
