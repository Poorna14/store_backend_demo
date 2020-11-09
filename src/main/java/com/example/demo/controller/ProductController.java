package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PriceDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class ProductController {

    @Autowired
    ProductService productService;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> getProducts(HttpServletRequest request) {
        logger.debug("getProducts endpoint called");
        List<ProductDTO> result = productService.getAllAvailableProducts();
        logger.debug("response for the getProducts endpoint : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/products/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceDTO> getProductPrice(@PathVariable("code") String code, HttpServletRequest request) {
        logger.debug("getProductPrice endpoint called");
        PriceDTO result = productService.getProductPrice(code);
        logger.debug("response for the getProductPrice endpoint : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BigDecimal> getOrderPrice(@RequestBody List<OrderDTO> orderDTOList, HttpServletRequest request) {
        logger.debug("getPrice getOrderPrice called");
        BigDecimal result = productService.getOrderPrice(orderDTOList);
        logger.debug("response for the getOrderPrice endpoint : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
