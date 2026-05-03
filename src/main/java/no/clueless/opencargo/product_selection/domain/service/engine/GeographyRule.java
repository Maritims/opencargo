package no.clueless.opencargo.product_selection.domain.service.engine;

import no.clueless.opencargo.domain.model.applicability.EvaluationResult;
import no.clueless.opencargo.bindings.GeographyRuleDTO;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.domain.model.geography.CountrySpecification;
import no.clueless.opencargo.domain.model.geography.CountrySpecifications;
import no.clueless.opencargo.domain.model.geography.CountryCode;
import no.clueless.opencargo.domain.model.geography.PostalCode;

/**
 * A rule which is satisfied by certain geographical properties, such as country codes and/or postal codes.
 */
public final class GeographyRule extends RuleBase implements Rule {
    private final CountrySpecifications countrySpecifications;

    public GeographyRule(RuleMetadata metadata, CountrySpecifications countrySpecifications) {
        super(metadata);
        this.countrySpecifications = ArgumentExceptionHelper.throwIfNull(countrySpecifications, "countryConstraints");
    }

    public CountrySpecifications getCountryConstraints() {
        return countrySpecifications;
    }

    @Override
    public EvaluationResult evaluate(ProductSelectionQuery productSelectionQuery) {
        ArgumentExceptionHelper.throwIfNull(productSelectionQuery, "query");

        CountryCode destinationCountry    = productSelectionQuery.getDestination().getCountryCode();
        PostalCode  destinationPostalCode = productSelectionQuery.getDestination().getPostalCode();

        if (getCountryConstraints().stream().noneMatch(countryConstraint -> countryConstraint.getCountryCode().equals(destinationCountry))) {
            return EvaluationResult.unsatisfied(String.format("Service not available in country: %s", destinationCountry));
        }

        if (getCountryConstraints().stream().noneMatch(countryConstraint -> countryConstraint.matches(destinationCountry, destinationPostalCode))) {
            return EvaluationResult.unsatisfied(String.format("Postal code %s is not within the supported postal code constraints in %s", destinationPostalCode, destinationCountry));
        }

        return EvaluationResult.satisfied();
    }

    public static GeographyRule from(GeographyRuleDTO dto, RuleMetadata metadata) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        ArgumentExceptionHelper.throwIfNull(metadata, "metadata");

        return new GeographyRule(metadata, dto.getCountrySpecification()
                .stream()
                .map(CountrySpecification::from)
                .collect(CountrySpecifications.collector()));
    }
}
