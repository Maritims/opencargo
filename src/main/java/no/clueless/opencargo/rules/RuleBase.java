package no.clueless.opencargo.rules;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;
import java.util.Set;

abstract class RuleBase implements Rule {
    private final int          id;
    private final Integer      consignorId;
    private final Set<Integer> productId;
    private final String       number;
    private final String       name;
    private final int          priority;
    private final boolean      isTerminal;

    protected RuleBase(int id, Integer consignorId, Set<Integer> productId, String number, String name, int priority, boolean isTerminal) {
        this.id          = ArgumentExceptionHelper.throwIfNegative(id, "id");
        this.consignorId = consignorId;
        this.productId   = productId;
        this.number      = ArgumentExceptionHelper.throwIfNullOrBlank(number, "number");
        this.name        = ArgumentExceptionHelper.throwIfNullOrBlank(name, "name");
        this.priority    = ArgumentExceptionHelper.throwIfNegative(priority, "priority");
        this.isTerminal  = isTerminal;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Integer getConsignorId() {
        return consignorId;
    }

    @Override
    public Set<Integer> getProductIds() {
        return productId;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RuleBase ruleBase = (RuleBase) o;
        return id == ruleBase.id && priority == ruleBase.priority && isTerminal == ruleBase.isTerminal && Objects.equals(consignorId, ruleBase.consignorId) && Objects.equals(productId, ruleBase.productId) && Objects.equals(number, ruleBase.number) && Objects.equals(name, ruleBase.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, consignorId, productId, number, name, priority, isTerminal);
    }

    @Override
    public String toString() {
        return "RuleBase{" +
                "id=" + id +
                ", consignorId=" + consignorId +
                ", productId=" + productId +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", isTerminal=" + isTerminal +
                '}';
    }
}
