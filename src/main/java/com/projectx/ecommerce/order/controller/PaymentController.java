package com.projectx.ecommerce.order.controller;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndTypeDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.ResponseDto;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.entity.PaymentHistory;
import com.projectx.ecommerce.order.payloads.PaymentRequestDto;
import com.projectx.ecommerce.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/payment/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/doPayment")
    public ResponseEntity<ResponseDto<Boolean>> doPayment(@RequestBody PaymentRequestDto dto) {
        try {
             Boolean data = paymentService.doPayment(dto);
             return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (InvalidDataException | AlreadyExistException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getPaymentDetailsByPaymentId")
    public ResponseEntity<ResponseDto<PaymentDetails>> getPaymentDetailsByPaymentId(@RequestBody EntityIdDto dto) {
        try {
            PaymentDetails data = paymentService.getPaymentDetailsByPaymentId(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getPaymentDetailsByOrderId")
    public ResponseEntity<ResponseDto<PaymentDetails>> getPaymentDetailsByOrderId(@RequestBody EntityIdDto dto) {
        try {
            PaymentDetails data = paymentService.getPaymentDetailsByOrderId(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updatePaymentStatus")
    public ResponseEntity<ResponseDto<Boolean>> updatePaymentStatus(@RequestBody EntityIdAndTypeDto dto) {
        try {
            Boolean data = paymentService.updatePaymentStatus(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getPaymentHistoryByPaymentId")
    public ResponseEntity<ResponseDto<List<PaymentHistory>>> getPaymentHistoryByPaymentId(@RequestBody EntityIdDto dto) {
        try {
            List<PaymentHistory> data = paymentService.getPaymentHistoryByPaymentId(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getPaymentHistoryByOrderId")
    public ResponseEntity<ResponseDto<List<PaymentHistory>>> getPaymentHistoryByOrderId(@RequestBody EntityIdDto dto) {
        try {
            List<PaymentHistory> data = paymentService.getPaymentHistoryByOrderId(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }
}
