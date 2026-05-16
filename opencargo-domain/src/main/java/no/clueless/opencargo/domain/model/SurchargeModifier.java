package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.shared.Money;
import no.clueless.opencargo.domain.shared.Percentage;

public class SurchargeModifier {
    private final Money      money;
    private final Percentage percentage;

    private SurchargeModifier(Money money, Percentage percentage) {
        if (money == null && percentage == null) {
            throw new IllegalArgumentException("money and percentage cannot both be null");
        }
        if (money != null && percentage != null) {
            throw new IllegalArgumentException("money and percentage cannot both be set");
        }
        this.money = money;
        this.percentage = percentage;
    }

    public static SurchargeModifier money(Money money) {
        if (money == null) {
            throw new IllegalArgumentException("money cannot be null");
        }
        return new SurchargeModifier(money, null);
    }

    public static SurchargeModifier percentage(Percentage percentage) {
        if (percentage == null) {
            throw new IllegalArgumentException("percentage cannot be null");
        }
        return new SurchargeModifier(null, percentage);
    }

    public Money getMoney() {
        return money;
    }

    public Percentage getPercentage() {
        return percentage;
    }

    public Money applyTo(Money money) {
        if (money == null) {
            throw new IllegalArgumentException("money cannot be null");
        }
        if (this.money != null) {
            return money.add(this.money);
        } else if (this.percentage != null) {
            return money.multiply(this.percentage.getFactor());
        } else {
            throw new IllegalStateException("surcharge modifier is not set");
        }
    }

    public boolean isMoney() {
        return money != null;
    }

    public boolean isPercentage() {
        return percentage != null;
    }
}
