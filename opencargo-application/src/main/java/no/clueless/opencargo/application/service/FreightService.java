package no.clueless.opencargo.application.service;

import no.clueless.opencargo.application.dto.FindProductsQuery;
import no.clueless.opencargo.application.ports.input.FindEligibleProductsUseCase;
import no.clueless.opencargo.application.ports.input.FindPriceUseCase;
import no.clueless.opencargo.application.ports.input.FindProductsUseCase;
import no.clueless.opencargo.application.ports.output.FreightPriceRepository;
import no.clueless.opencargo.application.ports.output.FreightProductRepository;
import no.clueless.opencargo.domain.model.*;
import no.clueless.opencargo.domain.physical.Destination;
import no.clueless.opencargo.domain.physical.Dimensions;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.shared.AdrClass;
import no.clueless.opencargo.domain.shared.CountryCode;
import no.clueless.opencargo.domain.shared.Measure;

import java.util.*;
import java.util.stream.Collectors;

public class FreightService implements FindEligibleProductsUseCase, FindProductsUseCase, FindPriceUseCase {
    private final FreightPriceRepository   freightPriceRepository;
    private final FreightProductRepository freightProductRepository;

    public FreightService(FreightPriceRepository freightPriceRepository, FreightProductRepository freightProductRepository) {
        this.freightPriceRepository   = Objects.requireNonNull(freightPriceRepository, "freightPriceRepository cannot be null");
        this.freightProductRepository = Objects.requireNonNull(freightProductRepository, "freightProductRepository cannot be null");
    }

    @Override
    public List<FreightProduct> findEligibleProducts(FindProductsQuery query) {
        if (query == null) {
            throw new IllegalArgumentException("query cannot be null");
        }

        var width      = new Measure<>(query.getWidth(), query.getDistanceUnit());
        var height     = new Measure<>(query.getHeight(), query.getDistanceUnit());
        var length     = new Measure<>(query.getLength(), query.getDistanceUnit());
        var dimensions = new Dimensions(width, height, length);
        var weight     = new Weight(query.getWeight(), query.getWeightUnit());
        var adrRatings = query.getAdrClassShortCodes() == null ? new HashSet<AdrClass>() : AdrClass.fromShortCodes(query.getAdrClassShortCodes());
        var parcel     = new Parcel(UUID.randomUUID(), dimensions, weight, adrRatings, new Destination(new CountryCode("NO"), "3241"), null);

        var stream = freightProductRepository.findAll()
                .stream()
                .filter(freightProduct -> freightProduct.isEligible(parcel));

        if (query.getAcceptableCarrierIds() != null && !query.getAcceptableCarrierIds().isEmpty()) {
            stream = stream.filter(freightProduct -> query.getAcceptableCarrierIds().contains(freightProduct.getCarrierId().toString()));
        } else if (query.getUnacceptableCarrierIds() != null && !query.getUnacceptableCarrierIds().isEmpty()) {
            stream = stream.filter(freightProduct -> !query.getUnacceptableCarrierIds().contains(freightProduct.getCarrierId().toString()));
        }

        return stream.collect(Collectors.toList());
    }

    @Override
    public List<FreightProduct> findProducts() {
        return freightProductRepository.findAll();
    }

    @Override
    public List<FreightPrice> findPrice(FreightProductId productId) {
        return freightPriceRepository.findAll();
    }
}
