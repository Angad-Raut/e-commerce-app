package com.projectx.ecommerce.controller;

import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.utils.InventoryStockTestData;
import com.projectx.ecommerce.inventory.controller.InventoryStockController;
import com.projectx.ecommerce.inventory.payloads.InventoryRequestDto;
import com.projectx.ecommerce.inventory.service.InventoryStockService;
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
@WebMvcTest(InventoryStockController.class)
@AutoConfigureRestDocs
public class InventoryStockControllerIntegrationTest extends InventoryStockTestData {

    private MockMvc mockMvc;
    @MockBean
    private InventoryStockService inventoryStockService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void add_stock_test() throws Exception {
        when(inventoryStockService.insertUpdate(createStock(true))).thenReturn(MessageUtils.ADD_STOCK);
        mockMvc.perform(post("/inventoryStock/api/insertUpdate")
                        .content(toJson(createStock(true)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_stock_by_id_test() throws Exception {
        when(inventoryStockService.insertUpdate(createStock(true))).thenReturn(MessageUtils.ADD_STOCK);
        when(inventoryStockService.getById(new EntityIdDto(1L))).thenReturn(createStock(false));
        mockMvc.perform(post("/inventoryStock/api/getById")
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
    public void update_stock_status_by_id_test() throws Exception {
        when(inventoryStockService.insertUpdate(createStock(true))).thenReturn(MessageUtils.ADD_STOCK);
        when(inventoryStockService.updateStockStatus(new EntityIdDto(1L))).thenReturn(true);
        mockMvc.perform(post("/inventoryStock/api/updateStockStatus")
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
    public void stock_drop_down_test() throws Exception {
        InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder()
                .categoryId(1L)
                .productId(1L)
                .batchId(1L)
                .build();
        when(inventoryStockService.insertUpdate(createStock(true))).thenReturn(MessageUtils.ADD_STOCK);
        when(inventoryStockService.getInventoryStockDropDown(inventoryRequestDto)).thenReturn(stockDropDown());
        mockMvc.perform(post("/inventoryStock/api/getInventoryStockDropDown")
                        .content(toJson(inventoryRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_all_inventory_stock_test() throws Exception {
        InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder()
                .categoryId(1L)
                .productId(1L)
                .batchId(1L)
                .build();
        when(inventoryStockService.insertUpdate(createStock(true))).thenReturn(MessageUtils.ADD_STOCK);
        when(inventoryStockService.getAllInventoryStock(inventoryRequestDto)).thenReturn(getStockList());
        mockMvc.perform(post("/inventoryStock/api/getAllInventoryStock")
                        .content(toJson(inventoryRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
