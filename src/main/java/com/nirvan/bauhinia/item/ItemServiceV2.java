package com.nirvan.bauhinia.item;

import com.nirvan.bauhinia.cart.Cart;
import com.nirvan.bauhinia.cart.CartServiceV2;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ItemNotFoundException;
import com.nirvan.bauhinia.order.ProductOrder;
import com.nirvan.bauhinia.order.ProductOrderServiceV2;
import com.nirvan.bauhinia.product.Product;
import com.nirvan.bauhinia.product.ProductServiceV2;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceV2 {

    private final ItemRepository ITEM_REPOSITORY;
    private final CartServiceV2 CART_SERVICE;
    private final ProductServiceV2 PRODUCT_SERVICE;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Item with the following id does not exist: %s";
    private static final String SKU_NOT_FOUND_MESSAGE = "Item with the following SKU does not exist: %s";
    private static final String INVALID_SKU_MESSAGE = "SKU is invalid: %s";
    private static final String DUPLICATE_SKU_MESSAGE = "SKU already exists: %s";
    private static final String INVALID_SIZE_MESSAGE = "Size is invalid: %s";
    private static final String INVALID_COLOUR_MESSAGE = "Colour is invalid: %s";
    private static final String INVALID_COST_MESSAGE = "Cost is invalid: %s";
    private final ProductOrderServiceV2 ORDER_SERVICE;


    public Item fetchItemById(int itemId) throws ItemNotFoundException {
        return ITEM_REPOSITORY.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, itemId)));
    }

    public List<Item> fetchAllItems() {
        return ITEM_REPOSITORY.findAll();
    }

    public Item fetchItemBySku(String sku) throws ItemNotFoundException {
        return ITEM_REPOSITORY.findBySku(sku)
                .orElseThrow(() -> new ItemNotFoundException(String.format(SKU_NOT_FOUND_MESSAGE, sku)));
    }

    public Boolean addItem(Item item, int productId) throws InvalidParameterException {
        final String SKU = item.getSku();
        final String SIZE = item.getSize();
        final String COLOUR = item.getColour();
        final double COST = item.getCost();
        //
        // Make sure sku is unique and valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(SKU)) {
            throw new InvalidParameterException(String.format(INVALID_SKU_MESSAGE, SKU));
        }
        else if(ITEM_REPOSITORY.existsItemBySku(SKU)) {
            throw new InvalidParameterException(String.format(DUPLICATE_SKU_MESSAGE, SKU));
        }
        //
        // Check if size is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(SIZE)) {
            throw new InvalidParameterException(String.format(INVALID_SIZE_MESSAGE, SIZE));
        }
        //
        // Check if colour is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(COLOUR)) {
            throw new InvalidParameterException(String.format(INVALID_COLOUR_MESSAGE, COLOUR));
        }
        //
        // Check if cost is valid
        //
        if(COST < 0) {
            throw new InvalidParameterException(String.format(INVALID_COST_MESSAGE, COST));
        }
        ITEM_REPOSITORY.save(item);
        assignProductToItem(item.getId(), productId);
        return true;
    }

    @Transactional
    public Boolean updateItem(
            int itemId,
            String sku,
            String size,
            String colour,
            Double cost
    ) throws InvalidParameterException {
        final Item PERSISTED_ITEM = fetchItemById(itemId);
        //
        // Make sure sku is unique
        //
        if(sku != null) {
            if(!VALIDATION.validNonBlankParam(sku)) {
                throw new InvalidParameterException(String.format(INVALID_SKU_MESSAGE, sku));
            }
            if(ITEM_REPOSITORY.existsItemBySku(sku) && !PERSISTED_ITEM.getSku().equals(sku)) {
                throw new InvalidParameterException(String.format(DUPLICATE_SKU_MESSAGE, sku));
            }
            PERSISTED_ITEM.setSku(sku);
        }
        //
        // Check if size is valid
        //
        if(size != null) {
            if(!VALIDATION.validNonBlankParam(size)) {
                throw new InvalidParameterException(String.format(INVALID_SIZE_MESSAGE, size));
            }
            PERSISTED_ITEM.setSize(size);
        }
        //
        // Check if colour is valid
        //
        if(colour != null) {
            if(!VALIDATION.validNonBlankParam(colour)) {
                throw new InvalidParameterException(String.format(INVALID_COLOUR_MESSAGE, colour));
            }
            PERSISTED_ITEM.setColour(colour);
        }
        //
        // Check if cost is valid
        //
        if(cost != null) {
            if(cost <= 0) {
                throw new InvalidParameterException(String.format(INVALID_COST_MESSAGE, cost));
            }
            PERSISTED_ITEM.setCost(cost);
        }
        return true;
    }

    public Boolean deleteItem(int itemId) throws ItemNotFoundException {
        if(!ITEM_REPOSITORY.existsById(itemId)) {
            throw new ItemNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, itemId));
        }
        ITEM_REPOSITORY.deleteById(itemId);
        return true;
    }

    @Transactional
    public Boolean addItemToCart(int itemId, int cartId) {
        final Item ITEM = fetchItemById(itemId);
        final Cart CART = CART_SERVICE.fetchCartById(cartId);

        final List<Item> CART_ITEMS = CART.getItems();
        CART_ITEMS.add(ITEM);
        CART.setItems(CART_ITEMS);

        final List<Cart> CART_LIST = ITEM.getCarts();
        CART_LIST.add(CART);
        ITEM.setCarts(CART_LIST);

        ITEM_REPOSITORY.save(ITEM);
        CART_SERVICE.saveCart(CART);
        return true;
    }

    public Boolean assignProductToItem(int itemId, int productId) {
        final Item ITEM = fetchItemById(itemId);
        final Product product = PRODUCT_SERVICE.fetchProductById(productId);

        List<Item> itemList = product.getItemList();
        itemList.add(ITEM);
        product.setItemList(itemList);
        ITEM.setProduct(product);

        ITEM_REPOSITORY.save(ITEM);
        PRODUCT_SERVICE.saveProduct(product);
        return true;
    }

    public Boolean removeItemFromCart(int itemId, int cartId) {
        final Item ITEM = fetchItemById(itemId);
        final Cart CART = CART_SERVICE.fetchCartById(cartId);

        final List<Item> ITEM_LIST = CART.getItems();
        ITEM_LIST.remove(ITEM);
        CART.setItems(ITEM_LIST);

        final List<Cart> CART_LIST = ITEM.getCarts();
        CART_LIST.remove(CART);
        ITEM.setCarts(CART_LIST);

        ITEM_REPOSITORY.save(ITEM);
        CART_SERVICE.saveCart(CART);
        return true;
    }

    public Boolean addItemToOrder(int itemId, int orderId) {
        final Item ITEM = fetchItemById(itemId);
        final ProductOrder ORDER = ORDER_SERVICE.fetchProductOrderById(orderId);

        final List<Item> ITEM_LIST = ORDER.getItems();
        ITEM_LIST.add(ITEM);
        ORDER.setItems(ITEM_LIST);

        ITEM.setOrder(ORDER);

        ITEM_REPOSITORY.save(ITEM);
        ORDER_SERVICE.save(ORDER);
        return null;
    }

    public void saveItem(Item item) {
        ITEM_REPOSITORY.save(item);
    }
}
