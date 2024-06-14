package com.projectx.ecommerce.controller;

import com.projectx.ecommerce.common.payloads.EntityIdAndTypeDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.utils.PaymentTestData;
import com.projectx.ecommerce.order.controller.PaymentController;
import com.projectx.ecommerce.order.service.PaymentService;
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
@WebMvcTest(PaymentController.class)
@AutoConfigureRestDocs
public class PaymentControllerIntegrationTest extends PaymentTestData {

    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void do_payment_test() throws Exception {
        when(paymentService.doPayment(doPayment())).thenReturn(true);
        mockMvc.perform(post("/payment/api/doPayment")
                        .content(toJson(doPayment()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_payment_details_by_payment_id_test() throws Exception {
        when(paymentService.doPayment(doPayment())).thenReturn(true);
        when(paymentService.getPaymentDetailsByPaymentId(new EntityIdDto(1L))).thenReturn(getPaymentDetailsById());
        mockMvc.perform(post("/payment/api/getPaymentDetailsByPaymentId")
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
    public void get_payment_details_by_order_id_test() throws Exception {
        when(paymentService.doPayment(doPayment())).thenReturn(true);
        when(paymentService.getPaymentDetailsByPaymentId(new EntityIdDto(1L))).thenReturn(getPaymentDetailsById());
        mockMvc.perform(post("/payment/api/getPaymentDetailsByOrderId")
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
    public void update_payment_status_test() throws Exception {
        when(paymentService.doPayment(doPayment())).thenReturn(true);
        when(paymentService.updatePaymentStatus(new EntityIdAndTypeDto(1L,MessageUtils.FULL_PAID))).thenReturn(true);
        mockMvc.perform(post("/payment/api/updatePaymentStatus")
                        .content(toJson(new EntityIdAndTypeDto(1L,MessageUtils.FULL_PAID)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_payment_history_by_payment_id_test() throws Exception {
        when(paymentService.doPayment(doPayment())).thenReturn(true);
        when(paymentService.getPaymentHistoryByPaymentId(new EntityIdDto(1L))).thenReturn(getPaymentHistory());
        mockMvc.perform(post("/payment/api/getPaymentHistoryByPaymentId")
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
    public void get_payment_history_by_order_id_test() throws Exception {
        when(paymentService.doPayment(doPayment())).thenReturn(true);
        when(paymentService.getPaymentHistoryByOrderId(new EntityIdDto(1L))).thenReturn(getPaymentHistory());
        mockMvc.perform(post("/payment/api/getPaymentHistoryByOrderId")
                        .content(toJson(new EntityIdDto(1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
