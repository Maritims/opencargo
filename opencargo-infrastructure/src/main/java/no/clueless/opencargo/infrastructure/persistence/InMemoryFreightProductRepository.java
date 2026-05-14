package no.clueless.opencargo.infrastructure.persistence;

import no.clueless.opencargo.application.ports.output.FreightProductRepository;
import no.clueless.opencargo.domain.criteria.*;
import no.clueless.opencargo.domain.model.*;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.physical.WeightUnit;
import no.clueless.opencargo.domain.shared.AdrClass;
import no.clueless.opencargo.domain.shared.Measure;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class InMemoryFreightProductRepository implements FreightProductRepository {
    private final List<FreightProduct> products;

    public InMemoryFreightProductRepository() {
        products = List.of(
                new FreightProduct(UUID.randomUUID(),
                        "Standard Parcel",
                        List.of(new MaxWeightConstraint(new Weight(new BigDecimal("35.00"), WeightUnit.KILOGRAM)))
                ),
                new FreightProduct(UUID.randomUUID(),
                        "Heavy Cargo",
                        List.of(new MaxWeightConstraint(new Weight(new BigDecimal("100.00"), WeightUnit.KILOGRAM)))
                ),
                new FreightProduct(UUID.randomUUID(),
                        "Flexible Courier Service",
                        List.of(
                                new MaxWeightConstraint(new Weight(new BigDecimal("10.00"), WeightUnit.KILOGRAM)),
                                new AnyConstraint(
                                        new MaxLengthConstraint(new Measure<>(new BigDecimal("1200"), DistanceUnit.MILLIMETER)),
                                        new MaxLengthPlusGirthConstraint(new Measure<>(new BigDecimal("2000"), DistanceUnit.MILLIMETER))
                                )
                        )
                ),
                // Basic Gas Transport: Only supports Class 2
                new FreightProduct(
                        UUID.randomUUID(), "Basic Gas Transport",
                        List.of(new AdrConstraint(Set.of(AdrClass.CLASS_2_GASES)))
                ),
                // Hazmat Premium: Supports both Gases and Flammable Liquids
                new FreightProduct(
                        UUID.randomUUID(), "Hazmat Premium",
                        List.of(new AdrConstraint(Set.of(
                                AdrClass.CLASS_2_GASES,
                                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS
                        )))
                )
        );
    }

    @Override
    public List<FreightProduct> findAll() {
        return products;
    }
}
