package com.example.demo;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PriceDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.ProductNotfoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private static Product testProduct1;
    private static Product testProduct2;
    private static List<Product> testProductArr1;
    private static List<Product> testProductArr2;

    private static OrderDTO testOrderDTO1;
    private static OrderDTO testOrderDTO2;
    private static OrderDTO testOrderDTO3;

    private static List<OrderDTO> testOrderDTOArr1;
    private static List<OrderDTO> testOrderDTOArr2;
    @InjectMocks
    private static ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @BeforeClass
    public static void setupBeforeClass() {
        productService = new ProductServiceImpl();

        testProduct1 = new Product(1L, "apple", "apple", new BigDecimal(10), 5, new BigDecimal(1.3));
        testProduct2 = new Product(2L, "orange", "orange", new BigDecimal(20), 10, new BigDecimal(1.3));

        testProductArr1 = new ArrayList<>();
        testProductArr1.add(testProduct1);
        testProductArr1.add(testProduct2);

        testProductArr2 = new ArrayList<>();

        testOrderDTO1 = new OrderDTO("apple", 1, 1);
        testOrderDTO2 = new OrderDTO("orange", 1, 1);
        testOrderDTO3 = new OrderDTO("strawberry", 1, 1);

        testOrderDTOArr1 = new ArrayList<>();
        testOrderDTOArr1.add(testOrderDTO1);
        testOrderDTOArr1.add(testOrderDTO2);

        testOrderDTOArr2 = new ArrayList<>();
        testOrderDTOArr2.add(testOrderDTO3);
    }

    @Test
    public void getAllProducts_ReturnsAllProducts() {
        given(productRepository.findAll()).willReturn(testProductArr1);
        List<ProductDTO> productList = productService.getAllAvailableProducts();
        Assert.assertNotNull(productList);
        Assert.assertEquals(testProductArr1.size(), productList.size());
        Assert.assertEquals(testProduct1.getName(), productList.get(0).getName());
        Assert.assertEquals(testProduct2.getName(), productList.get(1).getName());
    }

    @Test
    public void getAllProducts_Empty() {
        given(productRepository.findAll()).willReturn(testProductArr2);
        List<ProductDTO> productList = productService.getAllAvailableProducts();
        Assert.assertNotNull(productList);
        Assert.assertEquals(testProductArr2.size(), productList.size());
    }

    @Test
    public void getProductPrice_ReturnsPrice() {
        given(productRepository.findOneByCode("apple")).willReturn(testProduct1);
        PriceDTO priceDTO = productService.getProductPrice("apple");
        Assert.assertNotNull(priceDTO);
        Assert.assertEquals(priceDTO.getProductDTO().getName(), "apple");

        MathContext m = new MathContext(2);

        Assert.assertEquals((priceDTO.getPriceMap().get(5)).round(m), new BigDecimal(10));
        Assert.assertEquals((priceDTO.getPriceMap().get(10)).round(m), new BigDecimal(20));
    }

    @Test(expected = ProductNotfoundException.class)
    public void getProductPrice_NotFound() {
        productService.getProductPrice("strawberry");
    }

    @Test
    public void getOrderPrice_getPrice() {
        given(productRepository.findOneByCode("apple")).willReturn(testProduct1);
        given(productRepository.findOneByCode("orange")).willReturn(testProduct2);
        BigDecimal totalPrice = productService.getOrderPrice(testOrderDTOArr1);
        Assert.assertNotNull(totalPrice);

        MathContext m = new MathContext(3);

        Assert.assertEquals(totalPrice.round(m), new BigDecimal("35.2"));
    }

    @Test(expected = ProductNotfoundException.class)
    public void getOrderPrice_NotFound() {
        productService.getOrderPrice(testOrderDTOArr2);
    }

}
