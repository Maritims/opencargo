package no.clueless.opencargo.product_selection.port.in;

import no.clueless.opencargo.product_selection.domain.service.RulesService;
import no.clueless.opencargo.product_selection.domain.service.engine.Rule;
import no.clueless.opencargo.shared.Population;

import java.util.Set;

/**
 * Use case for retrieving a list of all product selection rules currently registered in the system.
 */
public interface ListRulesUseCase {
    /**
     * Retrieves all registered product selection rules wrapped in a {@link Population} container.
     *
     * @return A {@link Population} object containing a {@link Set} of {@link Rule} entities providing both the data and any associated collection metadata.
     */
    Population<Rule, Set<Rule>> listRules();

    /**
     * Factory method to get the default implementation of this use case. Currently backed by the {@link RulesService} singleton.
     */
    static ListRulesUseCase create() {
        return RulesService.getInstance();
    }
}
