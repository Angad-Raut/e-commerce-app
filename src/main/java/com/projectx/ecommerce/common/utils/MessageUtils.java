package com.projectx.ecommerce.common.utils;

import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MessageUtils {

    //Login related messages
    public static final String USER_NOT_FOUND="User not present in the system,please try another!!";

    //Customer related messages
    public static final String ADD_CUSTOMER="Customer registered successfully!!";
    public static final String UPDATE_CUSTOMER="Customer details updated successfully!!";
    public static final String CUSTOMER_ENABLE="Customer enabled successfully!!";
    public static final String CUSTOMER_DISABLE="Customer disabled successfully!!";
    public static final String CUSTOMER_NOT_EXIST="Customer not present in the system!!";
    public static final String CUSTOMER_MOBILE_EXIST="Customer already registered with given mobile number!!";
    public static final String CUSTOMER_EMAIL_EXIST="Customer already registered with given email!!";

    //Product Category messages
    public static final String ADD_PRODUCT_CATEGORY="Product category added successfully!!";
    public static final String UPDATE_PRODUCT_CATEGORY="Product category update successfully!!";
    public static final String CATEGORY_ENABLE="Product category enabled successfully!!";
    public static final String CATEGORY_DISABLE="Product category disabled successfully!!";
    public static final String CATEGORY_NOT_EXIST="Product category not present in the system!!";
    public static final String CATEGORY_EXIST="Product category already exist in the system!!";

    //Product related messages
    public static final String ADD_PRODUCT="Product added successfully!!";
    public static final String UPDATE_PRODUCT="Product details updated successfully!!";
    public static final String PRODUCT_EXIST="Product already exist in the system!!";
    public static final String PRODUCT_NOT_EXIST="Product details not exist in the system!!";
    public static final Integer ADDED_STATUS=1;
    public static final Integer VERIFY_STATUS=2;
    public static final String STATUS_INSERT="Inserted";
    public static final String STATUS_VERIFY="Verified";

    //Batch details messages
    public static final String ADD_BATCH="Batch added successfully!!";
    public static final String UPDATE_BATCH="Batch details updated successfully!!";
    public static final String BATCH_ALREADY_EXIST="Batch already exist in the system!!";
    public static final String BATCH_NOT_EXIST="Batch details not present in the system!!";
    public static final String BATCH_LIMIT_EXIST="Insufficient product batch quantity!!";

    //Inventory stock related messages
    public static final String ADD_STOCK="Product added in inventory stock!!";
    public static final String STOCK_ALREADY_EXIST="Product already added in inventory stock!!";
    public static final String STOCK_NOT_EXIST="Product inventory stock not present in the system!!";

    //Order & Payment messages
    public static final String PLACE_ORDER_MSG="Order has been placed with ";
    public static final String ORDER_NOT_EXIST="Order details not present in the system!!";
    public static final String PAYMENT_DETAILS_NOT_EXIST="Payment details not present!!";
    public static final String PAYMENT_ALREADY_DONE="Order payment already completed!!";
    public static final String ORDER_NUMBER="100001";
    public static final String ORDER_NUMBER_TWO="100002";
    public static final Integer UNPAID_STATUS=1;
    public static final Integer PARTIALLY_PAID=2;
    public static final Integer FULL_PAID=3;
    public static final String UNPAID_STATUS_MSG="Unpaid";
    public static final String PARTIALLY_PAID_MSG="Partially Paid";
    public static final String FULL_PAID_MSG="Paid";
    public static final String setDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);
    }
    public static final Integer DEFAULT_COUNT=0;
    public static final Integer NFT_TYPE=1;
    public static final Integer CHEQUE_TYPE=2;
    public static final Integer CASH_TYPE=3;
    public static final String NFT_TYPE_MSG="NFT";
    public static final String CHEQUE_TYPE_MSG="Cheque";
    public static final String CASH_TYPE_MSG="Cash";

    public static final String DISCOUNT_NOT_FOUND="Discount not exist in the system!!";
    public static final String DISCOUNT_EXISTS="Discount already exists with given value!!";
}
