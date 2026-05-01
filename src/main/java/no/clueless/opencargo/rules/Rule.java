package no.clueless.opencargo.rules;

import no.clueless.opencargo.Query;
import no.clueless.opencargo.dto.RuleListDTO;
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

    Integer getConsignorId();

    Set<Integer> getProductIds();

    String getNumber();

    String getName();

    int getPriority();

    EvaluationResult evaluate(Query query);

    /**
     * Default implementation to allow sorting by priority.
     */
    @Override
    default int compareTo(Rule other) {
        ArgumentExceptionHelper.throwIfNull(other, "other");
        return Integer.compare(getPriority(), other.getPriority());
    }

    static RuleListDTO unmarshal(InputStream is) {
        ArgumentExceptionHelper.throwIfNull(is, "is");

        return XmlMarshaller.unmarshalResourceSilently(is, RuleListDTO.class);
    }
}
