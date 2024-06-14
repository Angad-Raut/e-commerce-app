package com.projectx.ecommerce.product.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.EntityTypeDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.product.entity.ProductCategory;
import com.projectx.ecommerce.product.entity.ProductDetails;
import com.projectx.ecommerce.product.payloads.EditProductDto;
import com.projectx.ecommerce.product.payloads.ProductDto;
import com.projectx.ecommerce.product.payloads.ViewProductDto;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
import com.projectx.ecommerce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public String insertUpdate(ProductDto dto) throws ResourceNotFoundException, AlreadyExistException, InvalidDataException, IOException {
        ProductDetails productDetails = null;
        String result = null;
        if (dto.getProductId()==null) {
                isProductExist(dto.getProductName());
                ProductCategory productCategory = productCategoryRepository.findByCategoryId(dto.getProductCategoryId());
                productDetails = ProductDetails.builder()
                        .productName(dto.getProductName())
                        .productDescription(dto.getProductDescription())
                        .productPrice(dto.getProductPrice())
                        .productStatus(MessageUtils.ADDED_STATUS)
                        .insertedTime(new Date())
                        .updatedTime(new Date())
                        .productCategory(productCategory)
                        .build();
                if (dto.getProductImage()!=null) {
                    productDetails.setProductImage(dto.getProductImage().getBytes());
                }
                result = MessageUtils.ADD_PRODUCT;
        } else {
                productDetails = productRepository.findByProductId(dto.getProductId());
                if (productDetails==null) {
                    throw new ResourceNotFoundException(MessageUtils.PRODUCT_NOT_EXIST);
                }
                if (!dto.getProductName().equals(productDetails.getProductName())) {
                    isProductExist(dto.getProductName());
                    productDetails.setProductName(dto.getProductName());
                }
                if (!dto.getProductDescription().equals(productDetails.getProductDescription())) {
                    productDetails.setProductDescription(dto.getProductDescription());
                }
                if (!dto.getProductCategoryId().equals(productDetails.getProductCategory().getCategoryId())) {
                    ProductCategory productCategory = productCategoryRepository.findByCategoryId(dto.getProductCategoryId());
                    productDetails.setProductCategory(productCategory);
                }
                if (!dto.getProductPrice().equals(productDetails.getProductPrice())) {
                    productDetails.setProductPrice(dto.getProductPrice());
                }
                productDetails.setUpdatedTime(new Date());
                result = MessageUtils.UPDATE_PRODUCT;
        }
        try {
             productRepository.save(productDetails);
             return result;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (AlreadyExistException e) {
            throw new AlreadyExistException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public EditProductDto getById(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            ProductDetails productDetails = productRepository.findByProductId(dto.getEntityId());
            if (productDetails==null) {
                throw new ResourceNotFoundException(MessageUtils.PRODUCT_NOT_EXIST);
            }
            return EditProductDto.builder()
                    .productId(productDetails.getProductId())
                    .productName(productDetails.getProductName())
                    .productDescription(productDetails.getProductDescription())
                    .productCategoryId(productDetails.getProductCategory().getCategoryId())
                    .productPrice(productDetails.getProductPrice())
                    .productImage(productDetails.getProductImage()!=null?productDetails.getProductImage():null)
                    .build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Boolean updateProductStatus(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            ProductDetails productDetails = productRepository.findByProductId(dto.getEntityId());
            Integer count = 0;
            if (productDetails==null) {
                throw new ResourceNotFoundException(MessageUtils.PRODUCT_NOT_EXIST);
            }
            if (productDetails.getProductStatus().equals(MessageUtils.ADDED_STATUS)) {
                count = productRepository.updateProductStatus(productDetails.getProductId(), MessageUtils.VERIFY_STATUS);
            }
            return count==1?true:false;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ViewProductDto> getAllProducts(EntityTypeDto dto) {
        List<ViewProductDto> responseList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(0);
        if (dto.getEntityType()!=null && dto.getEntityType().equals(MessageUtils.ADDED_STATUS)) {
            List<ProductDetails> fetchList = productRepository.getAllProductsByStatus(MessageUtils.ADDED_STATUS);
            responseList = !fetchList.isEmpty()?fetchList.stream()
                    .map(data -> {
                        return ViewProductDto.builder()
                                .srNo(index.incrementAndGet())
                                .productId(data.getProductId())
                                .productName(data.getProductName())
                                .productPrice(data.getProductPrice())
                                .productCategory(data.getProductCategory().getCategoryName())
                                .productStatus(setStatus(data.getProductStatus()))
                                .build();
                    }).collect(Collectors.toList()):new ArrayList<>();
        } else if (dto.getEntityType()!=null && dto.getEntityType().equals(MessageUtils.VERIFY_STATUS)) {
            List<ProductDetails> fetchList = productRepository.getAllProductsByStatus(MessageUtils.VERIFY_STATUS);
            responseList = !fetchList.isEmpty()?fetchList.stream()
                    .map(data -> {
                        return ViewProductDto.builder()
                                .srNo(index.incrementAndGet())
                                .productId(data.getProductId())
                                .productName(data.getProductName())
                                .productPrice(data.getProductPrice())
                                .productCategory(data.getProductCategory().getCategoryName())
                                .productStatus(setStatus(data.getProductStatus()))
                                .build();
                    }).collect(Collectors.toList()):new ArrayList<>();
        } else {
            List<ProductDetails> fetchList = productRepository.findAll();
            responseList = !fetchList.isEmpty()?fetchList.stream()
                    .map(data -> {
                        return ViewProductDto.builder()
                                .srNo(index.incrementAndGet())
                                .productId(data.getProductId())
                                .productName(data.getProductName())
                                .productPrice(data.getProductPrice())
                                .productCategory(data.getProductCategory().getCategoryName())
                                .productStatus(setStatus(data.getProductStatus()))
                                .build();
                    }).collect(Collectors.toList()):new ArrayList<>();
        }
        return responseList;
    }

    @Override
    public List<ViewProductDto> getVerifiedProductsByCategory(EntityIdDto dto) {
        List<ProductDetails> fetchList = productRepository.getAllVerifiedProductsByCategory(MessageUtils.VERIFY_STATUS,dto.getEntityId());
        AtomicInteger index = new AtomicInteger(0);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return ViewProductDto.builder()
                            .srNo(index.incrementAndGet())
                            .productId(data.getProductId())
                            .productName(data.getProductName())
                            .productPrice(data.getProductPrice())
                            .productCategory(data.getProductCategory().getCategoryName())
                            .productStatus(setStatus(data.getProductStatus()))
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public void deleteAll() {
        if (activeProfile.equals("test")) {
            productRepository.deleteAll();
            productCategoryRepository.deleteAll();
        }
    }

    private void isProductExist(String productName) {
        if (productRepository.existsByProductName(productName)) {
            throw new AlreadyExistException(MessageUtils.PRODUCT_EXIST);
        }
    }
    private String setStatus(Integer status) {
        return status.equals(MessageUtils.ADDED_STATUS)?MessageUtils.STATUS_INSERT:MessageUtils.STATUS_VERIFY;
    }
}
