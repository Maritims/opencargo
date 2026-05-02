package no.clueless.opencargo.selection;

import java.util.Objects;
import java.util.Set;

abstract class RuleBase implements SelectionRule {
    private final RuleMetadata metadata;

    RuleBase(RuleMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public int getId() {
        return metadata.getId();
    }

    @Override
    public Set<Integer> getConsignorIds() {
        return metadata.getConsignorIds();
    }

    @Override
    public Set<Integer> getProductIds() {
        return metadata.getProductIds();
    }

    @Override
    public String getNumber() {
        return metadata.getNumber();
    }

    @Override
    public String getName() {
        return metadata.getName();
    }

    @Override
    public int getPriority() {
        return metadata.getPriority();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RuleBase ruleBase = (RuleBase) o;
        return Objects.equals(metadata, ruleBase.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(metadata);
    }

    @Override
    public String toString() {
        return "RuleBase{" +
                "metadata=" + metadata +
                '}';
    }
}
