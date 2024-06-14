package com.projectx.ecommerce.controller;

import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.utils.CommonTestData;
import com.projectx.ecommerce.product.controller.ProductCategoryController;
import com.projectx.ecommerce.product.service.ProductCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@ExtendWith({ RestDocumentationExtension.class})
@WebMvcTest(ProductCategoryController.class)
@AutoConfigureRestDocs
public class ProductCategoryControllerIntegrationTest extends CommonTestData {

     private MockMvc mockMvc;

     @MockBean
     private ProductCategoryService productCategoryService;

     @BeforeEach
     public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
     }

    @Test
    public void create_category_test() throws Exception {
         when(productCategoryService.insertUpdate(createCategory())).thenReturn(MessageUtils.ADD_PRODUCT_CATEGORY);
         mockMvc.perform(post("/category/api/insertUpdate")
                        .content(toJson(createCategory()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                 .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_product_category_by_id_test() throws Exception {
        when(productCategoryService.insertUpdate(createCategory())).thenReturn(MessageUtils.ADD_PRODUCT_CATEGORY);
        when(productCategoryService.getById(new EntityIdDto(1L))).thenReturn(createCategory());
        EntityIdDto entityIdDto = EntityIdDto.builder()
                .entityId(1L)
                .build();
        mockMvc.perform(post("/category/api/getProductCategoryById")
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
    public void update_category_status_test() throws Exception {
        when(productCategoryService.insertUpdate(createCategory())).thenReturn(MessageUtils.ADD_PRODUCT_CATEGORY);
        when(productCategoryService.updateCategoryStatus(new EntityIdDto(1L))).thenReturn(MessageUtils.CATEGORY_DISABLE);
        EntityIdDto entityIdDto = EntityIdDto.builder()
                .entityId(1L)
                .build();
        mockMvc.perform(post("/category/api/updateCategoryStatus")
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
    public void get_product_category_drop_down_test() throws Exception {
        when(productCategoryService.insertUpdate(createCategory())).thenReturn(MessageUtils.ADD_PRODUCT_CATEGORY);
        when(productCategoryService.insertUpdate(createCategoryTwo())).thenReturn(MessageUtils.ADD_PRODUCT_CATEGORY);
        when(productCategoryService.getCategoryDropDown()).thenReturn(categoryDropDown());
        mockMvc.perform(get("/category/api/getProductCategoryDropDown")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_all_categories_test() throws Exception {
        when(productCategoryService.insertUpdate(createCategory())).thenReturn(MessageUtils.ADD_PRODUCT_CATEGORY);
        when(productCategoryService.insertUpdate(createCategoryTwo())).thenReturn(MessageUtils.ADD_PRODUCT_CATEGORY);
        when(productCategoryService.getAllCategories()).thenReturn(getCategoryList(false));
        mockMvc.perform(get("/category/api/getAllCategories")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_all_enabled_categories_test() throws Exception {
        when(productCategoryService.insertUpdate(createCategory())).thenReturn(MessageUtils.ADD_PRODUCT_CATEGORY);
        when(productCategoryService.insertUpdate(createCategoryTwo())).thenReturn(MessageUtils.ADD_PRODUCT_CATEGORY);
        when(productCategoryService.getAllEnabledCategories()).thenReturn(getCategoryList(true));
        mockMvc.perform(get("/category/api/getAllEnabledCategories")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
