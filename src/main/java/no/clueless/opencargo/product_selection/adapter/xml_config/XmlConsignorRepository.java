package no.clueless.opencargo.product_selection.adapter.xml_config;

import no.clueless.opencargo.domain.model.Consignor;
import no.clueless.opencargo.product_selection.port.out.ConsignorRepository;

import java.util.Set;

public class XmlConsignorRepository implements ConsignorRepository {
    @Override
    public int getTotalCount() {
        return 0;
    }

    @Override
    public Set<Consignor> getAll() {
        return Set.of();
    }
}
