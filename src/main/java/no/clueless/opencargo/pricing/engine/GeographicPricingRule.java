package no.clueless.opencargo.pricing.engine;

import no.clueless.opencargo.shared.applicability.EvaluationResult;
import no.clueless.opencargo.bindings.GeographicPricingRuleDTO;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.geography.CountrySpecification;
import no.clueless.opencargo.pricing.PricingQuery;
import no.clueless.opencargo.pricing.policy.PricingRule;

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

    public static GeographicPricingRule from(GeographicPricingRuleDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        return new GeographicPricingRule(CountrySpecification.from(dto.getCountrySpecification()));
    }
}
