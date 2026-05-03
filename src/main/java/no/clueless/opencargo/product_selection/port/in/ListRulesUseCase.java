package no.clueless.opencargo.product_selection.port.in;

import no.clueless.opencargo.product_selection.domain.service.RulesService;
import no.clueless.opencargo.product_selection.domain.service.engine.Rule;
import no.clueless.opencargo.shared.Population;

import java.util.Set;

public interface ListRulesUseCase {
    Population<Rule, Set<Rule>> listRules();

    static ListRulesUseCase create() {
        return RulesService.getInstance();
    }
}
