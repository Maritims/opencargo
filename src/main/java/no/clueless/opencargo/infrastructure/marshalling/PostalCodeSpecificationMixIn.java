package no.clueless.opencargo.infrastructure.marshalling;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import no.clueless.opencargo.domain.model.geography.PostalCodeRange;
import no.clueless.opencargo.domain.model.geography.PostalCodes;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostalCodeRange.class, name = "postalCodeRange"),
        @JsonSubTypes.Type(value = PostalCodes.class, name = "postalCodeSet")
})
public interface PostalCodeSpecificationMixIn {
}
