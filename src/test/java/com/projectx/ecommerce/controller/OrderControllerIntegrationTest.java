package com.projectx.ecommerce.controller;

import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.utils.OrderTestData;
import com.projectx.ecommerce.order.controller.OrderController;
import com.projectx.ecommerce.order.service.OrderService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(OrderController.class)
@AutoConfigureRestDocs
public class OrderControllerIntegrationTest extends OrderTestData {

    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void place_order_test() throws Exception {
        when(orderService.placeOrder(placeOrder())).thenReturn(MessageUtils.PLACE_ORDER_MSG+MessageUtils.ORDER_NUMBER);
        mockMvc.perform(post("/order/api/placeOrder")
                        .content(toJson(placeOrder()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_order_details_by_id_test() throws Exception {
        when(orderService.placeOrder(placeOrder())).thenReturn(MessageUtils.PLACE_ORDER_MSG);
        when(orderService.getByOrderId(new EntityIdDto(1L))).thenReturn(setOrderDetails());
        mockMvc.perform(post("/order/api/getOrderDetailsById")
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
    public void update_order_status_by_order_id_test() throws Exception {
        when(orderService.placeOrder(placeOrder())).thenReturn(MessageUtils.PLACE_ORDER_MSG);
        when(orderService.updateOrderStatus(new EntityIdDto(1L))).thenReturn(true);
        mockMvc.perform(post("/order/api/updateOrderStatusByOrderId")
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
    public void get_customer_order_history_by_customerId_test() throws Exception {
        when(orderService.placeOrder(placeOrder())).thenReturn(MessageUtils.PLACE_ORDER_MSG);
        when(orderService.getCustomerOrderHistory(new EntityIdDto(1L))).thenReturn(getCustomerOrderHistory());
        mockMvc.perform(post("/order/api/getCustomerOrderHistoryByCustomerId")
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
    public void get_all_in_completed_orders_test() throws Exception {
        when(orderService.placeOrder(placeOrder())).thenReturn(MessageUtils.PLACE_ORDER_MSG);
        when(orderService.getAllInCompletedOrders()).thenReturn(getAllOrders(false));
        mockMvc.perform(get("/order/api/getAllInCompletedOrders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_all_completed_orders_test() throws Exception {
        when(orderService.placeOrder(placeOrder())).thenReturn(MessageUtils.PLACE_ORDER_MSG);
        when(orderService.getAllCompletedOrders()).thenReturn(getAllOrders(true));
        mockMvc.perform(get("/order/api/getAllCompletedOrders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
