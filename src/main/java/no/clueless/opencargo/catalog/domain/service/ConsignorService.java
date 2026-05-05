package no.clueless.opencargo.catalog.domain.service;

import no.clueless.opencargo.bindings.ConsignorListType;
import no.clueless.opencargo.catalog.port.in.CountConsignorsUseCase;
import no.clueless.opencargo.catalog.port.in.ListConsignorsUseCase;
import no.clueless.opencargo.domain.model.Consignor;
import no.clueless.opencargo.infrastructure.marshalling.XmlMarshaller;
import no.clueless.opencargo.shared.Population;

import java.util.HashSet;
import java.util.Set;

public class ConsignorService implements CountConsignorsUseCase, ListConsignorsUseCase {
    private ConsignorListType consignorListDTO;

    private ConsignorListType getConsignors() {
        if (consignorListDTO == null) {
            consignorListDTO = XmlMarshaller.unmarshalResourceSilently("consignors.xml", ConsignorListType.class);
        }
        return consignorListDTO;
    }

    @Override
    public int countConsignors() {
        var consignors = getConsignors();
        return consignors == null || consignors.getConsignor() == null ? 0 : consignors.getConsignor().size();
    }

    @Override
    public Population<Consignor, Set<Consignor>> listConsignors() {
        var consignors = getConsignors();
        return consignors == null || consignors.getConsignor() == null ? null : consignors.getConsignor()
                .stream()
                .map(consignorDTO -> new Consignor(consignorDTO.getId().intValue(), consignorDTO.getNumber(), consignorDTO.getName()))
                .collect(Population.collector(HashSet::new));
    }

    private static final class SingletonHolder {
        private static final ConsignorService INSTANCE = new ConsignorService();
    }

    public static ConsignorService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
