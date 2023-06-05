package com.nirvan.bauhinia.order;

import com.nirvan.bauhinia.customer.Customer;
import com.nirvan.bauhinia.customer.CustomerRepository;
import com.nirvan.bauhinia.exception.*;
import com.nirvan.bauhinia.item.Item;
import com.nirvan.bauhinia.item.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductOrderService {

    private final ProductOrderRepository PO_REPOSITORY;
    private final CustomerRepository CUSTOMER_REPOSITORY;
    private final ItemRepository ITEM_REPOSITORY;

    @Autowired
    public ProductOrderService(ProductOrderRepository poRepository, CustomerRepository customerRepository, ItemRepository itemRepository) {
        PO_REPOSITORY = poRepository;
        CUSTOMER_REPOSITORY = customerRepository;
        ITEM_REPOSITORY = itemRepository;
    }

    private ProductOrder getProductOrder(int id) {
        return PO_REPOSITORY.findById(id)
                .orElseThrow(() -> new ProductOrderNotFoundException("Order with id (" + id + ") does not exist"));
    }

    public List<ProductOrder> select() {
        return PO_REPOSITORY.findAll();
    }

    public ProductOrder selectById(int id) {
        return getProductOrder(id);
    }

    public ProductOrder insert(ProductOrder order) {
        // Check if price is valid
        if(order.getPrice() <= 0) {
            throw new InvalidParameterException("Price (" + order.getPrice() + ") is invalid");
        }

        // Check if date is valid
        if(order.getDate() == null) {
            throw new InvalidParameterException("Date is invalid");
        }

        PO_REPOSITORY.save(order);
        return order;
    }

    @Transactional
    public ProductOrder update(ProductOrder updatedOrder) {
        ProductOrder persistedOrder = getProductOrder(updatedOrder.getId());

        // Check if price is valid
        if(updatedOrder.getPrice() <= 0) {
            if(!Objects.equals(persistedOrder.getPrice(), updatedOrder.getPrice())) {
                persistedOrder.setPrice(updatedOrder.getPrice());
            }
        }
        else {
            throw new InvalidParameterException("Price (" + updatedOrder.getPrice() + ") is invalid");
        }

        // Check if date is valid
        if(updatedOrder.getDate() != null) {
            if(!Objects.equals(persistedOrder.getDate(), updatedOrder.getDate())) {
                persistedOrder.setDate(updatedOrder.getDate());
            }
        }
        else {
            throw new InvalidParameterException("Date is invalid");
        }

        return persistedOrder;
    }

    @Transactional
    public ProductOrder assignCustomerToProductOrder( int customerId, int orderId) {
        ProductOrder order = getProductOrder(orderId);
        Customer customer = CUSTOMER_REPOSITORY.findById(customerId)
                .orElseThrow(() ->  new CustomerNotFoundException("Customer with id (" + customerId + ") does not exist"));

        order.setCustomer(customer);
        List<ProductOrder> orderList = customer.getOrders();
        orderList.add(order);
        customer.setOrders(orderList);
        CUSTOMER_REPOSITORY.save(customer);
        PO_REPOSITORY.save(order);
        return order;
    }

    @Transactional
    public ProductOrder assignItemToProductOrder(int itemId, int orderId) {
        ProductOrder order = getProductOrder(orderId);
        Item item = ITEM_REPOSITORY.findById(itemId)
                .orElseThrow(() ->  new ItemNotFoundException("Item with id (" + itemId + ") does not exist"));

        List<Item> itemList = order.getItems();
        itemList.add(item);
        order.setItems(itemList);
        item.setOrder(order);
        PO_REPOSITORY.save(order);
        ITEM_REPOSITORY.save(item);
        return order;
    }

    public void delete(int productOrderId) {
        if(!PO_REPOSITORY.existsById(productOrderId)) {
            throw new ItemNotFoundException("Order with id (" + productOrderId + ") does not exist");
        }
        PO_REPOSITORY.deleteById(productOrderId);
    }
}
