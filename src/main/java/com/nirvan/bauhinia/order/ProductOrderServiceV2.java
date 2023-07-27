package com.nirvan.bauhinia.order;

import com.nirvan.bauhinia.customer.Customer;
import com.nirvan.bauhinia.customer.CustomerServiceV2;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ItemNotFoundException;
import com.nirvan.bauhinia.exception.ProductOrderNotFoundException;
import com.nirvan.bauhinia.orderAddress.OrderAddress;
import com.nirvan.bauhinia.orderAddress.OrderAddressService;
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
    private final OrderAddressService ADDRESS_SERVICE;
    private static final String ID_NOT_FOUND_MESSAGE = "Order with the following id does not exist: %s";
    private static final String INVALID_PRICE_MESSAGE = "Price is invalid: %s";
    private static final String INVALID_DATE_MESSAGE = "Date is invalid: %s";

    public ProductOrderStats getProductOrderCount() {
        Integer count = PRODUCT_ORDER_REPOSITORY.getProductOrderCount().get();
        return new ProductOrderStats(count);
    }

    public List<ProductOrder> fetchAllProductOrders() {
        return PRODUCT_ORDER_REPOSITORY.findAll();
    }

    public ProductOrder fetchProductOrderById(int productOrderId) throws ProductOrderNotFoundException {
        return PRODUCT_ORDER_REPOSITORY.findById(productOrderId)
                .orElseThrow(() -> new ProductOrderNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, productOrderId)));
    }
//
//    public ProductOrder addProductOrder(ProductOrder order, int customerId, int shippingAddrId, int billingAddrId) throws InvalidParameterException {
//        final double PRICE = order.getPrice();
//        final LocalDate DATE = order.getDate();
//
//        if(PRICE <= 0) {
//            throw new InvalidParameterException(String.format(INVALID_PRICE_MESSAGE, PRICE));
//        }
//        if(DATE == null) {
//            throw new InvalidParameterException(String.format(INVALID_DATE_MESSAGE, "null"));
//        }
//
//        PRODUCT_ORDER_REPOSITORY.save(order);
//        assignCustomerToProductOrder(customerId, order.getId());
//        return order;
//    }
    public ProductOrder addProductOrderV2(ProductOrder order, int customerId) throws InvalidParameterException {
        final double PRICE = order.getPrice();
        final List<OrderAddress> addressList = order.getAddresses();
        final LocalDate DATE = order.getDate();
        Customer customer = CUSTOMER_SERVICE.fetchCustomerById(customerId);

        if(PRICE <= 0) {
            throw new InvalidParameterException(String.format(INVALID_PRICE_MESSAGE, PRICE));
        }
        if(DATE == null) {
            throw new InvalidParameterException(String.format(INVALID_DATE_MESSAGE, "null"));
        }

        order.setCustomer(customer);
        List<ProductOrder> orderList = customer.getOrders();
        orderList.add(order);
        customer.setOrders(orderList);
        CUSTOMER_SERVICE.saveCustomer(customer);
        addressList.forEach(address -> {
            address.setOrder(order);
        });
        PRODUCT_ORDER_REPOSITORY.save(order);
//        assignCustomerToProductOrder(customerId, order.getId());
        return order;
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

        if(price != null) {
            if(price <= 0) {
                throw new InvalidParameterException(String.format(INVALID_PRICE_MESSAGE, price));
            }
            PERSISTED_ORDER.setPrice(price);
        }
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
