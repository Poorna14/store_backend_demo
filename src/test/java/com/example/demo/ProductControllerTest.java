package com.example.demo;

import com.example.demo.controller.ProductController;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PriceDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.ProductNotfoundException;
import com.example.demo.service.ProductService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    private static ProductDTO testProductDTO1;
    private static ProductDTO testProductDTO2;
    private static List<ProductDTO> testProductDTOArr1;
    private static PriceDTO testPriceDTO1;
    private static Map<Integer, BigDecimal> testPriceMap1;
    private static List<OrderDTO> testOrderDTOArr1;
    private static BigDecimal totalPrice1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeClass
    public static void setupBeforeClass() {
        testProductDTO1 = new ProductDTO(1L, "apple", "apple");
        testProductDTO2 = new ProductDTO(2L, "orange", "orange");

        testProductDTOArr1 = new ArrayList<>();
        testProductDTOArr1.add(testProductDTO1);
        testProductDTOArr1.add(testProductDTO2);

        testPriceMap1 = new LinkedHashMap<>();
        testPriceMap1.put(1, new BigDecimal(5));
        testPriceMap1.put(2, new BigDecimal(10));
        testPriceDTO1 = new PriceDTO(testProductDTO1, testPriceMap1);

        testOrderDTOArr1 = new ArrayList<>();
        totalPrice1 = new BigDecimal(30);
    }

    @Test
    public void getAllProducts_ReturnsAllProducts() throws Exception {
        given(productService.getAllAvailableProducts()).willReturn(testProductDTOArr1);
        mockMvc.perform(MockMvcRequestBuilders.get("/products")).andExpect(status().isOk()).andExpect(jsonPath("$.[0].id").value(1)).andExpect(jsonPath("$.[1].id").value(2));
    }

    @Test
    public void getAllProducts_NotFound() throws Exception {
        given(productService.getAllAvailableProducts()).willThrow(new ProductNotfoundException("not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/products")).andExpect(status().isNotFound());
    }

    @Test
    public void getProductPrice_ReturnsPrice() throws Exception {
        given(productService.getProductPrice(anyString())).willReturn(testPriceDTO1);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/apple")).andExpect(status().isOk()).andExpect(jsonPath("$.productDTO.name").value("apple"))
                .andExpect(jsonPath("$.priceMap.1").value(5)).andExpect(jsonPath("$.priceMap.2").value(10));
    }

    @Test
    public void getProductPrice_NotFound() throws Exception {
        given(productService.getProductPrice(anyString())).willThrow(new ProductNotfoundException("not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/products/orange")).andExpect(status().isNotFound());
    }

    @Test
    public void getOrderPrice_getPrice() throws Exception {
        given(productService.getOrderPrice(testOrderDTOArr1)).willReturn(totalPrice1);
        mockMvc.perform(MockMvcRequestBuilders.post("/order").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(testOrderDTOArr1))).andExpect(status().isOk()).andExpect(jsonPath("$").value(30));
    }

    @Test
    public void getOrderPrice_NotFound() throws Exception {
        given(productService.getOrderPrice(testOrderDTOArr1)).willThrow(new ProductNotfoundException("not found"));
        mockMvc.perform(MockMvcRequestBuilders.post("/order").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(testOrderDTOArr1))).andExpect(status().isNotFound());
    }

}
