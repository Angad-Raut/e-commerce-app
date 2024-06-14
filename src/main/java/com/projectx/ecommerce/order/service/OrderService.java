package com.projectx.ecommerce.order.service;

import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.order.entity.OrderDetails;
import com.projectx.ecommerce.order.payloads.CustomersOrdersDto;
import com.projectx.ecommerce.order.payloads.OrderDto;
import com.projectx.ecommerce.order.payloads.ViewOrderPaymentDto;
import com.projectx.ecommerce.order.payloads.ViewOrdersDto;

import java.util.List;

public interface OrderService {
    String placeOrder(OrderDto dto)throws ResourceNotFoundException, InvalidDataException;
    OrderDetails getByOrderId(EntityIdDto dto)throws ResourceNotFoundException;
    Boolean updateOrderStatus(EntityIdDto dto)throws ResourceNotFoundException;
    List<ViewOrdersDto> getAllInCompletedOrders();
    List<ViewOrdersDto> getAllCompletedOrders();
    List<CustomersOrdersDto> getCustomerOrderHistory(EntityIdDto dto);
    Long getPaymentIdByOrderId(EntityIdDto dto);

    List<ViewOrderPaymentDto> getUnpaidOrders();
    List<ViewOrderPaymentDto> getPaidOrders();
}
