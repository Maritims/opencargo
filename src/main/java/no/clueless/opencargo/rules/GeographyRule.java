package no.clueless.opencargo.rules;

import no.clueless.opencargo.domain.geography.CountrySpecifications;
import no.clueless.opencargo.util.ArgumentExceptionHelper;
import no.clueless.opencargo.ProductQuery;
import no.clueless.opencargo.domain.geography.CountryCode;
import no.clueless.opencargo.domain.geography.PostalCode;

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
    public EvaluationResult evaluate(ProductQuery productQuery) {
        ArgumentExceptionHelper.throwIfNull(productQuery, "query");

        CountryCode destinationCountry    = productQuery.getDestination().getCountryCode();
        PostalCode  destinationPostalCode = productQuery.getDestination().getPostalCode();

        if (getCountryConstraints().stream().noneMatch(countryConstraint -> countryConstraint.getCountryCode().equals(destinationCountry))) {
            return EvaluationResult.unsatisfied(String.format("Service not available in country: %s", destinationCountry));
        }

        if (getCountryConstraints().stream().noneMatch(countryConstraint -> countryConstraint.matches(destinationCountry, destinationPostalCode))) {
            return EvaluationResult.unsatisfied(String.format("Postal code %s is not within the supported postal code constraints in %s", destinationPostalCode, destinationCountry));
        }

        return EvaluationResult.satisfied();
    }
}
