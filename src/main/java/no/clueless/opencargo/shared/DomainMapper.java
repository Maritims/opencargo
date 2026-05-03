package no.clueless.opencargo.shared;

@FunctionalInterface
public interface DomainMapper<T> {
    T toDomain();
}
