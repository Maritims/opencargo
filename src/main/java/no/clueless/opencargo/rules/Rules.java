package no.clueless.opencargo.rules;

import no.clueless.opencargo.dto.RuleListDTO;
import no.clueless.opencargo.util.ArgumentExceptionHelper;
import no.clueless.opencargo.util.XmlMarshaller;

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

public class Rules implements Iterable<Rule> {
    private final LinkedHashSet<Rule> rules;

    public Rules(Set<Rule> rules) {
        this.rules = ArgumentExceptionHelper.throwIfNullOrEmpty(rules, "rules")
                .stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Rules(Rule... rules) {
        this(Set.of(rules));
    }

    public static Rules fromResources() {
        return XmlMarshaller.unmarshalResourceSilently("rules.xml", RuleListDTO.class).toDomain();
    }

    @Override
    public Iterator<Rule> iterator() {
        return rules.iterator();
    }

    public Stream<Rule> stream() {
        return rules.stream();
    }

    public static Collector<Rule, Set<Rule>, Rules> collector() {
        return new Collector<>() {

            @Override
            public Supplier<Set<Rule>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<Rule>, Rule> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<Rule>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<Rule>, Rules> finisher() {
                return Rules::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }
}
