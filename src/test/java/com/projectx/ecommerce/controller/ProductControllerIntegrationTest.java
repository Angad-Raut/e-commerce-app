package com.projectx.ecommerce.controller;

import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.EntityTypeDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.utils.CommonTestData;
import com.projectx.ecommerce.product.controller.ProductController;
import com.projectx.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({ RestDocumentationExtension.class})
@WebMvcTest(ProductController.class)
public class ProductControllerIntegrationTest extends CommonTestData {

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void add_product_test() throws Exception {
        when(productService.insertUpdate(createProduct())).thenReturn(MessageUtils.ADD_PRODUCT);
        mockMvc.perform(post("/product/api/insertUpdate")
                        .content(toJson(createProduct()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_product_by_id_test() throws Exception {
        when(productService.insertUpdate(createProduct())).thenReturn(MessageUtils.ADD_PRODUCT);
        when(productService.getById(new EntityIdDto(1L))).thenReturn(getProductById());
        EntityIdDto entityIdDto = EntityIdDto.builder().entityId(1L).build();
        mockMvc.perform(post("/product/api/getProductById")
                        .content(toJson(entityIdDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void update_product_status_test() throws Exception {
        when(productService.insertUpdate(createProduct())).thenReturn(MessageUtils.ADD_PRODUCT);
        when(productService.updateProductStatus(new EntityIdDto(1L))).thenReturn(true);
        mockMvc.perform(post("/product/api/updateProductStatus")
                        .content(toJson(new EntityIdDto(1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_all_products_by_status_test() throws Exception {
        when(productService.insertUpdate(createProduct())).thenReturn(MessageUtils.ADD_PRODUCT);
        when(productService.insertUpdate(createProductTwo())).thenReturn(MessageUtils.ADD_PRODUCT);
        when(productService.getAllProducts(new EntityTypeDto(1))).thenReturn(getProductList(false));
        EntityTypeDto entityTypeDto = EntityTypeDto.builder().entityType(1).build();
        mockMvc.perform(post("/product/api/getAllProductsByStatus")
                        .content(toJson(entityTypeDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_verified_products_by_category_test() throws Exception {
        when(productService.insertUpdate(createProduct())).thenReturn(MessageUtils.ADD_PRODUCT);
        when(productService.insertUpdate(createProductTwo())).thenReturn(MessageUtils.ADD_PRODUCT);
        when(productService.getVerifiedProductsByCategory(new EntityIdDto(1L))).thenReturn(getProductList(true));
        EntityIdDto entityIdDto = EntityIdDto.builder().entityId(1L).build();
        mockMvc.perform(post("/product/api/getVerifiedProductsByCategory")
                        .content(toJson(entityIdDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
