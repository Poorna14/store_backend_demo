package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PriceDTO;
import com.example.demo.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllAvailableProducts();

    PriceDTO getProductPrice(String code);

    BigDecimal getOrderPrice(List<OrderDTO> orderDTOList);

}
