package no.clueless.opencargo.pricing;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class PricingRules implements Iterable<PricingRule> {
    private final Set<PricingRule> rules;

    public PricingRules(Set<PricingRule> rules) {
        this.rules = ArgumentExceptionHelper.throwIfNullOrEmpty(rules, "rules");
    }

    public int size() {
        return rules.size();
    }

    @Override
    public Iterator<PricingRule> iterator() {
        return rules.iterator();
    }

    public Stream<PricingRule> stream() {
        return rules.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PricingRules that = (PricingRules) o;
        return Objects.equals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rules);
    }

    @Override
    public String toString() {
        return "PricingRules{" +
                "rules=" + rules +
                '}';
    }
}
