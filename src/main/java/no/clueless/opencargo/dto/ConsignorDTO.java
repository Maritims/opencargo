package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import no.clueless.opencargo.domain.Consignor;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Objects;

@XmlRootElement(name = "consignor")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ConsignorDTO implements DTO<Consignor> {
    private int    id;
    private String number;
    private String name;

    public ConsignorDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = ArgumentExceptionHelper.throwIfZeroOrNegative(id, "id");
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

    @Override
    public Consignor toDomain() {
        return new Consignor(id, number, name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConsignorDTO that = (ConsignorDTO) o;
        return id == that.id && Objects.equals(number, that.number) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name);
    }

    @Override
    public String toString() {
        return "ConsignorDTO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
