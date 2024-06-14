package com.projectx.ecommerce.order.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndTypeDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.entity.PaymentHistory;
import com.projectx.ecommerce.order.payloads.PaymentRequestDto;
import com.projectx.ecommerce.order.repository.PaymentHistoryRepository;
import com.projectx.ecommerce.order.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private OrderService orderService;

    @Transactional
    @Override
    public Boolean doPayment(PaymentRequestDto dto) throws InvalidDataException,AlreadyExistException {
        PaymentHistory paymentHistory = null;
        Boolean result = false;
        PaymentDetails paymentDetails = paymentRepository.findByPaymentId(dto.getPaymentId());
        if (paymentDetails.getPaymentAmount().equals(paymentDetails.getOrderAmount())) {
            throw new AlreadyExistException(MessageUtils.PAYMENT_ALREADY_DONE);
        }
        if (dto.getPaymentType().equals(MessageUtils.NFT_TYPE)) {
            paymentHistory = PaymentHistory.builder()
                    .paymentDetails(paymentDetails)
                    .accountHolderName(dto.getAccountHolderName())
                    .accountNumber(dto.getAccountNumber())
                    .bankName(dto.getBankName())
                    .ifsCode(dto.getIfsCode())
                    .payableAmount(dto.getPayableAmount())
                    .paymentTime(new Date())
                    .paymentType(dto.getPaymentType())
                    .remark(dto.getRemark())
                    .build();
        } else if (dto.getPaymentType().equals(MessageUtils.CHEQUE_TYPE)) {
            paymentHistory = PaymentHistory.builder()
                    .paymentDetails(paymentDetails)
                    .chequeNumber(dto.getChequeNumber())
                    .payableAmount(dto.getPayableAmount())
                    .remark(dto.getRemark())
                    .paymentType(dto.getPaymentType())
                    .paymentTime(new Date())
                    .build();
        } else if (dto.getPaymentType().equals(MessageUtils.CASH_TYPE)) {
            paymentHistory = PaymentHistory.builder()
                    .paymentDetails(paymentDetails)
                    .paymentTime(new Date())
                    .paymentType(dto.getPaymentType())
                    .payableAmount(dto.getPayableAmount())
                    .remark(dto.getRemark())
                    .cashGivenBy(dto.getCashGivenBy())
                    .cashCollectedDate(new Date())
                    .build();
        }
        if (paymentHistory!=null) {
            paymentHistoryRepository.save(paymentHistory);
            Double amount = paymentDetails.getPaymentAmount();
            Double calculatedAmount = (amount+dto.getPayableAmount());
            paymentDetails.setPaymentAmount(calculatedAmount);
            if (paymentDetails.getPaymentAmount().equals(paymentDetails.getOrderAmount())) {
                paymentDetails.setPaymentStatus(MessageUtils.FULL_PAID);
            }
            paymentRepository.save(paymentDetails);
            result = true;
        }
        return result;
    }

    @Override
    public PaymentDetails getPaymentDetailsByPaymentId(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            PaymentDetails details = paymentRepository.findByPaymentId(dto.getEntityId());
            if (details==null) {
                throw new ResourceNotFoundException(MessageUtils.PAYMENT_DETAILS_NOT_EXIST);
            }
            return details;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public PaymentDetails getPaymentDetailsByOrderId(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            Long paymentId = orderService.getPaymentIdByOrderId(dto);
            PaymentDetails details = paymentRepository.findByPaymentId(paymentId);
            if (details==null) {
                throw new ResourceNotFoundException(MessageUtils.PAYMENT_DETAILS_NOT_EXIST);
            }
            return details;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Boolean updatePaymentStatus(EntityIdAndTypeDto dto) throws ResourceNotFoundException {
        try {
            PaymentDetails details = paymentRepository.findByPaymentId(dto.getEntityId());
            if (details==null) {
                throw new ResourceNotFoundException(MessageUtils.PAYMENT_DETAILS_NOT_EXIST);
            }
            Integer count = paymentRepository.updatePaymentStatus(dto.getEntityId(),dto.getEntityType());
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
    public List<PaymentHistory> getPaymentHistoryByPaymentId(EntityIdDto dto) throws InvalidDataException {
        try {
            List<PaymentHistory> fetchList = paymentHistoryRepository.getPaymentHistoryByPaymentId(dto.getEntityId());
            return !fetchList.isEmpty()?fetchList:new ArrayList<>();
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public List<PaymentHistory> getPaymentHistoryByOrderId(EntityIdDto dto) throws InvalidDataException {
        try {
            Long paymentId = orderService.getPaymentIdByOrderId(dto);
            List<PaymentHistory> fetchList = paymentHistoryRepository.getPaymentHistoryByPaymentId(paymentId);
            return !fetchList.isEmpty()?fetchList:new ArrayList<>();
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }
}
