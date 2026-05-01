package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import no.clueless.opencargo.domain.Consignors;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "consignors")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ConsignorListDTO implements DTO<Consignors> {
    private List<ConsignorDTO> items = new ArrayList<>();

    public ConsignorListDTO() {
    }

    @XmlElement(name = "consignor")
    public List<ConsignorDTO> getConsignors() {
        return items;
    }

    public void setConsignors(List<ConsignorDTO> items) {
        this.items = ArgumentExceptionHelper.throwIfNullOrEmpty(items, "consignors");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConsignorListDTO that = (ConsignorListDTO) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(items);
    }

    @Override
    public String toString() {
        return "ConsignorListDTO{" +
                "consignors=" + items +
                '}';
    }

    @Override
    public Consignors toDomain() {
        return items.stream()
                .map(ConsignorDTO::toDomain)
                .collect(Consignors.collector());
    }
}
