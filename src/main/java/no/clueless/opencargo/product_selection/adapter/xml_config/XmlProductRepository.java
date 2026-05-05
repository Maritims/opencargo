package no.clueless.opencargo.product_selection.adapter.xml_config;

import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.product_selection.port.out.ProductRepository;

import java.util.Optional;
import java.util.Set;

public class XmlProductRepository implements ProductRepository {
    @Override
    public int getTotalCount() {
        return 0;
    }

    @Override
    public Set<Product> getAll() {
        return Set.of();
    }

    @Override
    public Optional<Product> findByNumber(String number) {
        return Optional.empty();
    }
}
