package com.projectx.ecommerce.service;

import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.utils.OrderTestData;
import com.projectx.ecommerce.customer.repository.CustomerRepository;
import com.projectx.ecommerce.inventory.repository.InventoryStockRepository;
import com.projectx.ecommerce.inventory.service.InventoryStockService;
import com.projectx.ecommerce.order.entity.OrderDetails;
import com.projectx.ecommerce.order.payloads.CustomersOrdersDto;
import com.projectx.ecommerce.order.payloads.ViewOrdersDto;
import com.projectx.ecommerce.order.repository.OrderRepository;
import com.projectx.ecommerce.order.repository.PaymentRepository;
import com.projectx.ecommerce.order.service.OrderServiceImpl;
import com.projectx.ecommerce.utils.CustomerTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest extends OrderTestData {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private InventoryStockRepository inventoryStockRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private InventoryStockService inventoryStockService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void place_order_test() {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        List<EntityIdDto> entityIdDtoList = new ArrayList<>();
        entityIdDtoList.add(new EntityIdDto(1L));
        when(customerRepository.findByCustomerId(1L)).thenReturn(CustomerTestUtils.toCustomerEntity());
        when(orderRepository.save(setOrderDetails())).thenReturn(setOrderDetails());
        when(inventoryStockRepository.updateAllStatus(idList,false)).thenReturn(1);
        when(paymentRepository.save(setPaymentDetails())).thenReturn(setPaymentDetails());
        inventoryStockService.updateAllStocksStatus(entityIdDtoList);
        String message = orderService.placeOrder(placeOrder());
        assertEquals(message,MessageUtils.PLACE_ORDER_MSG+MessageUtils.ORDER_NUMBER);
    }

    @Test
    public void get_order_details_by_id_test() {
        when(orderRepository.findByOrderId(1L)).thenReturn(setOrderDetails());
        OrderDetails orderDetails = orderService.getByOrderId(new EntityIdDto(1L));
        assertEquals(orderDetails.getOrderId(),setOrderDetails().getOrderId());
        assertEquals(orderDetails.getOrderNumber(),setOrderDetails().getOrderNumber());
        assertEquals(orderDetails.getOrderAmount(),setOrderDetails().getOrderAmount());
    }

    @Test
    public void update_order_status_test() {
        when(orderRepository.findByOrderId(1L)).thenReturn(setOrderDetails());
        when(orderRepository.updateOrderStatus(1L,false)).thenReturn(1);
        Boolean status = orderService.updateOrderStatus(new EntityIdDto(1L));
        assertThat(status).isSameAs(true);
    }

    @Test
    public void get_all_in_completed_orders_test() {
        when(orderRepository.getAllOrdersByStatus(true)).thenReturn(toOrderList(false));
        List<ViewOrdersDto> fetchList = orderService.getAllInCompletedOrders();
        assertEquals(fetchList.get(0).getSrNo(),1);
        assertEquals(fetchList.get(0).getOrderId(),toOrderList(false).get(0).getOrderId());
        assertEquals(fetchList.get(0).getOrderNumber(),toOrderList(false).get(0).getOrderNumber());
        assertEquals(fetchList.get(0).getCustomerId(),toOrderList(false).get(0).getCustomerDetails().getCustomerId());
        assertEquals(fetchList.get(0).getCustomerName(),toOrderList(false).get(0).getCustomerDetails().getCustomerName());
        assertEquals(fetchList.get(0).getOrderAmount(),toOrderList(false).get(0).getOrderAmount());
        assertEquals(fetchList.get(0).getOrderStatus(),toOrderList(false).get(0).getOrderStatus()?"Incomplete":"Completed");
        assertEquals(fetchList.get(1).getSrNo(),2);
        assertEquals(fetchList.get(1).getOrderId(),toOrderList(false).get(1).getOrderId());
        assertEquals(fetchList.get(1).getOrderNumber(),toOrderList(false).get(1).getOrderNumber());
        assertEquals(fetchList.get(1).getCustomerId(),toOrderList(false).get(1).getCustomerDetails().getCustomerId());
        assertEquals(fetchList.get(1).getCustomerName(),toOrderList(false).get(1).getCustomerDetails().getCustomerName());
        assertEquals(fetchList.get(1).getOrderAmount(),toOrderList(false).get(1).getOrderAmount());
        assertEquals(fetchList.get(1).getOrderStatus(),toOrderList(false).get(1).getOrderStatus()?"Incomplete":"Completed");
    }

    @Test
    public void get_all_completed_orders_test() {
        when(orderRepository.getAllOrdersByStatus(false)).thenReturn(toOrderList(true));
        List<ViewOrdersDto> fetchList = orderService.getAllCompletedOrders();
        assertEquals(fetchList.get(0).getSrNo(),1);
        assertEquals(fetchList.get(0).getOrderId(),toOrderList(true).get(0).getOrderId());
        assertEquals(fetchList.get(0).getOrderNumber(),toOrderList(true).get(0).getOrderNumber());
        assertEquals(fetchList.get(0).getCustomerId(),toOrderList(true).get(0).getCustomerDetails().getCustomerId());
        assertEquals(fetchList.get(0).getCustomerName(),toOrderList(true).get(0).getCustomerDetails().getCustomerName());
        assertEquals(fetchList.get(0).getOrderAmount(),toOrderList(true).get(0).getOrderAmount());
        assertEquals(fetchList.get(0).getOrderStatus(),toOrderList(true).get(0).getOrderStatus()?"Incomplete":"Completed");
        assertEquals(fetchList.get(1).getSrNo(),2);
        assertEquals(fetchList.get(1).getOrderId(),toOrderList(true).get(1).getOrderId());
        assertEquals(fetchList.get(1).getOrderNumber(),toOrderList(true).get(1).getOrderNumber());
        assertEquals(fetchList.get(1).getCustomerId(),toOrderList(true).get(1).getCustomerDetails().getCustomerId());
        assertEquals(fetchList.get(1).getCustomerName(),toOrderList(true).get(1).getCustomerDetails().getCustomerName());
        assertEquals(fetchList.get(1).getOrderAmount(),toOrderList(true).get(1).getOrderAmount());
        assertEquals(fetchList.get(1).getOrderStatus(),toOrderList(true).get(1).getOrderStatus()?"Incomplete":"Completed");
    }

    @Test
    public void get_customer_order_history_test() {
        when(orderRepository.getCustomerOrderHistory(1L)).thenReturn(toCustomerOrderList());
        List<CustomersOrdersDto> fetchList = orderService.getCustomerOrderHistory(new EntityIdDto(1L));
        assertEquals(fetchList.get(0).getSrNo(),1);
        assertEquals(fetchList.get(0).getOrderId(),toCustomerOrderList().get(0).getOrderId());
        assertEquals(fetchList.get(0).getOrderNumber(),toCustomerOrderList().get(0).getOrderNumber());
        assertEquals(fetchList.get(0).getOrderAmount(),toCustomerOrderList().get(0).getOrderAmount());
        assertEquals(fetchList.get(0).getOrderStatus(),toCustomerOrderList().get(0).getOrderStatus()?"Incomplete":"Completed");
        assertEquals(fetchList.get(1).getSrNo(),2);
        assertEquals(fetchList.get(1).getOrderId(),toCustomerOrderList().get(1).getOrderId());
        assertEquals(fetchList.get(1).getOrderNumber(),toCustomerOrderList().get(1).getOrderNumber());
        assertEquals(fetchList.get(1).getOrderAmount(),toCustomerOrderList().get(1).getOrderAmount());
        assertEquals(fetchList.get(1).getOrderStatus(),toCustomerOrderList().get(1).getOrderStatus()?"Incomplete":"Completed");
    }

    @Test
    public void get_payment_id_by_order_id_test() {
        when(orderRepository.getPaymentIdByOrderId(1L)).thenReturn(1L);
        Long paymentId = orderService.getPaymentIdByOrderId(new EntityIdDto(1L));
        assertEquals(paymentId,1L);
    }
}
