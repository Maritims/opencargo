package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import no.clueless.opencargo.rules.Rule;
import no.clueless.opencargo.rules.WeightRule;

import java.math.BigDecimal;

@XmlRootElement(name = "weightRule")
@XmlType(name = "weightRule")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class WeightRuleDTO extends RuleDTO {
    private BigDecimal min;
    private BigDecimal max;

    public WeightRuleDTO() {}

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    @Override
    public Rule toDomain() {
        return new WeightRule(getId(), getConsignorId(), getProductIds(), getNumber(), getName(), getPriority(), false, getMin(), getMax());
    }
}
