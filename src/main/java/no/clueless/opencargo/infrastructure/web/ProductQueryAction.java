package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.clueless.opencargo.ProductQuery;
import no.clueless.opencargo.domain.cargo.Address;
import no.clueless.opencargo.domain.cargo.Cargo;
import no.clueless.opencargo.domain.geography.CountryCode;
import no.clueless.opencargo.domain.geography.PostalCode;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;
import no.clueless.opencargo.selection.RuleEngine;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;

public class ProductQueryAction extends JsonAction<ProductQueryRequest> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final        RuleEngine   ruleEngine;

    protected ProductQueryAction(RuleEngine ruleEngine) {
        super(ProductQueryRequest.class);
        this.ruleEngine = ArgumentExceptionHelper.throwIfNull(ruleEngine, "ruleEngine");
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArgumentExceptionHelper.throwIfNull(request, "request");
        ArgumentExceptionHelper.throwIfNull(response, "response");

        var body = getBody(request);

        var cargo = new Cargo(
                new BigDecimal(body.getCargoWeight()),
                new BigDecimal(body.getCargoWidth()),
                new BigDecimal(body.getCargoLength()),
                new BigDecimal(body.getCargoHeight()),
                new BigDecimal(body.getCargoMonetaryValue())
        );

        var address = new Address(body.getDeliveryAddressLine1(), body.getDeliveryAddressLine2(), body.getDeliveryCity(), body.getDeliveryState(), new PostalCode(body.getDeliveryPostalCode()), new CountryCode(body.getDeliveryCountryCode()));

        var query = new ProductQuery(cargo, address);

        var result = ruleEngine.resolve(query);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        OBJECT_MAPPER.writeValue(response.getWriter(), result);
        response.getWriter().flush();
    }
}
