package no.clueless.opencargo.rules;

import no.clueless.opencargo.Cargo;

import java.math.BigDecimal;

public interface ScalarRule extends Rule {
    BigDecimal resolveValue(Cargo cargo);
}
