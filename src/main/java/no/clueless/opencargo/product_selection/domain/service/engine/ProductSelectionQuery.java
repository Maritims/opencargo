package no.clueless.opencargo.product_selection.domain.service.engine;

import no.clueless.opencargo.product_selection.port.in.RequestProductsCommand;
import no.clueless.opencargo.domain.model.geography.Address;
import no.clueless.opencargo.domain.model.Cargo;

public class ProductSelectionQuery extends RequestProductsCommand {
    public ProductSelectionQuery(Cargo cargo, Address destination) {
        super(cargo, destination);
    }
}
