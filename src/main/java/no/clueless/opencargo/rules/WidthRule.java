package no.clueless.opencargo.rules;

import no.clueless.opencargo.util.ArgumentExceptionHelper;
import no.clueless.opencargo.Cargo;

import java.math.BigDecimal;
import java.util.Set;

public final class WidthRule extends ScalarRuleBase implements ScalarRule {

    WidthRule(int id, Integer consignorId, Set<Integer> productIds, String number, String name, int priority, boolean isTerminal, BigDecimal min, BigDecimal max) {
        super(id, consignorId, productIds, number, name, priority, isTerminal, min, max);
    }

    @Override
    public BigDecimal resolveValue(Cargo cargo) {
        return ArgumentExceptionHelper.throwIfNull(cargo, "cargo").getWidth();
    }
}
