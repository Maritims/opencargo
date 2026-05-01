package no.clueless.opencargo.dto;

public interface DTO<TDomain> {
    TDomain toDomain();
}
