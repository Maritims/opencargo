package no.clueless.opencargo.catalog.domain.service;

import no.clueless.opencargo.bindings.ProductListType;
import no.clueless.opencargo.catalog.port.in.CountProductsUseCase;
import no.clueless.opencargo.catalog.port.in.ListProductsUseCase;
import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.domain.model.Products;
import no.clueless.opencargo.infrastructure.marshalling.XmlMarshaller;

public class ProductService implements CountProductsUseCase, ListProductsUseCase {
    private ProductListType productList;

    private ProductListType getProductList() {
        if (productList == null) {
            productList = XmlMarshaller.unmarshalResourceSilently("products.xml", ProductListType.class);
        }
        return productList;
    }

    @Override
    public int countProducts() {
        var productListDTO = getProductList();
        return productListDTO == null || productListDTO.getProduct() == null ? 0 : productListDTO.getProduct().size();
    }

    @Override
    public Products listProducts() {
        var productListDTO = getProductList();
        return productListDTO == null || productListDTO.getProduct() == null ? null : productListDTO.getProduct()
                .stream()
                .map(productDTO -> new Product(productDTO.getId().intValue(), productDTO.getConsignorId(), productDTO.getNumber(), productDTO.getName()))
                .collect(Products.collector());
    }

    private static final class SingletonHolder {
        private static final ProductService INSTANCE = new ProductService();
    }

    public static ProductService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
