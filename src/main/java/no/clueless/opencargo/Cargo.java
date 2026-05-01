package no.clueless.opencargo;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.math.BigDecimal;
import java.util.Objects;

public class Cargo {
    private final BigDecimal weight;
    private final BigDecimal width;
    private final BigDecimal length;
    private final BigDecimal height;
    private final BigDecimal monetaryValue;

    public Cargo(BigDecimal weight, BigDecimal width, BigDecimal length, BigDecimal height, BigDecimal monetaryValue) {
        this.weight        = ArgumentExceptionHelper.throwIfNullOrNegative(weight, "weight");
        this.width         = ArgumentExceptionHelper.throwIfNullOrNegative(width, "width");
        this.length        = ArgumentExceptionHelper.throwIfNullOrNegative(length, "length");
        this.height        = ArgumentExceptionHelper.throwIfNullOrNegative(height, "height");
        this.monetaryValue = ArgumentExceptionHelper.throwIfNullOrNegative(monetaryValue, "monetaryValue");
    }

    public Cargo(double weight, double width, double length, double height, double monetaryValue) {
        this(BigDecimal.valueOf(weight), BigDecimal.valueOf(width), BigDecimal.valueOf(length), BigDecimal.valueOf(height), BigDecimal.valueOf(monetaryValue));
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getLength() {
        return length;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cargo cargo = (Cargo) o;
        return Objects.equals(weight, cargo.weight) && Objects.equals(width, cargo.width) && Objects.equals(length, cargo.length) && Objects.equals(height, cargo.height) && Objects.equals(monetaryValue, cargo.monetaryValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, width, length, height, monetaryValue);
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "weight=" + weight +
                ", width=" + width +
                ", length=" + length +
                ", height=" + height +
                ", monetaryValue=" + monetaryValue +
                '}';
    }
}
