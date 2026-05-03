package no.clueless.opencargo.product_selection.port.in;

import no.clueless.opencargo.product_selection.domain.service.RulesService;

public interface CountRulesUseCase {
    int countRules();

    static CountRulesUseCase create() {
        return RulesService.getInstance();
    }
}
