package com.nirvan.bauhinia.item;

import com.nirvan.bauhinia.cart.Cart;
import com.nirvan.bauhinia.cart.CartServiceV2;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ItemNotFoundException;
import com.nirvan.bauhinia.imagedata.ImageData;
import com.nirvan.bauhinia.imagedata.ImageDataService;
import com.nirvan.bauhinia.order.ProductOrder;
import com.nirvan.bauhinia.order.ProductOrderServiceV2;
import com.nirvan.bauhinia.product.Product;
import com.nirvan.bauhinia.product.ProductServiceV2;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    private static final String INVALID_HEX_CODE_MESSAGE = "Hexadecimal colour code is invalid: %s";
    private static final String INVALID_COST_MESSAGE = "Cost is invalid: %s";
    private final ProductOrderServiceV2 ORDER_SERVICE;

    private final ImageDataService IMAGE_DATA_SERVICE;


    public Product fetchProductIdBySku(String sku) {
        return ITEM_REPOSITORY.findProductBySku(sku).orElseThrow(() -> new ItemNotFoundException(""));
    }

    public Item fetchItemById(int itemId) throws ItemNotFoundException {
        return ITEM_REPOSITORY.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, itemId)));
    }

    public List<Item> fetchAllItems() {
        return ITEM_REPOSITORY.findAll();
    }

    public List<Item> fetchAllAvailableItems() {
        return ITEM_REPOSITORY.findAllAvailableItems().get();
    }

    public List<Item> fetchItemsBySize(String size) {
        return ITEM_REPOSITORY.findItemBySize(size)
                .orElseThrow(() -> new InvalidParameterException(String.format(INVALID_SIZE_MESSAGE, size)));
    }

    public List<Item> findIAllAvailableItemBySizeAndProduct(String size, int productId) {
        List<Item> result = null;
        if(ITEM_REPOSITORY.findIAllAvailableItemBySizeAndProduct(size, productId).isPresent()) {
            result = ITEM_REPOSITORY.findIAllAvailableItemBySizeAndProduct(size, productId).get();
        }
        return result;
    }

    public Item fetchItemBySku(String sku) throws ItemNotFoundException {
        return ITEM_REPOSITORY.findBySku(sku)
                .orElseThrow(() -> new ItemNotFoundException(String.format(SKU_NOT_FOUND_MESSAGE, sku)));
    }

    public Item addItem(Item item, int productId) throws InvalidParameterException {
        final String SKU = item.getSku();
        final String SIZE = item.getSize();
        final String COLOUR = item.getColour();
        final double COST = item.getCost();

        if(!VALIDATION.validNonNullAndNonBlankParam(SKU)) {
            throw new InvalidParameterException(String.format(INVALID_SKU_MESSAGE, SKU));
        }
        else if(ITEM_REPOSITORY.existsItemBySku(SKU)) {
            throw new InvalidParameterException(String.format(DUPLICATE_SKU_MESSAGE, SKU));
        }

        if(!VALIDATION.validNonNullAndNonBlankParam(SIZE)) {
            throw new InvalidParameterException(String.format(INVALID_SIZE_MESSAGE, SIZE));
        }

        if(!VALIDATION.validNonNullAndNonBlankParam(COLOUR)) {
            throw new InvalidParameterException(String.format(INVALID_COLOUR_MESSAGE, COLOUR));
        }

        if(COST < 0) {
            throw new InvalidParameterException(String.format(INVALID_COST_MESSAGE, COST));
        }
        ITEM_REPOSITORY.save(item);
        assignProductToItem(item.getId(), productId);
        return item;
    }


    @Transactional
    public Boolean assignImageToItem(int itemId, MultipartFile file) throws InvalidParameterException, IOException {
        Item item = fetchItemById(itemId);
        List<ImageData> images =  item.getImages();
        ImageData image = IMAGE_DATA_SERVICE.uploadImage(file);
        images.add(image);
        image.setImageItem(item);
        return true;
    }

    @Transactional
    public Boolean updateImage(int itemId, MultipartFile file) throws IOException {
        Item item = fetchItemById(itemId);
        List<ImageData> images =  item.getImages();
        if(images.size() == 3) {
            images.forEach(image -> {
                image.setImageItem(null);
            });
            item.setImages(new ArrayList<>());
        }
        ImageData image = IMAGE_DATA_SERVICE.uploadImage(file);
        images.add(image);
        image.setImageItem(item);
        return true;
    }

    @Transactional
    public Item updateItem(
            int itemId,
            String sku,
            String size,
            String colour,
            Double cost,
            int quantity
    ) throws InvalidParameterException {
        final Item PERSISTED_ITEM = fetchItemById(itemId);

        if(sku != null) {
            if(!VALIDATION.validNonBlankParam(sku)) {
                throw new InvalidParameterException(String.format(INVALID_SKU_MESSAGE, sku));
            }
            if(ITEM_REPOSITORY.existsItemBySku(sku) && !PERSISTED_ITEM.getSku().equals(sku)) {
                throw new InvalidParameterException(String.format(DUPLICATE_SKU_MESSAGE, sku));
            }
            PERSISTED_ITEM.setSku(sku);
        }

        if(size != null) {
            if(!VALIDATION.validNonBlankParam(size)) {
                throw new InvalidParameterException(String.format(INVALID_SIZE_MESSAGE, size));
            }
            PERSISTED_ITEM.setSize(size);
        }

        if(colour != null) {
            if(!VALIDATION.validNonBlankParam(colour)) {
                throw new InvalidParameterException(String.format(INVALID_COLOUR_MESSAGE, colour));
            }
            PERSISTED_ITEM.setColour(colour);
        }

        if(cost != null) {
            if(cost <= 0) {
                throw new InvalidParameterException(String.format(INVALID_COST_MESSAGE, cost));
            }
            PERSISTED_ITEM.setCost(cost);
        }

        if(quantity > 0) {
            PERSISTED_ITEM.setQuantity(quantity);
        }

        return PERSISTED_ITEM;
    }

    // TODO: Improve this... its more like "remove items from a cart"
    public Boolean deleteItem(int itemId) throws ItemNotFoundException {
        Item item = this.fetchItemById(itemId);
        ITEM_REPOSITORY.delete(item);
        return true;
    }

    @Transactional
    public Cart addItemToCart(int itemId, int cartId) {
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
        return CART;
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

    public Boolean removeItemFromCart(List<Integer> itemIds, int cartId) {
        final Cart CART = CART_SERVICE.fetchCartById(cartId);
        final List<Item> ITEM_LIST = CART.getItems();
        CART.setItems(ITEM_LIST);
        itemIds.forEach(itemId -> {
            Item item = this.fetchItemById(itemId);
            item.getCarts().remove(CART);
            ITEM_LIST.remove(item);
            ITEM_REPOSITORY.save(item);
        });
        return true;
    }

    public Boolean addItemToOrder(List<Integer> itemIds, int orderId, int quantity) {
        ProductOrder ORDER = ORDER_SERVICE.fetchProductOrderById(orderId);
        List<Item> ITEM_LIST = ORDER.getItems();

        itemIds.forEach(itemId -> {
            Item ITEM = fetchItemById(itemId);
            ITEM_LIST.add(ITEM);
            ITEM.setOrder(ORDER);
            ITEM.setQuantity((ITEM.getQuantity() - quantity));
        });
        ORDER.setItems(ITEM_LIST);
        ORDER_SERVICE.save(ORDER);
        return true;
    }

    public ItemStats getItemStatsForProduct(int productId) {
        List<Integer> quantities = ITEM_REPOSITORY.findAvailableItemsByProduct(productId).get();
        int total = 0;
        for(int quantity : quantities) {
            total += quantity;
        }
        return new ItemStats(productId, total);
    }
}
