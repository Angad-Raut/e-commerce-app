package com.projectx.ecommerce.order.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDto {
    private Long paymentId;
    private Double payableAmount;
    private Integer paymentType;
    private String bankName;
    private String ifsCode;
    private String accountNumber;
    private String accountHolderName;
    private String cashGivenBy;
    private String chequeNumber;
    private String remark;
}
