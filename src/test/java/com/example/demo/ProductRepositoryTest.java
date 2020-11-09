package com.example.demo;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    private static Product testProduct1;
    private static Product testProduct2;

    private static String testProductCode1;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeClass
    public static void setupBeforeClass() {
        testProduct1 = new Product(null, "apple", "apple", new BigDecimal(10), 5, new BigDecimal(1.3));
        testProduct2 = new Product(null, "orange", "orange", new BigDecimal(20), 10, new BigDecimal(1.3));

        testProductCode1 = "apple";
    }

    @Test
    public void getAllProducts_ReturnsAllProducts() {
        Product savedProduct1 = entityManager.persistFlushFind(testProduct1);
        Product savedProduct2 = entityManager.persistFlushFind(testProduct2);

        List<Product> productList = productRepository.findAll();

        Assert.assertEquals(productList.get(2).getName(), savedProduct1.getName());
        Assert.assertEquals(productList.get(3).getName(), savedProduct2.getName());
    }

    @Test
    public void getProduct_ReturnProduct() {
        entityManager.merge(testProduct1);

        Product product = productRepository.findOneByCode(testProductCode1);

        Assert.assertEquals(product.getCode(), testProductCode1);
    }

}
