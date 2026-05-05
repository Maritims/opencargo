package no.clueless.opencargo.product_selection.port.out;

import no.clueless.opencargo.domain.model.Consignor;
import no.clueless.opencargo.product_selection.adapter.xml_config.XmlConsignorRepository;

import java.util.Set;

public interface ConsignorRepository {
    int getTotalCount();

    Set<Consignor> getAll();

    static ConsignorRepository create() {
        return new XmlConsignorRepository();
    }
}
