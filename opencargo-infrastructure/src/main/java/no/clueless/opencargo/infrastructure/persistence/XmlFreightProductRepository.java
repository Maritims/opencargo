package no.clueless.opencargo.infrastructure.persistence;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import no.clueless.opencargo.application.ports.output.FreightProductRepository;
import no.clueless.opencargo.domain.criteria.*;
import no.clueless.opencargo.domain.model.CarrierId;
import no.clueless.opencargo.domain.model.FreightProduct;
import no.clueless.opencargo.domain.model.FreightProductId;
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

    protected Constraint mapToDomain(XmlConstraint<?> xmlConstraint) {
        if (xmlConstraint == null) {
            throw new IllegalArgumentException("xmlConstraint cannot be null");
        }
        return xmlConstraint.toDomain();
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
                new CarrierId(xmlProduct.getCarrierId()),
                xmlProduct.getName(),
                mapToDomain(xmlProduct.getConstraints()),
                xmlProduct.getPrice().toDomain()
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
