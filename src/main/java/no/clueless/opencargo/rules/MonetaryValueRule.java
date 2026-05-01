package no.clueless.opencargo.rules;

import no.clueless.opencargo.Cargo;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.math.BigDecimal;
import java.util.Set;

public final class MonetaryValueRule extends ScalarRuleBase implements ScalarRule {
    public MonetaryValueRule(int id, Integer consignorId, Set<Integer> productIds, String number, String name, int priority, boolean isTerminal, BigDecimal min) {
        super(id, consignorId, productIds, number, name, priority, isTerminal, min, null);
    }

    @Override
    public BigDecimal resolveValue(Cargo cargo) {
        return  ArgumentExceptionHelper.throwIfNull(cargo, "cargo").getMonetaryValue();
    }
}
