package com.projectx.ecommerce.order.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndTypeDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.entity.PaymentHistory;
import com.projectx.ecommerce.order.payloads.PaymentRequestDto;

import java.util.List;

public interface PaymentService {

    Boolean doPayment(PaymentRequestDto dto)throws InvalidDataException,AlreadyExistException;
    PaymentDetails getPaymentDetailsByPaymentId(EntityIdDto dto)throws ResourceNotFoundException;
    PaymentDetails getPaymentDetailsByOrderId(EntityIdDto dto)throws ResourceNotFoundException;
    Boolean updatePaymentStatus(EntityIdAndTypeDto dto)throws ResourceNotFoundException;
    List<PaymentHistory> getPaymentHistoryByPaymentId(EntityIdDto dto)throws InvalidDataException;
    List<PaymentHistory> getPaymentHistoryByOrderId(EntityIdDto dto)throws InvalidDataException;

}
