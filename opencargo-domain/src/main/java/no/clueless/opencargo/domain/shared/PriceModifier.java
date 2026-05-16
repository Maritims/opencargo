package no.clueless.opencargo.domain.shared;

public class PriceModifier {
    private final Money      money;
    private final Percentage percentage;

    private PriceModifier(Money money, Percentage percentage) {
        if (money == null && percentage == null) {
            throw new IllegalArgumentException("money and percentage cannot both be null");
        }
        if (money != null && percentage != null) {
            throw new IllegalArgumentException("money and percentage cannot both be set");
        }
        this.money = money;
        this.percentage = percentage;
    }

    public static PriceModifier money(Money money) {
        if (money == null) {
            throw new IllegalArgumentException("money cannot be null");
        }
        return new PriceModifier(money, null);
    }

    public static PriceModifier percentage(Percentage percentage) {
        if (percentage == null) {
            throw new IllegalArgumentException("percentage cannot be null");
        }
        return new PriceModifier(null, percentage);
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
