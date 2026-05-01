package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import no.clueless.opencargo.rules.MinDimensionsRule;
import no.clueless.opencargo.rules.Rule;

import java.math.BigDecimal;

@XmlRootElement(name = "minDimensionsRule")
@XmlType(name = "minDimensionsRule")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MinDimensionsRuleDTO extends RuleDTO {
    private BigDecimal minWidth;
    private BigDecimal minLength;
    private BigDecimal minHeight;

    public MinDimensionsRuleDTO() {}

    public BigDecimal getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(BigDecimal minWidth) {
        this.minWidth = minWidth;
    }

    public BigDecimal getMinLength() {
        return minLength;
    }

    public void setMinLength(BigDecimal minLength) {
        this.minLength = minLength;
    }

    public BigDecimal getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(BigDecimal minHeight) {
        this.minHeight = minHeight;
    }

    @Override
    public Rule toDomain() {
        return new MinDimensionsRule(getId(), getConsignorId(), getProductIds(), getNumber(), getName(), getPriority(), false, minWidth, minLength, minHeight);
    }
}
