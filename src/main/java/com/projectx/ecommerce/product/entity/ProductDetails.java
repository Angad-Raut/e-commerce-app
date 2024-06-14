package com.projectx.ecommerce.product.entity;

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
@Table(name = "product_details")
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private Double productPrice;
    @Column(name = "product_description")
    private String productDescription;
    @Lob
    @Column(name = "product_image",length = 1000)
    private byte[] productImage;
    @OneToOne
    private ProductCategory productCategory;
    @Column(name = "product_status")
    private Integer productStatus;
    @Column(name = "inserted_time")
    private Date insertedTime;
    @Column(name = "updated_time")
    private Date updatedTime;
}
