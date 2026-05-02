package no.clueless.opencargo.pricing;

import no.clueless.opencargo.EvaluationResult;
import no.clueless.opencargo.PricingQuery;
import no.clueless.opencargo.domain.geography.CountrySpecification;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;

public class GeographicPricingRule implements PricingRule {
    private final CountrySpecification countrySpecification;

    public GeographicPricingRule(CountrySpecification countrySpecification) {
        this.countrySpecification = ArgumentExceptionHelper.throwIfNull(countrySpecification, "countrySpecification");
    }

    @Override
    public String getName() {
        return "Geographic Constraint: " + countrySpecification.getCountryCode().getValue();
    }

    @Override
    public EvaluationResult evaluate(PricingQuery query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");
        return countrySpecification.matches(query.getDestination().getCountryCode(), query.getDestination().getPostalCode()) ?
                EvaluationResult.satisfied() :
                EvaluationResult.unsatisfied(String.format("The destination %s is outside of the supported country and postal code constraints", query.getDestination()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPricingRule that = (GeographicPricingRule) o;
        return Objects.equals(countrySpecification, that.countrySpecification);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(countrySpecification);
    }

    @Override
    public String toString() {
        return "GeographicPricingRule{" +
                "countrySpecification=" + countrySpecification +
                '}';
    }
}
