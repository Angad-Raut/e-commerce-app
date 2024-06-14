package com.projectx.ecommerce.controller;

import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.utils.InventoryStockTestData;
import com.projectx.ecommerce.inventory.controller.BatchController;
import com.projectx.ecommerce.inventory.payloads.CategoryAndProductDto;
import com.projectx.ecommerce.inventory.service.BatchService;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(BatchController.class)
@AutoConfigureRestDocs
public class BatchControllerIntegrationTest extends InventoryStockTestData {

    private MockMvc mockMvc;
    @MockBean
    private BatchService batchService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void add_batch_test() throws Exception {
        when(batchService.insertUpdate(createBatch())).thenReturn(MessageUtils.ADD_BATCH);
        mockMvc.perform(post("/batch/api/insertUpdate")
                        .content(toJson(createBatch()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_batch_by_id_test() throws Exception {
        when(batchService.insertUpdate(createBatch())).thenReturn(MessageUtils.ADD_BATCH);
        when(batchService.getById(new EntityIdDto(1L))).thenReturn(createBatch());
        mockMvc.perform(post("/batch/api/getBatchById")
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
    public void update_batch_status_by_id_test() throws Exception {
        when(batchService.insertUpdate(createBatch())).thenReturn(MessageUtils.ADD_BATCH);
        when(batchService.updateBatchStatus(new EntityIdDto(1L))).thenReturn(true);
        mockMvc.perform(post("/batch/api/updateBatchStatus")
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
    public void get_batch_drop_down_test() throws Exception {
        CategoryAndProductDto categoryAndProductDto = CategoryAndProductDto.builder()
                .categoryId(1L)
                .productId(1L)
                .build();
        when(batchService.insertUpdate(createBatch())).thenReturn(MessageUtils.ADD_BATCH);
        when(batchService.getBatchDropDown(categoryAndProductDto)).thenReturn(batchDropDown());
        mockMvc.perform(post("/batch/api/getBatchDropDown")
                        .content(toJson(categoryAndProductDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_all_batch_list_test() throws Exception {
        CategoryAndProductDto categoryAndProductDto = CategoryAndProductDto.builder()
                .categoryId(1L)
                .productId(1L)
                .build();
        when(batchService.insertUpdate(createBatch())).thenReturn(MessageUtils.ADD_BATCH);
        when(batchService.getAllBatchList(categoryAndProductDto)).thenReturn(getBatchList());
        mockMvc.perform(post("/batch/api/getAllBatchList")
                        .content(toJson(categoryAndProductDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
