package no.clueless.opencargo.application.ports.input;

import no.clueless.opencargo.application.dto.FindProductsQuery;
import no.clueless.opencargo.domain.model.FreightProduct;

import java.util.List;

public interface FindEligibleProductsUseCase {
    List<FreightProduct> findForCriteria(FindProductsQuery query);
}
