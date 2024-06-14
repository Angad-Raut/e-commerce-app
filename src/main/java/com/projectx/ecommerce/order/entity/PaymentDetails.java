package com.projectx.ecommerce.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment_details")
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Double orderAmount;
    private Double paymentAmount;
    private Integer paymentStatus;
    private Date paymentDate;
    @OneToMany(mappedBy = "paymentDetails",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<PaymentHistory> paymentHistories;
}
