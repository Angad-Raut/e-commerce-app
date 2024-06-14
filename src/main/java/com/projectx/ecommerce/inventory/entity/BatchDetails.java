package com.projectx.ecommerce.inventory.entity;

import com.projectx.ecommerce.product.entity.ProductCategory;
import com.projectx.ecommerce.product.entity.ProductDetails;
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
@Table(name = "batch_details")
public class BatchDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;
    private String batchName;
    private Integer batchQty;
    private Integer remainingQty;
    @OneToOne
    private ProductCategory productCategory;
    @OneToOne
    private ProductDetails productDetails;
    private Boolean batchStatus;
    private Date insertedTime;
    private Date updatedTime;
}
