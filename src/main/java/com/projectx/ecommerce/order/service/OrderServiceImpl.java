package com.projectx.ecommerce.order.service;

import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.customer.entity.CustomerDetails;
import com.projectx.ecommerce.customer.repository.CustomerRepository;
import com.projectx.ecommerce.inventory.service.InventoryStockService;
import com.projectx.ecommerce.order.entity.OrderDetails;
import com.projectx.ecommerce.order.entity.OrderItem;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.entity.PaymentHistory;
import com.projectx.ecommerce.order.payloads.*;
import com.projectx.ecommerce.order.repository.OrderRepository;
import com.projectx.ecommerce.order.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InventoryStockService inventoryStockService;

    @Transactional(rollbackFor = InvalidDataException.class)
    @Override
    public String placeOrder(OrderDto dto) throws ResourceNotFoundException, InvalidDataException {
        Set<OrderItem> orderItemSet = new HashSet<>();
        List<EntityIdDto> idDtoList = new ArrayList<>();
        Double orderTotalAmount = 0.0;
        for(ItemsDto itemsDto: dto.getItemsDto()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(itemsDto.getItemId());
            orderItem.setPrice(itemsDto.getPrice());
            orderItem.setQuantity(itemsDto.getQuantity());
            orderItem.setTotalPrice(itemsDto.getPrice()*itemsDto.getPrice());
            orderTotalAmount = (orderTotalAmount+orderItem.getTotalPrice());
            orderItemSet.add(orderItem);
            idDtoList.add(new EntityIdDto(itemsDto.getItemId()));
        }
        try {
            CustomerDetails customerDetails = customerRepository.findByCustomerId(dto.getCustomerId());
            OrderDetails orderDetails = OrderDetails.builder()
                    .orderDate(new Date())
                    .orderStatus(true)
                    .orderAmount(orderTotalAmount)
                    .orderItems(orderItemSet)
                    .orderNumber(setOrderNumber())
                    .customerDetails(customerDetails)
                    .paymentDetails(setPayment(orderTotalAmount))
                    .build();
            orderRepository.save(orderDetails);
            inventoryStockService.updateAllStocksStatus(idDtoList);
            return MessageUtils.PLACE_ORDER_MSG+orderDetails.getOrderNumber();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public OrderDetails getByOrderId(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            OrderDetails orderDetails = orderRepository.findByOrderId(dto.getEntityId());
            if (orderDetails==null) {
                throw new ResourceNotFoundException(MessageUtils.ORDER_NOT_EXIST);
            }
            return orderDetails;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Boolean updateOrderStatus(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            OrderDetails orderDetails = orderRepository.findByOrderId(dto.getEntityId());
            if (orderDetails==null) {
                throw new ResourceNotFoundException(MessageUtils.ORDER_NOT_EXIST);
            }
            Integer count = orderRepository.updateOrderStatus(dto.getEntityId(),false);
            if (count==1) {
                return true;
            } else {
                return false;
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ViewOrdersDto> getAllInCompletedOrders() {
        try {
            List<OrderDetails> fetchList = orderRepository.getAllOrdersByStatus(true);
            AtomicInteger index = new AtomicInteger(0);
            return !fetchList.isEmpty()?fetchList.stream()
                    .map(data -> {
                        return ViewOrdersDto.builder()
                                .srNo(index.incrementAndGet())
                                .orderId(data.getOrderId())
                                .orderNumber(data.getOrderNumber())
                                .orderDate(MessageUtils.setDate(data.getOrderDate()))
                                .orderAmount(data.getOrderAmount())
                                .orderStatus(data.getOrderStatus()?"Incomplete":"Completed")
                                .customerId(data.getCustomerDetails().getCustomerId())
                                .customerName(data.getCustomerDetails().getCustomerName())
                                .build();
                    }).collect(Collectors.toList()):new ArrayList<>();
        } catch (Exception e){
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public List<ViewOrdersDto> getAllCompletedOrders() {
        try {
            List<OrderDetails> fetchList = orderRepository.getAllOrdersByStatus(false);
            AtomicInteger index = new AtomicInteger(0);
            return !fetchList.isEmpty()?fetchList.stream()
                    .map(data -> {
                        return ViewOrdersDto.builder()
                                .srNo(index.incrementAndGet())
                                .orderId(data.getOrderId())
                                .orderNumber(data.getOrderNumber())
                                .orderDate(MessageUtils.setDate(data.getOrderDate()))
                                .orderAmount(data.getOrderAmount())
                                .orderStatus(data.getOrderStatus()?"Incomplete":"Completed")
                                .customerId(data.getCustomerDetails().getCustomerId())
                                .customerName(data.getCustomerDetails().getCustomerName())
                                .build();
                    }).collect(Collectors.toList()):new ArrayList<>();
        } catch (Exception e){
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public List<CustomersOrdersDto> getCustomerOrderHistory(EntityIdDto dto) {
        try {
            List<OrderDetails> fetchList = orderRepository.getCustomerOrderHistory(dto.getEntityId());
            AtomicInteger index = new AtomicInteger(0);
            return !fetchList.isEmpty()?fetchList.stream()
                    .map(data -> {
                        return CustomersOrdersDto.builder()
                                .srNo(index.incrementAndGet())
                                .orderId(data.getOrderId())
                                .orderNumber(data.getOrderNumber())
                                .orderDate(MessageUtils.setDate(data.getOrderDate()))
                                .orderAmount(data.getOrderAmount())
                                .orderStatus(data.getOrderStatus()?"Incomplete":"Completed")
                                .build();
                    }).collect(Collectors.toList()):new ArrayList<>();
        } catch (Exception e){
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public Long getPaymentIdByOrderId(EntityIdDto dto) {
        return orderRepository.getPaymentIdByOrderId(dto.getEntityId());
    }

    @Override
    public List<ViewOrderPaymentDto> getUnpaidOrders() {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(MessageUtils.UNPAID_STATUS);
        statusList.add(MessageUtils.PARTIALLY_PAID);
        List<Object[]> fetchList = orderRepository.getOrdersByPaymentStatus(statusList);
        AtomicInteger index = new AtomicInteger(0);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    Date orderDate = data[2]!=null && data[2] instanceof Date?(Date) data[1]:null;
                    return ViewOrderPaymentDto.builder()
                            .srNo(index.incrementAndGet())
                            .orderId(data[0]!=null?Long.parseLong(data[0].toString()):null)
                            .orderNumber(data[1]!=null?data[1].toString():"-")
                            .orderDate(orderDate!=null?MessageUtils.setDate(orderDate):"-")
                            .orderAmount(data[3]!=null?Double.parseDouble(data[3].toString()):0.0)
                            .paymentId(data[4]!=null?Long.parseLong(data[4].toString()):null)
                            .paymentAmount(data[5]!=null?Double.parseDouble(data[5].toString()):0.0)
                            .customerId(data[6]!=null?Long.parseLong(data[6].toString()):null)
                            .customerName(data[7]!=null?data[7].toString():"-")
                            .build();
                }).collect(Collectors.toList()) :new ArrayList<>();
    }

    @Override
    public List<ViewOrderPaymentDto> getPaidOrders() {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(MessageUtils.FULL_PAID);
        List<Object[]> fetchList = orderRepository.getOrdersByPaymentStatus(statusList);
        AtomicInteger index = new AtomicInteger(0);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    Date orderDate = data[2]!=null && data[2] instanceof Date?(Date) data[1]:null;
                    return ViewOrderPaymentDto.builder()
                            .srNo(index.incrementAndGet())
                            .orderId(data[0]!=null?Long.parseLong(data[0].toString()):null)
                            .orderNumber(data[1]!=null?data[1].toString():"-")
                            .orderDate(orderDate!=null?MessageUtils.setDate(orderDate):"-")
                            .orderAmount(data[3]!=null?Double.parseDouble(data[3].toString()):0.0)
                            .paymentId(data[4]!=null?Long.parseLong(data[4].toString()):null)
                            .paymentAmount(data[5]!=null?Double.parseDouble(data[5].toString()):0.0)
                            .customerId(data[6]!=null?Long.parseLong(data[6].toString()):null)
                            .customerName(data[7]!=null?data[7].toString():"-")
                            .build();
                }).collect(Collectors.toList()) :new ArrayList<>();
    }

    private String setOrderNumber() {
         String orderNo = orderRepository.getLastOrderNumber();
         if (orderNo==null) {
             return MessageUtils.ORDER_NUMBER;
         } else {
             Long value = Long.parseLong(orderNo)+1;
             return String.valueOf(value);
         }
    }
    private PaymentDetails setPayment(Double orderAmount) {
        try {
            Set<PaymentHistory> paymentHistories = new HashSet<>();
            PaymentDetails paymentDetails = PaymentDetails.builder()
                    .orderAmount(orderAmount)
                    .paymentAmount(0.0)
                    .paymentDate(new Date())
                    .paymentStatus(MessageUtils.UNPAID_STATUS)
                    .paymentHistories(paymentHistories)
                    .build();
            return paymentRepository.save(paymentDetails);
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }
}
