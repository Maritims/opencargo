package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import no.clueless.opencargo.rules.Rule;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Set;

@XmlAccessorType(XmlAccessType.PROPERTY)
public abstract class RuleDTO implements DTO<Rule> {
    private int          id;
    private Integer      consignorId;
    private Set<Integer> productIds;
    private String       number;
    private String       name;
    private int          priority;

    public RuleDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = ArgumentExceptionHelper.throwIfZeroOrNegative(id, "id");
    }

    public Integer getConsignorId() {
        return consignorId;
    }

    public void setConsignorId(Integer consignorId) {
        this.consignorId = ArgumentExceptionHelper.throwIfNegative(consignorId, "consignorId");
    }

    @XmlElementWrapper(name = "productIds")
    @XmlElement(name = "productId")
    public Set<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Integer> productIds) {
        this.productIds = productIds;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = ArgumentExceptionHelper.throwIfNullOrBlank(number, "number");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = ArgumentExceptionHelper.throwIfNullOrBlank(name, "name");
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = ArgumentExceptionHelper.throwIfNegative(priority, "priority");
    }
}
