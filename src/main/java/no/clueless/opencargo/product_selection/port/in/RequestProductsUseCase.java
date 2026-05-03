package no.clueless.opencargo.product_selection.port.in;

import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.domain.model.applicability.ApplicabilityReports;
import no.clueless.opencargo.product_selection.domain.service.RequestProductsService;

/**
 * Use case for evaluating and selecting applicable products based on specific cargo and transport requirements.
 */
public interface RequestProductsUseCase {
    /**
     * Executes the product selection process for the given cargo and destination.
     * <p>
     * It evaluates the criteria provided in the {@code command} against the current rule set to determine which products are applicable.
     *
     * @param command The validated parameters for the request, including cargo details and constraints.
     * @return A {@link ApplicabilityReports} object containing the applicable products and detailed feedback on why others were rejected.
     */
    ApplicabilityReports<Product> requestProducts(RequestProductsCommand command);

    /**
     * Factory method to get the default implementation of this use case. Currently backed by the {@link RequestProductsService} singleton.
     */
    static RequestProductsUseCase create() {
        return RequestProductsService.getInstance();
    }
}
