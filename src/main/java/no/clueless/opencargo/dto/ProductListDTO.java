package no.clueless.opencargo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import no.clueless.opencargo.domain.Products;
import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProductListDTO implements DTO<Products> {
    private List<ProductDTO> products = new ArrayList<>();

    public ProductListDTO() {
    }

    @XmlElementRef
    public List<ProductDTO> getProducts() {
        return products;
    }

    @SuppressWarnings("unused")
    public void setProducts(List<ProductDTO> products) {
        this.products = ArgumentExceptionHelper.throwIfNull(products, "products");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductListDTO that = (ProductListDTO) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(products);
    }

    @Override
    public String toString() {
        return "ProductListDTO{" +
                "products=" + products +
                '}';
    }

    @Override
    public Products toDomain() {
        return products.stream()
                .map(ProductDTO::toDomain)
                .collect(Products.collector());
    }
}
