package no.clueless.opencargo.pricing.breakdown;

import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

import java.math.BigDecimal;
import java.util.Objects;

public class PriceComponent {
    private final String             description;
    private final BigDecimal         amount;
    private final PriceComponentType componentType;

    public PriceComponent(String description, BigDecimal amount, PriceComponentType componentType) {
        this.description   = ArgumentExceptionHelper.throwIfNullOrBlank(description, "description");
        this.amount        = ArgumentExceptionHelper.throwIfNullOrNegative(amount, "amount");
        this.componentType = ArgumentExceptionHelper.throwIfNull(componentType, "componentType");
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PriceComponentType getComponentType() {
        return componentType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PriceComponent that = (PriceComponent) o;
        return Objects.equals(description, that.description) && Objects.equals(amount, that.amount) && componentType == that.componentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, amount, componentType);
    }

    @Override
    public String toString() {
        return "PriceComponent{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", componentType=" + componentType +
                '}';
    }
}
