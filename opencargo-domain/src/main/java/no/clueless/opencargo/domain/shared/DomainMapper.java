package no.clueless.opencargo.domain.shared;

@FunctionalInterface
public interface DomainMapper<TDomain> {
    TDomain toDomain();
}
