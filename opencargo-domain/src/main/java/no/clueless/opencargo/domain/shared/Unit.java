package no.clueless.opencargo.domain.shared;

import java.math.BigDecimal;

public interface Unit {
    BigDecimal getMultiplier();

    String getSymbol();
}
