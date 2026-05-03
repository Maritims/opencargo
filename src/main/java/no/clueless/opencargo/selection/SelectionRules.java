package no.clueless.opencargo.selection;

import no.clueless.opencargo.bindings.RuleListDTO;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;
import no.clueless.opencargo.infrastructure.xml.XmlMarshaller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectionRules implements Iterable<SelectionRule> {
    private final LinkedHashSet<SelectionRule> rules;

    public SelectionRules(Set<SelectionRule> rules) {
        this.rules = ArgumentExceptionHelper.throwIfNullOrEmpty(rules, "rules")
                .stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public SelectionRules(SelectionRule... rules) {
        this(Set.of(rules));
    }

    @Override
    public Iterator<SelectionRule> iterator() {
        return rules.iterator();
    }

    public Stream<SelectionRule> stream() {
        return rules.stream();
    }

    public static Collector<SelectionRule, Set<SelectionRule>, SelectionRules> collector() {
        return new Collector<>() {

            @Override
            public Supplier<Set<SelectionRule>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<SelectionRule>, SelectionRule> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<SelectionRule>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<SelectionRule>, SelectionRules> finisher() {
                return SelectionRules::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }

    public static SelectionRules from(RuleListDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        return SelectionRulesMapper.getInstance().mapToRules(dto);
    }

    public static SelectionRules fromResources() {
        var dto = XmlMarshaller.unmarshalResourceSilently("rules.xml", RuleListDTO.class);
        return SelectionRules.from(dto);
    }
}
