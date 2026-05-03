package no.clueless.opencargo.catalog.domain.service;

import no.clueless.opencargo.bindings.ConsignorListDTO;
import no.clueless.opencargo.catalog.port.in.CountConsignorsUseCase;
import no.clueless.opencargo.catalog.port.in.ListConsignorsUseCase;
import no.clueless.opencargo.domain.model.Consignor;
import no.clueless.opencargo.infrastructure.marshalling.XmlMarshaller;
import no.clueless.opencargo.shared.Population;

import java.util.HashSet;
import java.util.Set;

public class ConsignorService implements CountConsignorsUseCase, ListConsignorsUseCase {
    private ConsignorListDTO consignorListDTO;

    private ConsignorListDTO getConsignorListDTO() {
        if (consignorListDTO == null) {
            consignorListDTO = XmlMarshaller.unmarshalResourceSilently("consignors.xml", ConsignorListDTO.class);
        }
        return consignorListDTO;
    }

    @Override
    public int countConsignors() {
        var consignorListDTO = getConsignorListDTO();
        return consignorListDTO == null || consignorListDTO.getConsignor() == null ? 0 : consignorListDTO.getConsignor().size();
    }

    @Override
    public Population<Consignor, Set<Consignor>> listConsignors() {
        var consignorListDTO = getConsignorListDTO();
        return consignorListDTO == null || consignorListDTO.getConsignor() == null ? null : consignorListDTO.getConsignor()
                .stream()
                .map(consignorDTO -> new Consignor(consignorDTO.getId(), consignorDTO.getNumber(), consignorDTO.getName()))
                .collect(Population.collector(HashSet::new));
    }

    private static final class SingletonHolder {
        private static final ConsignorService INSTANCE = new ConsignorService();
    }

    public static ConsignorService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
