package com.projectx.ecommerce.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double payableAmount;
    private Integer paymentType;
    private String bankName;
    private String ifsCode;
    private String accountNumber;
    private String accountHolderName;
    private String cashGivenBy;
    private Date cashCollectedDate;
    private String chequeNumber;
    private String remark;
    private Date paymentTime;
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentDetails paymentDetails;
}
