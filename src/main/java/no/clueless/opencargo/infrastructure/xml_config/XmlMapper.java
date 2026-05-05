package no.clueless.opencargo.infrastructure.xml_config;

import no.clueless.opencargo.bindings.CountrySpecificationType;
import no.clueless.opencargo.domain.model.geography.*;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.stream.Collectors;

public class XmlMapper {
    public static CountrySpecification toCountrySpecification(CountrySpecificationType countrySpecification) {
        ArgumentExceptionHelper.throwIfNull(countrySpecification, "countrySpecification");
        var postalCodes = countrySpecification.getPostalCodeSpecificationGroup()
                .stream()
                .map(postalCodeSpecificationGroup -> {
                    if (postalCodeSpecificationGroup instanceof CountrySpecificationType.Range) {
                        var range = (CountrySpecificationType.Range) postalCodeSpecificationGroup;
                        return new PostalCodeRange(new PostalCode(range.getMin()), new PostalCode(range.getMax()));
                    } else if (postalCodeSpecificationGroup instanceof CountrySpecificationType.Set) {
                        var set = (CountrySpecificationType.Set) postalCodeSpecificationGroup;
                        return new PostalCodes(set.getCode().stream().map(PostalCode::new).collect(Collectors.toSet()));
                    } else {
                        throw new IllegalArgumentException("Unknown postal code specification type: " + postalCodeSpecificationGroup.getClass());
                    }
                })
                .collect(Collectors.toSet());
        var countryCode = new CountryCode(countrySpecification.getCountryCode());
        return new CountrySpecification(countryCode, postalCodes);
    }
}
