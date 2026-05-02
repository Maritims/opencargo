package no.clueless.opencargo.pricing;

import no.clueless.opencargo.PricingQuery;
import no.clueless.opencargo.domain.geography.CountrySpecification;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeographicSurcharge implements PriceModifier {
    private final String               name;
    private final CountrySpecification countrySpecification;
    private final BigDecimal           percentage;

    public GeographicSurcharge(String name, CountrySpecification countrySpecification, BigDecimal percentage) {
        this.name                 = ArgumentExceptionHelper.throwIfNullOrBlank(name, "name");
        this.countrySpecification = ArgumentExceptionHelper.throwIfNull(countrySpecification, "countrySpecification");
        this.percentage           = ArgumentExceptionHelper.throwIfNullOrNegative(percentage, "percentage");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isApplicable(PricingQuery query) {
        return countrySpecification.matches(query.getDestination().getCountryCode(), query.getDestination().getPostalCode());
    }

    @Override
    public BigDecimal calculateDelta(PricingQuery query, BigDecimal currentBasePrice) {
        var factor = percentage.divide(new BigDecimal("100"), currentBasePrice.scale(), RoundingMode.HALF_UP);
        return currentBasePrice.multiply(factor);
    }
}
