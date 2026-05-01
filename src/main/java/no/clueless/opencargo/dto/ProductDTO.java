package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import no.clueless.opencargo.domain.Product;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProductDTO implements DTO<Product> {
    private int    id;
    private int    consignorId;
    private String number;
    private String name;

    public ProductDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsignorId() {
        return consignorId;
    }

    public void setConsignorId(int consignorId) {
        this.consignorId = consignorId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Product toDomain() {
        return new  Product(id, consignorId, number, name);
    }
}
