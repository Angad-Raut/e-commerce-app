package com.projectx.ecommerce.order.entity;

import com.projectx.ecommerce.customer.entity.CustomerDetails;
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
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderNumber;
    private Date orderDate;
    private Double orderAmount;
    private Integer discountType;
    private Double discountAmt;
    private Double discountedAmt;
    private Boolean orderStatus;
    @Embedded
    private Set<OrderItem> orderItems;
    @OneToOne
    private CustomerDetails customerDetails;
    @OneToOne
    private PaymentDetails paymentDetails;
}
