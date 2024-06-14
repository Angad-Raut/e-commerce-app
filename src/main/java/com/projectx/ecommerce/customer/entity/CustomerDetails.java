package com.projectx.ecommerce.customer.entity;

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
@Table(name = "customer_details")
public class CustomerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private Long customerMobile;
    private String customerAddress;
    private Boolean customerStatus;
    private String customerPassword;
    private Boolean isAdmin;
    private Date insertedTime;
    private Date updatedTime;
}
