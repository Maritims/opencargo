package no.clueless.opencargo.rules.mapping;

import no.clueless.opencargo.bindings.GeographyRuleDTO;
import no.clueless.opencargo.bindings.PostalCodeRangeSpecificationDTO;
import no.clueless.opencargo.bindings.PostalCodeSetSpecificationDTO;
import no.clueless.opencargo.bindings.RuleBaseDTO;
import no.clueless.opencargo.domain.geography.*;
import no.clueless.opencargo.rules.GeographyRule;
import no.clueless.opencargo.rules.RuleMetadata;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.stream.Collectors;

public class GeographyRuleMapper implements RuleMapper<GeographyRuleDTO, GeographyRule> {
    @Override
    public Class<GeographyRuleDTO> getDTOClass() {
        return GeographyRuleDTO.class;
    }

    @Override
    public GeographyRule mapToRule(RuleBaseDTO dto, String tagName, RuleMetadata metadata) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        ArgumentExceptionHelper.throwIfNullOrBlank(tagName, "tagName");
        ArgumentExceptionHelper.throwIfNull(metadata, "metadata");

        var countrySpecifications = getDTOClass()
                .cast(dto)
                .getCountrySpecification()
                .stream()
                .map(countrySpecificationDTO -> {
                    var countryCode = new CountryCode(countrySpecificationDTO.getCountryCode());
                    var postalCodes = countrySpecificationDTO.getPostalCodeSpecification()
                            .stream()
                            .map(postalCodeSpecificationDTO -> {
                                if (postalCodeSpecificationDTO instanceof PostalCodeRangeSpecificationDTO) {
                                    var postalCodeRangeSpecificationDTO = (PostalCodeRangeSpecificationDTO) postalCodeSpecificationDTO;
                                    var start                           = new PostalCode(postalCodeRangeSpecificationDTO.getMinInclusive());
                                    var end                             = new PostalCode(postalCodeRangeSpecificationDTO.getMaxInclusive());
                                    return new PostalCodeRange(start, end);
                                } else if (postalCodeSpecificationDTO instanceof PostalCodeSetSpecificationDTO) {
                                    var postalCodeSetSpecificationDTO = (PostalCodeSetSpecificationDTO) postalCodeSpecificationDTO;
                                    return postalCodeSetSpecificationDTO.getPostalCode()
                                            .stream()
                                            .collect(PostalCodes.collector());
                                } else {
                                    throw new IllegalArgumentException("Unknown PostalCodeSpecificationDTO type: " + postalCodeSpecificationDTO.getClass());
                                }
                            })
                            .collect(Collectors.toSet());
                    return new CountrySpecification(countryCode, postalCodes);
                })
                .collect(CountrySpecifications.collector());

        return new GeographyRule(metadata, countrySpecifications);
    }
}
