package com.projectx.ecommerce.setting.entity;

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
@Table(name = "discount_details")
public class DiscountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer discountType;
    private Double discount;
    private Boolean status;
    private Date insertedTime;
    private Date updatedTime;
}
