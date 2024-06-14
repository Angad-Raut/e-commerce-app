package com.projectx.ecommerce.repository;

import com.projectx.ecommerce.customer.entity.CustomerDetails;
import com.projectx.ecommerce.customer.repository.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
    }

    public CustomerDetails toCustomer() {
        CustomerDetails details = CustomerDetails.builder()
                .customerName("Angad Raut")
                .customerMobile(9766945760L)
                .customerEmail("angadraut75@gmail.com")
                .customerAddress("Pune ,Maharashtra")
                .customerStatus(true)
                .updatedTime(new Date())
                .insertedTime(new Date())
                .build();
        return customerRepository.save(details);
    }

    public CustomerDetails toCustomerTwo() {
        CustomerDetails details = CustomerDetails.builder()
                .customerName("Shital Raut")
                .customerMobile(9766945761L)
                .customerEmail("shitalraut95@gmail.com")
                .customerAddress("Pune ,Maharashtra")
                .customerStatus(true)
                .updatedTime(new Date())
                .insertedTime(new Date())
                .build();
        return customerRepository.save(details);
    }

    @Test
    public void find_by_customer_details_test() {
        CustomerDetails customerDetails = toCustomer();
        CustomerDetails details = customerRepository.findByCustomerId(customerDetails.getCustomerId());
        assertEquals(details.getCustomerName(),customerDetails.getCustomerName());
        assertEquals(details.getCustomerEmail(),customerDetails.getCustomerEmail());
        assertEquals(details.getCustomerMobile(),customerDetails.getCustomerMobile());
        assertEquals(details.getCustomerAddress(),customerDetails.getCustomerAddress());
    }

    @Test
    public void exists_by_customer_mobile_test() {
        CustomerDetails customerDetails = toCustomer();
        Boolean status = customerRepository.existsByCustomerMobile(customerDetails.getCustomerMobile());
        assertTrue(status);
    }

    @Test
    public void exists_by_customer_email_test() {
        CustomerDetails customerDetails = toCustomer();
        Boolean status = customerRepository.existsByCustomerEmail(customerDetails.getCustomerEmail());
        assertTrue(status);
    }

    @Test
    public void update_customer_status_test() {
        CustomerDetails customerDetails = toCustomer();
        Integer status = customerRepository.updateCustomerStatus(customerDetails.getCustomerId(),false);
        assertEquals(status,1);
    }

    @Test
    public void get_customer_drop_down_test() {
        CustomerDetails firstCustomer = toCustomer();
        CustomerDetails secondCustomer = toCustomerTwo();
        List<Object[]> fetchList = customerRepository.getCustomerDropDown(true);
        Object[] first = fetchList.get(0);
        Object[] second = fetchList.get(1);
        assertEquals(Long.parseLong(first[0].toString()),firstCustomer.getCustomerId());
        assertEquals(first[1].toString(),firstCustomer.getCustomerName());
        assertEquals(Long.parseLong(second[0].toString()),secondCustomer.getCustomerId());
        assertEquals(second[1].toString(),secondCustomer.getCustomerName());
    }

    @Test
    public void get_all_enabled_customers_test() {
        CustomerDetails firstCustomer = toCustomer();
        CustomerDetails secondCustomer = toCustomerTwo();
        List<CustomerDetails> fetchList = customerRepository.getAllEnabledCustomers(true);
        assertEquals(fetchList.get(0).getCustomerId(),firstCustomer.getCustomerId());
        assertEquals(fetchList.get(0).getCustomerName(),firstCustomer.getCustomerName());
        assertEquals(fetchList.get(0).getCustomerEmail(),firstCustomer.getCustomerEmail());
        assertEquals(fetchList.get(0).getCustomerMobile(),firstCustomer.getCustomerMobile());
        assertEquals(fetchList.get(0).getCustomerAddress(),firstCustomer.getCustomerAddress());
        assertEquals(fetchList.get(1).getCustomerId(),secondCustomer.getCustomerId());
        assertEquals(fetchList.get(1).getCustomerName(),secondCustomer.getCustomerName());
        assertEquals(fetchList.get(1).getCustomerEmail(),secondCustomer.getCustomerEmail());
        assertEquals(fetchList.get(1).getCustomerMobile(),secondCustomer.getCustomerMobile());
        assertEquals(fetchList.get(1).getCustomerAddress(),secondCustomer.getCustomerAddress());
    }
}
