package com.nirvan.bauhinia.order;

import com.nirvan.bauhinia.customer.Customer;
import com.nirvan.bauhinia.customer.CustomerServiceV2;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ItemNotFoundException;
import com.nirvan.bauhinia.exception.ProductOrderNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOrderServiceV2 {

    private final ProductOrderRepository PRODUCT_ORDER_REPOSITORY;
    private final CustomerServiceV2 CUSTOMER_SERVICE;
    private static final String ID_NOT_FOUND_MESSAGE = "Order with the following id does not exist: %s";
    private static final String INVALID_PRICE_MESSAGE = "Price is invalid: %s";
    private static final String INVALID_DATE_MESSAGE = "Date is invalid: %s";

    public List<ProductOrder> fetchAllProductOrders() {
        return PRODUCT_ORDER_REPOSITORY.findAll();
    }

    public ProductOrder fetchProductOrderById(int productOrderId) throws ProductOrderNotFoundException {
        return PRODUCT_ORDER_REPOSITORY.findById(productOrderId)
                .orElseThrow(() -> new ProductOrderNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, productOrderId)));
    }

    public Boolean addProductOrder(ProductOrder order, int customerId) throws InvalidParameterException {
        final double PRICE = order.getPrice();
        final LocalDate DATE = order.getDate();
        //
        // Check if price is valid
        //
        if(PRICE <= 0) {
            throw new InvalidParameterException(String.format(INVALID_PRICE_MESSAGE, PRICE));
        }
        //
        // Check if date is valid
        //
        if(DATE == null) {
            throw new InvalidParameterException(String.format(INVALID_DATE_MESSAGE, "null"));
        }
        PRODUCT_ORDER_REPOSITORY.save(order);
        //
        // Set the association between order and customer
        //
        assignCustomerToProductOrder(order.getId(), customerId);
        return true;
    }

    /**
     * Updates the price of the order or the order date
     *
     * @param productOrderId the identifier of the product order
     * @param price the new price of the entire order
     * @param date the new date of the order
     *
     * @return true if successfully updated
     * @throws InvalidParameterException if the price is invalid
     * */
    @Transactional
    public Boolean updateProductOrder(
            Integer productOrderId,
            Double price,
            LocalDate date
    ) throws InvalidParameterException {
        final ProductOrder PERSISTED_ORDER = fetchProductOrderById(productOrderId);
        //
        // Check if price is valid
        //
        if(price != null) {
            if(price <= 0) {
                throw new InvalidParameterException(String.format(INVALID_PRICE_MESSAGE, price));
            }
            PERSISTED_ORDER.setPrice(price);
        }
        //
        // Check if date is valid
        //
        if(date != null) {
            PERSISTED_ORDER.setDate(date);
        }
        PRODUCT_ORDER_REPOSITORY.save(PERSISTED_ORDER);
        return true;
    }

    @Transactional
    public Boolean assignCustomerToProductOrder(int customerId, int orderId) {
        final ProductOrder order = fetchProductOrderById(orderId);
        final Customer customer = CUSTOMER_SERVICE.fetchCustomerById(customerId);

        order.setCustomer(customer);
        List<ProductOrder> orderList = customer.getOrders();
        orderList.add(order);
        customer.setOrders(orderList);
        CUSTOMER_SERVICE.saveCustomer(customer);
        PRODUCT_ORDER_REPOSITORY.save(order);
        return true;
    }

    public Boolean delete(int productOrderId) {
        if(!PRODUCT_ORDER_REPOSITORY.existsById(productOrderId)) {
            throw new ItemNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, productOrderId));
        }
        PRODUCT_ORDER_REPOSITORY.deleteById(productOrderId);
        return true;
    }

    public void save(ProductOrder order) {
        PRODUCT_ORDER_REPOSITORY.save(order);
    }
}
