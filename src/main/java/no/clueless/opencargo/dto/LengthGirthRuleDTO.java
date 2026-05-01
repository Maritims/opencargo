package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import no.clueless.opencargo.rules.LengthGirthRule;
import no.clueless.opencargo.rules.Rule;

import java.math.BigDecimal;

@SuppressWarnings("unused")
@XmlRootElement(name = "lengthGirthRule")
@XmlType(name = "lengthGirthRule")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class LengthGirthRuleDTO extends RuleDTO {
    private BigDecimal maxLength;
    private BigDecimal maxLengthAndGirth;

    public BigDecimal getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(BigDecimal maxLength) {
        this.maxLength = maxLength;
    }

    public BigDecimal getMaxLengthAndGirth() {
        return maxLengthAndGirth;
    }

    public void setMaxLengthAndGirth(BigDecimal maxLengthAndGirth) {
        this.maxLengthAndGirth = maxLengthAndGirth;
    }

    @Override
    public Rule toDomain() {
        return new LengthGirthRule(getId(), getConsignorId(), getProductIds(), getNumber(), getName(), getPriority(), false, maxLength, maxLengthAndGirth);
    }
}
