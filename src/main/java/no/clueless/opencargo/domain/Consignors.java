package no.clueless.opencargo.domain;

import no.clueless.opencargo.dto.ConsignorListDTO;
import no.clueless.opencargo.util.ArgumentExceptionHelper;
import no.clueless.opencargo.util.XmlMarshaller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Consignors implements Iterable<Consignor> {
    private final Set<Consignor> consignors;

    public Consignors(Set<Consignor> consignors) {
        this.consignors = ArgumentExceptionHelper.throwIfNullOrEmpty(consignors, "consignors");
    }

    @SuppressWarnings("unused")
    public Consignors(Consignor... consignors) {
        this(Set.of(consignors));
    }

    @SuppressWarnings("unused")
    public static Consignors fromResources() {
        return XmlMarshaller.unmarshalResourceSilently("consignors.xml", ConsignorListDTO.class).toDomain();
    }

    @Override
    public Iterator<Consignor> iterator() {
        return consignors.iterator();
    }

    public static Collector<Consignor, Set<Consignor>, Consignors> collector() {
        return new Collector<>() {

            @Override
            public Supplier<Set<Consignor>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<Consignor>, Consignor> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<Consignor>> combiner() {
                return (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                };
            }

            @Override
            public Function<Set<Consignor>, Consignors> finisher() {
                return Consignors::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }
}
