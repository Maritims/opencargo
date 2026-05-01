package no.clueless.opencargo.rules;

import no.clueless.opencargo.domain.geography.CountryConstraints;
import no.clueless.opencargo.util.ArgumentExceptionHelper;
import no.clueless.opencargo.Query;
import no.clueless.opencargo.domain.geography.CountryCode;
import no.clueless.opencargo.domain.geography.PostalCode;

import java.util.Set;

/**
 * A rule which is satisfied by certain geographical properties, such as country codes and/or postal codes.
 */
public final class GeographyRule extends RuleBase implements Rule {
    private final CountryConstraints countryConstraints;

    public GeographyRule(int id, Integer consignorId, Set<Integer> productIds, String number, String name, int priority, boolean isTerminal, CountryConstraints countryConstraints) {
        super(id, consignorId, productIds, number, name, priority, isTerminal);
        this.countryConstraints = ArgumentExceptionHelper.throwIfNull(countryConstraints, "countryConstraints");
    }

    public CountryConstraints getCountryConstraints() {
        return countryConstraints;
    }

    @Override
    public EvaluationResult evaluate(Query query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");

        CountryCode destinationCountry    = query.getDestination().getCountryCode();
        PostalCode  destinationPostalCode = query.getDestination().getPostalCode();

        if (getCountryConstraints().stream().noneMatch(countryConstraint -> countryConstraint.getCountryCode().equals(destinationCountry))) {
            return EvaluationResult.unsatisfied(String.format("Service not available in country: %s", destinationCountry));
        }

        if (getCountryConstraints().stream().noneMatch(countryConstraint -> countryConstraint.matches(destinationCountry, destinationPostalCode))) {
            return EvaluationResult.unsatisfied(String.format("Postal code %s is not within the supported postal code constraints in %s", destinationPostalCode, destinationCountry));
        }

        return EvaluationResult.satisfied();
    }
}
