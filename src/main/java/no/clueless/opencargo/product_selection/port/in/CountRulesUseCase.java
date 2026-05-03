package no.clueless.opencargo.product_selection.port.in;

import no.clueless.opencargo.product_selection.domain.service.RulesService;

/**
 * Use case for retrieving the total number of product selection rules currently registered in the system.
 */
public interface CountRulesUseCase {
    /**
     * Gets the total count of rules defined in the product selection domain.
     *
     * @return non-negative integer representing the total number of rules.
     */
    int countRules();

    /**
     * Factory method to get the default implementation of this use case. Currently backed by the {@link RulesService} singleton.
     */
    static CountRulesUseCase create() {
        return RulesService.getInstance();
    }
}
