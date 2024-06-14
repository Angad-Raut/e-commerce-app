package com.projectx.ecommerce.product.controller;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.ResponseDto;
import com.projectx.ecommerce.product.payloads.CategoryDto;
import com.projectx.ecommerce.product.payloads.ViewCategoryDto;
import com.projectx.ecommerce.product.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category/api")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping(value = "/insertUpdate")
    public ResponseEntity<ResponseDto<String>> insertUpdate(@RequestBody CategoryDto dto) {
        try {
            String data = productCategoryService.insertUpdate(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.CREATED);
        } catch (ResourceNotFoundException | AlreadyExistException | InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getProductCategoryById")
    public ResponseEntity<ResponseDto<CategoryDto>> getProductCategoryById(@RequestBody EntityIdDto dto) {
        try {
            CategoryDto data = productCategoryService.getById(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateCategoryStatus")
    public ResponseEntity<ResponseDto<String>> updateCategoryStatus(@RequestBody EntityIdDto dto) {
        try {
            String data = productCategoryService.updateCategoryStatus(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getProductCategoryDropDown")
    public ResponseEntity<ResponseDto<List<EntityIdAndValueDto>>> getCategoryDropDown() {
        try {
            List<EntityIdAndValueDto> data = productCategoryService.getCategoryDropDown();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllCategories")
    public ResponseEntity<ResponseDto<List<ViewCategoryDto>>> getAllCategories() {
        try {
            List<ViewCategoryDto> data = productCategoryService.getAllCategories();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllEnabledCategories")
    public ResponseEntity<ResponseDto<List<ViewCategoryDto>>> getAllEnabledCategories() {
        try {
            List<ViewCategoryDto> data = productCategoryService.getAllEnabledCategories();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }
}
