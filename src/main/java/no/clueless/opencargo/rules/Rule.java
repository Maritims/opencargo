package no.clueless.opencargo.rules;

import jakarta.annotation.Nonnull;
import no.clueless.opencargo.ProductQuery;
import no.clueless.opencargo.bindings.RuleListDTO;
import no.clueless.opencargo.util.ArgumentExceptionHelper;
import no.clueless.opencargo.util.XmlMarshaller;

import java.io.InputStream;
import java.util.Set;

/**
 * A base interface for rules of various types.
 * The type of rule is identified by the interface it implements, and therefore any rule interface meant for direct implementation should have a descriptive name.
 * Examples of good names are "HeightRule", "LengthRule" etc.
 */
public interface Rule extends Comparable<Rule> {
    int getId();

    Set<Integer> getConsignorIds();

    Set<Integer> getProductIds();

    String getNumber();

    String getName();

    int getPriority();

    EvaluationResult evaluate(ProductQuery productQuery);

    /**
     * Default implementation to allow sorting by priority.
     */
    @Override
    default int compareTo(@Nonnull Rule other) {
        ArgumentExceptionHelper.throwIfNull(other, "other");
        return Integer.compare(getPriority(), other.getPriority());
    }

    static Object unmarshal(InputStream is) {
        ArgumentExceptionHelper.throwIfNull(is, "is");
        return XmlMarshaller.unmarshalResourceSilently(is, RuleListDTO.class);
    }
}
