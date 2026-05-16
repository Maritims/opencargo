package no.clueless.opencargo.infrastructure.persistence;

import no.clueless.opencargo.application.ports.output.FreightProductRepository;
import no.clueless.opencargo.domain.criteria.*;
import no.clueless.opencargo.domain.model.*;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.physical.WeightUnit;
import no.clueless.opencargo.domain.shared.AdrClass;
import no.clueless.opencargo.domain.shared.Measure;
import no.clueless.opencargo.domain.shared.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Set;

public class InMemoryFreightProductRepository implements FreightProductRepository {
    private final List<FreightProduct> products;

    public InMemoryFreightProductRepository() {
        products = List.of(
                new FreightProduct(new FreightProductId("foo"),
                        "Standard Parcel",
                        List.of(new MaxWeightConstraint(new Weight(new BigDecimal("35.00"), WeightUnit.KILOGRAM))),
                        new FreightPrice(
                                new Money(BigDecimal.valueOf(100), Currency.getInstance("NOK")),
                                List.of(),
                                List.of()
                        )
                ),
                new FreightProduct(new FreightProductId("bar"),
                        "Heavy Cargo",
                        List.of(new MaxWeightConstraint(new Weight(new BigDecimal("100.00"), WeightUnit.KILOGRAM))),new FreightPrice(
                        new Money(BigDecimal.valueOf(100), Currency.getInstance("NOK")),
                        List.of(),
                        List.of()
                )
                ),
                new FreightProduct(new FreightProductId("baz"),
                        "Flexible Courier Service",
                        List.of(
                                new MaxWeightConstraint(new Weight(new BigDecimal("10.00"), WeightUnit.KILOGRAM)),
                                new AnyConstraint(
                                        new MaxLengthConstraint(new Measure<>(new BigDecimal("1200"), DistanceUnit.MILLIMETER)),
                                        new MaxLengthPlusGirthConstraint(new Measure<>(new BigDecimal("2000"), DistanceUnit.MILLIMETER))
                                )
                        ),
                        new FreightPrice(
                                new Money(BigDecimal.valueOf(100), Currency.getInstance("NOK")),
                                List.of(),
                                List.of()
                        )
                ),
                // Basic Gas Transport: Only supports Class 2
                new FreightProduct(
                        new FreightProductId("gas"), "Basic Gas Transport",
                        List.of(new AdrConstraint(Set.of(AdrClass.CLASS_2_GASES))),
                        new FreightPrice(
                                new Money(BigDecimal.valueOf(100), Currency.getInstance("NOK")),
                                List.of(),
                                List.of()
                        )
                ),
                // Hazmat Premium: Supports both Gases and Flammable Liquids
                new FreightProduct(
                        new FreightProductId("hazmat"), "Hazmat Premium",
                        List.of(new AdrConstraint(Set.of(
                                AdrClass.CLASS_2_GASES,
                                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS
                        ))),
                        new FreightPrice(
                                new Money(BigDecimal.valueOf(100), Currency.getInstance("NOK")),
                                List.of(),
                                List.of()
                        )
                )
        );
    }

    @Override
    public List<FreightProduct> findAll() {
        return products;
    }
}
