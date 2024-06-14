package com.projectx.ecommerce.order.controller;

import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.ResponseDto;
import com.projectx.ecommerce.order.entity.OrderDetails;
import com.projectx.ecommerce.order.payloads.CustomersOrdersDto;
import com.projectx.ecommerce.order.payloads.OrderDto;
import com.projectx.ecommerce.order.payloads.ViewOrderPaymentDto;
import com.projectx.ecommerce.order.payloads.ViewOrdersDto;
import com.projectx.ecommerce.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.List;

@RestController
@RequestMapping(value = "/order/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/placeOrder")
    public ResponseEntity<ResponseDto<String>> placeOrder(@RequestBody OrderDto dto) {
        try {
            String data = orderService.placeOrder(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.CREATED);
        } catch (ResourceNotFoundException | InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getOrderDetailsById")
    public ResponseEntity<ResponseDto<OrderDetails>> getOrderDetailsById(@RequestBody EntityIdDto dto) {
        try {
            OrderDetails data = orderService.getByOrderId(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateOrderStatusByOrderId")
    public ResponseEntity<ResponseDto<Boolean>> updateOrderStatusByOrderId(@RequestBody EntityIdDto dto) {
        try {
            Boolean data = orderService.updateOrderStatus(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getCustomerOrderHistoryByCustomerId")
    public ResponseEntity<ResponseDto<List<CustomersOrdersDto>>> getCustomerOrderHistoryByCustomerId(@RequestBody EntityIdDto dto) {
        try {
            List<CustomersOrdersDto> data = orderService.getCustomerOrderHistory(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllInCompletedOrders")
    public ResponseEntity<ResponseDto<List<ViewOrdersDto>>> getAllInCompletedOrders() {
        try {
            List<ViewOrdersDto> data = orderService.getAllInCompletedOrders();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllCompletedOrders")
    public ResponseEntity<ResponseDto<List<ViewOrdersDto>>> getAllCompletedOrders() {
        try {
            List<ViewOrdersDto> data = orderService.getAllCompletedOrders();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getUnpaidOrders")
    public ResponseEntity<ResponseDto<List<ViewOrderPaymentDto>>> getUnpaidOrders() {
        try {
            List<ViewOrderPaymentDto> data = orderService.getUnpaidOrders();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getPaidOrders")
    public ResponseEntity<ResponseDto<List<ViewOrderPaymentDto>>> getPaidOrders() {
        try {
            List<ViewOrderPaymentDto> data = orderService.getPaidOrders();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()),HttpStatus.OK);
        }
    }
}
