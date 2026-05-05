package no.clueless.opencargo.product_selection.port.out;

import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.product_selection.adapter.xml_config.XmlProductRepository;

import java.util.Optional;
import java.util.Set;

public interface ProductRepository {
    int getTotalCount();

    Set<Product> getAll();

    Optional<Product> findByNumber(String number);

    static ProductRepository create() {
        return new XmlProductRepository();
    }
}
