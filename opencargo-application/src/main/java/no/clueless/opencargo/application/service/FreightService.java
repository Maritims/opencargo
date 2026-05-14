package no.clueless.opencargo.application.service;

import no.clueless.opencargo.application.dto.FindProductsQuery;
import no.clueless.opencargo.application.ports.input.FindEligibleProductsUseCase;
import no.clueless.opencargo.application.ports.output.FreightProductRepository;
import no.clueless.opencargo.domain.model.*;
import no.clueless.opencargo.domain.physical.Destination;
import no.clueless.opencargo.domain.physical.Dimensions;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.shared.AdrClass;
import no.clueless.opencargo.domain.shared.CountryCode;
import no.clueless.opencargo.domain.shared.Measure;

import java.util.*;
import java.util.stream.Collectors;

public class FreightService implements FindEligibleProductsUseCase {
    private final FreightProductRepository freightProductRepository;

    public FreightService(FreightProductRepository freightProductRepository) {
        this.freightProductRepository = Objects.requireNonNull(freightProductRepository);
    }

    @Override
    public List<FreightProduct> findForCriteria(FindProductsQuery query) {
        if (query == null) {
            throw new IllegalArgumentException("query cannot be null");
        }

        var width      = new Measure<>(query.getWidth(), DistanceUnit.MILLIMETER);
        var height     = new Measure<>(query.getHeight(), DistanceUnit.MILLIMETER);
        var length     = new Measure<>(query.getLength(), DistanceUnit.MILLIMETER);
        var dimensions = new Dimensions(width, height, length);
        var weight     = new Weight(query.getWeight(), query.getWeightUnit());
        var adrRatings = query.getAdrClassShortCodes() == null ? new HashSet<AdrClass>() : AdrClass.fromShortCodes(query.getAdrClassShortCodes());
        var parcel     = new Parcel(UUID.randomUUID(), dimensions, weight, adrRatings, new Destination(new CountryCode("NO"), "3241"));

        return freightProductRepository.findAll()
                .stream()
                .filter(freightProduct -> freightProduct.isEligible(parcel))
                .collect(Collectors.toList());
    }
}
