package no.clueless.opencargo.application.ports.output;

import no.clueless.opencargo.domain.model.FreightProduct;
import no.clueless.opencargo.domain.model.Parcel;

import java.util.List;

public interface FreightProductRepository {
    List<FreightProduct> findAll();
}
