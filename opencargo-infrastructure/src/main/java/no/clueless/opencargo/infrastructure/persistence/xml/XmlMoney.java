package no.clueless.opencargo.infrastructure.persistence.xml;

import jakarta.xml.bind.annotation.*;
import no.clueless.opencargo.domain.shared.DomainMapper;
import no.clueless.opencargo.domain.shared.Money;

import java.math.BigDecimal;
import java.util.Currency;

@XmlRootElement(name = "money")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMoney implements XmlAmountModifier, DomainMapper<Money> {
    @XmlValue
    private BigDecimal amount;
    @XmlAttribute(name = "currency", required = true)
    private String     currencyCode;


    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public Money toDomain() {
        return new Money(amount, Currency.getInstance(currencyCode));
    }
}
