package no.clueless.opencargo.infrastructure.persistence;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import no.clueless.opencargo.application.ports.output.FreightProductRepository;
import no.clueless.opencargo.domain.criteria.*;
import no.clueless.opencargo.domain.model.FreightProduct;
import no.clueless.opencargo.domain.model.FreightProductId;
import no.clueless.opencargo.domain.physical.Dimensions;
import no.clueless.opencargo.domain.physical.DistanceUnit;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.shared.Measure;
import no.clueless.opencargo.infrastructure.persistence.xml.*;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class XmlFreightProductRepository implements FreightProductRepository {
    private final List<FreightProduct> products;

    public XmlFreightProductRepository(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null");
        }

        Unmarshaller xmlProductCatalogUnmarshaller;
        try {
            xmlProductCatalogUnmarshaller = JAXBContext.newInstance(XmlProductCatalog.class).createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to create unmarshaller", e);
        }

        XmlProductCatalog xmlProductCatalog;
        try {
            xmlProductCatalog = (XmlProductCatalog) xmlProductCatalogUnmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to unmarshal XML product catalog");
        }

        products = mapToDomain(xmlProductCatalog);
    }

    protected Measure<DistanceUnit> mapToMeasure(XmlDistanceConstraint xmlDistanceConstraint) {
        if (xmlDistanceConstraint == null) {
            throw new IllegalArgumentException("xmlDistanceConstraint cannot be null");
        }
        return new Measure<>(xmlDistanceConstraint.getValue(), xmlDistanceConstraint.getUnit());
    }

    protected Constraint mapToDomain(XmlConstraint xmlConstraint) {
        if (xmlConstraint == null) {
            throw new IllegalArgumentException("xmlConstraint cannot be null");
        }

        Constraint constraint;

        if (xmlConstraint instanceof XmlMaxLengthConstraint) {
            constraint = new MaxLengthConstraint(mapToMeasure((XmlDistanceConstraint) xmlConstraint));
        } else if (xmlConstraint instanceof XmlMaxLengthPlusGirthConstraint) {
            constraint = new MaxLengthPlusGirthConstraint(mapToMeasure((XmlDistanceConstraint) xmlConstraint));
        } else if (xmlConstraint instanceof XmlMaxWeightConstraint) {
            var xmlMaxWeightConstraint = (XmlMaxWeightConstraint) xmlConstraint;
            var maxWeight              = new Weight(xmlMaxWeightConstraint.getMaxWeight(), xmlMaxWeightConstraint.getUnit());
            constraint = new MaxWeightConstraint(maxWeight);
        } else if (xmlConstraint instanceof XmlMaxDimensionsConstraint) {
            var xmlMaxDimensionsConstraint = (XmlMaxDimensionsConstraint) xmlConstraint;
            constraint = new MaxDimensionsConstraint(new Dimensions(
                    mapToMeasure(xmlMaxDimensionsConstraint.getWidth()),
                    mapToMeasure(xmlMaxDimensionsConstraint.getLength()),
                    mapToMeasure(xmlMaxDimensionsConstraint.getHeight())
            ));
        } else if (xmlConstraint instanceof XmlMinDimensionsConstraint) {
            var xmlMinDimensionsConstraint = (XmlDimensionsConstraint) xmlConstraint;
            constraint = new MinDimensionsConstraint(new Dimensions(
                    mapToMeasure(xmlMinDimensionsConstraint.getWidth()),
                    mapToMeasure(xmlMinDimensionsConstraint.getLength()),
                    mapToMeasure(xmlMinDimensionsConstraint.getHeight())
            ));
        } else if (xmlConstraint instanceof XmlConstraints) {
            var xmlConstraints = (XmlConstraints) xmlConstraint;
            var constraints    = mapToDomain(xmlConstraints);
            constraint = new AnyConstraint(constraints.toArray(new Constraint[0]));
        } else {
            throw new IllegalArgumentException("Unknown constraint type: " + xmlConstraint.getClass());
        }

        return constraint;
    }

    protected List<Constraint> mapToDomain(XmlConstraints xmlConstraints) {
        if (xmlConstraints == null) {
            throw new IllegalArgumentException("xmlConstraints cannot be null");
        }

        return xmlConstraints.getConstraints()
                .stream()
                .filter(Objects::nonNull)
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    protected FreightProduct mapToDomain(XmlProduct xmlProduct) {
        if (xmlProduct == null) {
            throw new IllegalArgumentException("xmlProduct cannot be null");
        }

        return new FreightProduct(
                new FreightProductId(xmlProduct.getId()),
                xmlProduct.getName(),
                mapToDomain(xmlProduct.getConstraints())
        );
    }

    protected List<FreightProduct> mapToDomain(XmlProductCatalog xmlProductCatalog) {
        if (xmlProductCatalog == null) {
            throw new IllegalArgumentException("xmlProductCatalog cannot be null");
        }

        return xmlProductCatalog.getProducts()
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<FreightProduct> findAll() {
        return products;
    }
}
