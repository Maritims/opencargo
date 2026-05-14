package no.clueless.opencargo.bootstrap;

import no.clueless.opencargo.application.dto.FindProductsQuery;
import no.clueless.opencargo.application.service.FreightService;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.physical.WeightUnit;
import no.clueless.opencargo.infrastructure.persistence.InMemoryFreightProductRepository;

import java.math.BigDecimal;

public class OpenCargoApplication {
    public static void main(String[] args) {
        var productRepository = new InMemoryFreightProductRepository();
        var freightService    = new FreightService(productRepository);
        var query = new FindProductsQuery(
                new BigDecimal("15.00"), WeightUnit.KILOGRAM,
                new BigDecimal("300"), new BigDecimal("200"), new BigDecimal("100"),
                DistanceUnit.MILLIMETER,
                null
        );

        System.out.println("Finding eligible products for a 15kg parcel...");

        var eligibleProducts = freightService.findForCriteria(query);

        if (eligibleProducts.isEmpty()) {
            System.out.println("No products found");
        } else {
            eligibleProducts.forEach(product -> System.out.printf("Found product: %s", product.getName()));
        }
    }
}
