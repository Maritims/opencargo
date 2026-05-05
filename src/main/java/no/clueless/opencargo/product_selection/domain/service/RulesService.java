package no.clueless.opencargo.product_selection.domain.service;

import no.clueless.opencargo.product_selection.domain.model.Rule;
import no.clueless.opencargo.product_selection.port.in.CountRulesUseCase;
import no.clueless.opencargo.product_selection.port.in.ListRulesUseCase;
import no.clueless.opencargo.product_selection.port.out.RuleRepository;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Set;

public class RulesService implements CountRulesUseCase, ListRulesUseCase {
    private final RuleRepository ruleRepository;

    public RulesService(RuleRepository ruleRepository) {
        this.ruleRepository = ArgumentExceptionHelper.throwIfNull(ruleRepository, "ruleProvider");
    }

    @Override
    public int countRules() {
        return ruleRepository.getTotalCount();
    }

    @Override
    public Set<Rule> listRules() {
        return ruleRepository.getAll();
    }

    private static final class SingletonHolder {
        private static final RulesService INSTANCE = new RulesService(RuleRepository.create());
    }

    public static RulesService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
