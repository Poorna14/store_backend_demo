package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.Map;

public class PriceDTO {

    private ProductDTO productDTO;
    private Map<Integer, BigDecimal> priceMap;

    public PriceDTO(ProductDTO productDTO, Map priceMap) {
        this.productDTO = productDTO;
        this.priceMap = priceMap;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public Map<Integer, BigDecimal> getPriceMap() {
        return priceMap;
    }

    @Override
    public String toString() {
        return "{" +
                "productDTO = " + productDTO +
                ", priceMap = " + priceMap +
                '}';
    }
}
