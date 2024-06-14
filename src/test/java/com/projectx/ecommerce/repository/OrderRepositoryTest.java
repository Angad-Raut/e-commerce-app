package com.projectx.ecommerce.repository;

import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.customer.entity.CustomerDetails;
import com.projectx.ecommerce.customer.repository.CustomerRepository;
import com.projectx.ecommerce.inventory.entity.BatchDetails;
import com.projectx.ecommerce.inventory.entity.InventoryStockDetails;
import com.projectx.ecommerce.inventory.repository.BatchDetailsRepository;
import com.projectx.ecommerce.inventory.repository.InventoryStockRepository;
import com.projectx.ecommerce.order.entity.OrderDetails;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.repository.OrderRepository;
import com.projectx.ecommerce.order.repository.PaymentHistoryRepository;
import com.projectx.ecommerce.order.repository.PaymentRepository;
import com.projectx.ecommerce.product.entity.ProductCategory;
import com.projectx.ecommerce.product.entity.ProductDetails;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
import com.projectx.ecommerce.product.repository.ProductRepository;
import com.projectx.ecommerce.utils.CommonRepoUtils;
import com.projectx.ecommerce.utils.OrderTestRequest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    private InventoryStockRepository inventoryStockRepository;
    @Autowired
    private BatchDetailsRepository batchDetailsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void clearData() {
        paymentHistoryRepository.deleteAll();
        paymentRepository.deleteAll();
        orderRepository.deleteAll();
        inventoryStockRepository.deleteAll();
        batchDetailsRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
        customerRepository.deleteAll();
    }

    public OrderDetails placeOrder() {
        CustomerDetails customerDetails = customerRepository.save(CommonRepoUtils.toCustomer());
        ProductCategory category = productCategoryRepository.save(CommonRepoUtils.toCategory());
        ProductDetails productDetails = productRepository.save(CommonRepoUtils.toProduct(category));
        productRepository.updateProductStatus(productDetails.getProductId(),MessageUtils.VERIFY_STATUS);
        ProductDetails productDetails1 = productRepository.findByProductId(productDetails.getProductId());
        BatchDetails batchDetails = batchDetailsRepository.save(CommonRepoUtils.toBatch(category,productDetails1));
        InventoryStockDetails stockDetails = inventoryStockRepository.save(CommonRepoUtils.toStockOne(category,productDetails,batchDetails));
        PaymentDetails paymentDetails = paymentRepository.save(CommonRepoUtils.toPayment());
        OrderTestRequest orderTestRequest = OrderTestRequest.builder()
                .customerDetails(customerDetails)
                .paymentDetails(paymentDetails)
                .productId(stockDetails.getProductDetails().getProductId())
                .price(stockDetails.getProductDetails().getProductPrice())
                .quantity(2)
                .build();
        return orderRepository.save(CommonRepoUtils.toOrder(orderTestRequest));
    }

    @Test
    public void findByOrderIdTest() {
        OrderDetails orderDetails = placeOrder();
        OrderDetails details = orderRepository.findByOrderId(orderDetails.getOrderId());
        assertEquals(details.getOrderId(),orderDetails.getOrderId());
        assertEquals(details.getOrderNumber(),orderDetails.getOrderNumber());
        assertEquals(details.getOrderAmount(),orderDetails.getOrderAmount());
        assertEquals(details.getOrderStatus(),orderDetails.getOrderStatus());
    }

    @Test
    public void getLastOrderNumberTest() {
        OrderDetails orderDetails = placeOrder();
        String orderNumber = orderRepository.getLastOrderNumber();
        assertEquals(orderNumber,MessageUtils.ORDER_NUMBER);
    }

    @Test
    public void updateOrderStatusTest() {
        OrderDetails orderDetails = placeOrder();
        Integer result = orderRepository.updateOrderStatus(orderDetails.getOrderId(),false);
        assertEquals(result,1);
    }

    @Test
    public void getAllOrdersByStatusTest() {
        OrderDetails orderDetails = placeOrder();
        List<OrderDetails> fetchList = orderRepository.getAllOrdersByStatus(true);
        assertEquals(fetchList.get(0).getOrderId(),orderDetails.getOrderId());
        assertEquals(fetchList.get(0).getOrderNumber(),orderDetails.getOrderNumber());
        assertEquals(fetchList.get(0).getOrderAmount(),orderDetails.getOrderAmount());
        assertEquals(fetchList.get(0).getOrderStatus(),orderDetails.getOrderStatus());
    }

    @Test
    public void getCustomerOrderHistoryTest() {
        OrderDetails orderDetails = placeOrder();
        CustomerDetails customerDetails = orderDetails.getCustomerDetails();
        List<OrderDetails> fetchList = orderRepository.getCustomerOrderHistory(customerDetails.getCustomerId());
        assertEquals(fetchList.get(0).getOrderId(),orderDetails.getOrderId());
        assertEquals(fetchList.get(0).getOrderNumber(),orderDetails.getOrderNumber());
        assertEquals(fetchList.get(0).getOrderAmount(),orderDetails.getOrderAmount());
        assertEquals(fetchList.get(0).getOrderStatus(),orderDetails.getOrderStatus());
    }

    @Test
    public void getPaymentIdByOrderIdTest() {
        OrderDetails orderDetails = placeOrder();
        Long paymentId = orderRepository.getPaymentIdByOrderId(orderDetails.getOrderId());
        assertEquals(paymentId,orderDetails.getPaymentDetails().getPaymentId());
    }
}
