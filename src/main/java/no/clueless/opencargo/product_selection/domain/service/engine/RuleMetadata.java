package no.clueless.opencargo.product_selection.domain.service.engine;

import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Objects;
import java.util.Set;

public class RuleMetadata {
    private final int          id;
    private final String       number;
    private final String       name;
    private final int          priority;
    private final Set<Integer> consignorIds;
    private final Set<Integer> productIds;

    public RuleMetadata(int id, String number, String name, int priority, Set<Integer> consignorIds, Set<Integer> productIds) {
        this.id           = ArgumentExceptionHelper.throwIfNegative(id, "id");
        this.number       = ArgumentExceptionHelper.throwIfNullOrBlank(number, "number");
        this.name         = ArgumentExceptionHelper.throwIfNullOrBlank(name, "name");
        this.priority     = ArgumentExceptionHelper.throwIfNegative(priority, "priority");
        this.consignorIds = consignorIds == null ? Set.of() : consignorIds;
        this.productIds   = productIds == null ? Set.of() : productIds;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public Set<Integer> getConsignorIds() {
        return consignorIds;
    }

    public Set<Integer> getProductIds() {
        return productIds;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RuleMetadata that = (RuleMetadata) o;
        return id == that.id && priority == that.priority && Objects.equals(number, that.number) && Objects.equals(name, that.name) && Objects.equals(consignorIds, that.consignorIds) && Objects.equals(productIds, that.productIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name, priority, consignorIds, productIds);
    }

    @Override
    public String toString() {
        return "RuleMetadata{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", consignorIds=" + consignorIds +
                ", productIds=" + productIds +
                '}';
    }
}
