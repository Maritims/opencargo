package no.clueless.opencargo.catalog;

import no.clueless.opencargo.bindings.ProductDTO;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Objects;

public class Product {
    private final int    id;
    private final int    consignorId;
    private final String number;
    private final String name;

    public Product(int id, int consignorId, String number, String name) {
        this.id          = ArgumentExceptionHelper.throwIfNegative(id, "id");
        this.consignorId = ArgumentExceptionHelper.throwIfNegative(consignorId, "consignorId");
        this.number      = ArgumentExceptionHelper.throwIfNullOrBlank(number, "number");
        this.name        = ArgumentExceptionHelper.throwIfNullOrBlank(name, "name");
    }

    public int getId() {
        return id;
    }

    public int getConsignorId() {
        return consignorId;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && consignorId == product.consignorId && Objects.equals(number, product.number) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, consignorId, number, name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", consignorId=" + consignorId +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static Product from(ProductDTO dto) {
        ArgumentExceptionHelper.throwIfNull(dto, "dto");
        return new Product(dto.getId(), dto.getConsignorId(), dto.getNumber(), dto.getName());
    }
}
