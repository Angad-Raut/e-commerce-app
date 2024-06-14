package com.projectx.ecommerce.product.controller;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.EntityTypeDto;
import com.projectx.ecommerce.common.payloads.ResponseDto;
import com.projectx.ecommerce.product.payloads.EditProductDto;
import com.projectx.ecommerce.product.payloads.ProductDto;
import com.projectx.ecommerce.product.payloads.ViewProductDto;
import com.projectx.ecommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/product/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/insertUpdate")
    public ResponseEntity<ResponseDto<String>> insertUpdate(@ModelAttribute ProductDto dto) {
        try {
            String data = productService.insertUpdate(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.CREATED);
        } catch (ResourceNotFoundException | AlreadyExistException | InvalidDataException | IOException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getProductById")
    public ResponseEntity<ResponseDto<EditProductDto>> getProductById(@RequestBody EntityIdDto dto) {
        try {
            EditProductDto data = productService.getById(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateProductStatus")
    public ResponseEntity<ResponseDto<Boolean>> updateProductStatus(@RequestBody EntityIdDto dto) {
        try {
            Boolean data = productService.updateProductStatus(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getAllProductsByStatus")
    public ResponseEntity<ResponseDto<List<ViewProductDto>>> getAllProductsByStatus(@RequestBody EntityTypeDto dto) {
        try {
            List<ViewProductDto> data = productService.getAllProducts(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getVerifiedProductsByCategory")
    public ResponseEntity<ResponseDto<List<ViewProductDto>>> getVerifiedProductsByCategory(@RequestBody EntityIdDto dto) {
        try {
            List<ViewProductDto> data = productService.getVerifiedProductsByCategory(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }
}
