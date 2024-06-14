package com.projectx.ecommerce.controller;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.utils.CommonTestData;
import com.projectx.ecommerce.customer.controller.CustomerController;
import com.projectx.ecommerce.customer.service.CustomerService;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(CustomerController.class)
@AutoConfigureRestDocs
public class CustomerControllerIntegrationTest extends CommonTestData {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");


    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void register_customer_test() throws Exception {
        when(customerService.insertUpdate(createCustomerRequest())).thenReturn(MessageUtils.ADD_CUSTOMER);
        mockMvc.perform(post("/customer/api/insertUpdate")
                        .content(toJson(createCustomerRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
            .andDo(document("{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_customer_details_by_id_test() throws Exception {
        when(customerService.insertUpdate(createCustomerRequest())).thenReturn(MessageUtils.ADD_CUSTOMER);
        when(customerService.getById(new EntityIdDto(1L))).thenReturn(createCustomer());
        EntityIdDto entityIdDto = EntityIdDto.builder()
                .entityId(1L)
                .build();
        mockMvc.perform(post("/customer/api/getCustomerDetailsById")
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
    public void update_customer_status_test() throws Exception {
        when(customerService.insertUpdate(createCustomerRequest())).thenReturn(MessageUtils.ADD_CUSTOMER);
        when(customerService.updateCustomerStatus(new EntityIdDto(1L))).thenReturn(MessageUtils.CUSTOMER_DISABLE);
        EntityIdDto entityIdDto = EntityIdDto.builder()
                .entityId(1L)
                .build();
        mockMvc.perform(post("/customer/api/updateCustomerStatus")
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
    public void get_customer_drop_down_test() throws Exception {
        when(customerService.insertUpdate(createCustomer())).thenReturn(MessageUtils.ADD_CUSTOMER);
        when(customerService.insertUpdate(createCustomerRequest())).thenReturn(MessageUtils.ADD_CUSTOMER);
        when(customerService.getCustomerDropDown()).thenReturn(customerDropDown());
        mockMvc.perform(get("/customer/api/getCustomerDropDown")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_all_customers_test() throws Exception {
        when(customerService.insertUpdate(createCustomer())).thenReturn(MessageUtils.ADD_CUSTOMER);
        when(customerService.insertUpdate(createCustomerRequest())).thenReturn(MessageUtils.ADD_CUSTOMER);
        when(customerService.getAllCustomers()).thenReturn(getCustomerList(false));
        mockMvc.perform(get("/customer/api/getAllCustomers")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void get_all_enabled_customers_test() throws Exception {
        when(customerService.insertUpdate(createCustomer())).thenReturn(MessageUtils.ADD_CUSTOMER);
        when(customerService.insertUpdate(createCustomerRequest())).thenReturn(MessageUtils.ADD_CUSTOMER);
        when(customerService.getAllEnabledCustomers()).thenReturn(getCustomerList(true));
        mockMvc.perform(get("/customer/api/getAllEnabledCustomers")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
