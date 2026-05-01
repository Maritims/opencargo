package no.clueless.opencargo.applicability;

import no.clueless.opencargo.domain.Product;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;

public final class Rejection {
    private final Product product;
    private final String  reason;

    public Rejection(Product product, String reason) {
        this.product = ArgumentExceptionHelper.throwIfNull(product, "product");
        this.reason  = ArgumentExceptionHelper.throwIfNullOrBlank(reason, "reason");
    }

    public Product getProduct() {
        return product;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rejection rejection = (Rejection) o;
        return Objects.equals(product, rejection.product) && Objects.equals(reason, rejection.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, reason);
    }

    @Override
    public String toString() {
        return "Rejection{" +
                "product=" + product +
                ", reason='" + reason + '\'' +
                '}';
    }
}
