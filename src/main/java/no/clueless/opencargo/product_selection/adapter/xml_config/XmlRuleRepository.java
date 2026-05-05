package no.clueless.opencargo.product_selection.adapter.xml_config;

import no.clueless.opencargo.bindings.Rules;
import no.clueless.opencargo.infrastructure.marshalling.XmlMarshaller;
import no.clueless.opencargo.product_selection.domain.model.Rule;
import no.clueless.opencargo.product_selection.port.out.RuleRepository;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Set;
import java.util.stream.Collectors;

public class XmlRuleRepository implements RuleRepository {
    private final String        filename;
    private final XmlRuleMapper ruleMapper;

    private Set<Rule> rules;

    public XmlRuleRepository(String filename, XmlRuleMapper ruleMapper) {
        ArgumentExceptionHelper.throwIfNullOrBlank(filename, "filename");
        this.ruleMapper = ArgumentExceptionHelper.throwIfNull(ruleMapper, "ruleMapper");
        this.filename   = filename;
    }

    @Override
    public int getTotalCount() {
        return getAll().size();
    }

    @Override
    public Set<Rule> getAll() {
        if(rules == null) {
            rules = XmlMarshaller.unmarshalResourceSilently(filename, Rules.class)
                    .getGeographyOrWeightOrWidth()
                    .stream()
                    .map(ruleMapper::toDomain)
                    .collect(Collectors.toSet());
        }
        return rules;
    }
}
