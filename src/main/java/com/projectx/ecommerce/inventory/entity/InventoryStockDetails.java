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
@Table(name = "inventory_stock_details")
public class InventoryStockDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    private String serialNumber;
    @OneToOne
    private ProductCategory productCategory;
    @OneToOne
    private ProductDetails productDetails;
    @OneToOne
    private BatchDetails batchDetails;
    private Boolean status;
    private Date insertedTime;
    private Date updatedTime;
}
