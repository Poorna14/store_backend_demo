package com.example.demo.service.impl;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PriceDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.ProductNotfoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<ProductDTO> getAllAvailableProducts() {
        logger.debug("inside getAllAvailableProducts");

        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            productDTOList.add(new ProductDTO(product.getId(), product.getName(), product.getCode()));
        }

        return productDTOList;
    }

    @Override
    public PriceDTO getProductPrice(String code) {
        logger.debug("inside getProductPrice");
        Product product = productRepository.findOneByCode(code);

        if (product == null) {
            throw new ProductNotfoundException("product was not found for the order, product code : " + code);
        }

        Map<Integer, BigDecimal> priceMap = new LinkedHashMap();
        for (int i = 1; i <= 50; i++) {
            priceMap.put(i, calculatePrice(product, 0, i));
        }

        ProductDTO productDTO = new ProductDTO(product.getId(), product.getName(), product.getCode());

        return new PriceDTO(productDTO, priceMap);
    }

    @Override
    public BigDecimal getOrderPrice(List<OrderDTO> orderDTOList) {
        logger.debug("inside getOrderPrice");
        BigDecimal totalPrice = new BigDecimal(0);
        Product product;

        for (OrderDTO orderDTO : orderDTOList) {
            product = productRepository.findOneByCode(orderDTO.getCode());

            if (product == null) {
                throw new ProductNotfoundException("product was not found for the order, product code : " + orderDTO.getCode());
            }

            totalPrice = totalPrice.add(calculatePrice(product, orderDTO.getNumOfCartons(), orderDTO.getNumOfUnits()));
        }

        logger.debug("total calculated price : {}", totalPrice);
        return totalPrice;
    }

    private BigDecimal calculatePrice(Product product, int cartons, int units) {
        logger.debug("inside calculatePrice");

        Integer unitsPerCarton = product.getUnitsPerCarton();

        Integer totalNumOfCartons = cartons;
        Integer effectiveOrderedUnits = units;
        if (units >= unitsPerCarton) {
            totalNumOfCartons += (units / unitsPerCarton);
            effectiveOrderedUnits = units % unitsPerCarton;
        }

        BigDecimal totalPriceForCartons = new BigDecimal(totalNumOfCartons).multiply(product.getCartonPrice());
        if (totalNumOfCartons >= Constants.DISCOUNT_REQUIREMENT) {
            logger.info("applying discount for the product : {}", product.getName());
            totalPriceForCartons = applyDiscount(totalNumOfCartons, product.getCartonPrice());
        }

        BigDecimal unitPrice = (product.getCartonPrice().divide(new BigDecimal(product.getUnitsPerCarton()))).multiply(product.getPriceMultiplier());
        BigDecimal totalPriceForSeparateUnits = new BigDecimal(effectiveOrderedUnits).multiply(unitPrice);

        BigDecimal totalPrice = totalPriceForCartons.add(totalPriceForSeparateUnits);

        logger.debug("calculated price : {}", totalPrice);

        MathContext m = new MathContext(5);
        return totalPrice.round(m);
    }

    private BigDecimal applyDiscount(int numOfCartons, BigDecimal cartonPrice) {
        BigDecimal priceAfterDiscount = (cartonPrice.multiply(new BigDecimal(numOfCartons))).multiply(new BigDecimal(1).subtract(Constants.DISCOUNT));
        return priceAfterDiscount;
    }
}
