package com.nirvan.bauhinia.config;

import com.nirvan.bauhinia.category.Category;
import com.nirvan.bauhinia.category.CategoryRepository;
import com.nirvan.bauhinia.imagedata.ImageData;
import com.nirvan.bauhinia.imagedata.ImageDataRepository;
import com.nirvan.bauhinia.item.Item;
import com.nirvan.bauhinia.item.ItemRepository;
import com.nirvan.bauhinia.product.Product;
import com.nirvan.bauhinia.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProductAndItemConfig {

    @Bean
    CommandLineRunner productAndItemConfigCommandLineRunner(ProductRepository productRepository, ItemRepository itemRepository, CategoryRepository categoryRepository) {
        return args -> {
            //
            // CATEGORIES
            //
            Category category1 = new Category("T-shirt");
            Category category2 = new Category("Gym wear");
            Category category3 = new Category("Men's wear");
            Category category4 = new Category("Women's wear");

            //
            // SAVE CATEGORIES
            //
            List<Category> categoriesToAdd = new ArrayList<>();
            categoriesToAdd.add(category1);
            categoriesToAdd.add(category2);
            categoriesToAdd.add(category3);
            categoriesToAdd.add(category4);

            categoriesToAdd.forEach(category -> {
                if(!categoryRepository.existsCategoryByName(category.getName())) {
                    categoryRepository.save(category);
                }
            });

            //
            // BEAR APPAREL POLO T-SHIRT
            //
            Product product1 = new Product(
                    "Bear Apparel",
                    "Polo t-shirt"
            );

            ImageData image1 = new ImageData(
                    "navyBluePolo1.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\navyBluePolo1.jpeg"
            );
            ImageData image2 = new ImageData(
                    "navyBluePolo2.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\navyBluePolo2.jpeg"
            );
            ImageData image3 = new ImageData(
                    "navyBluePolo3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\navyBluePolo3.jpeg"
            );
            List<ImageData> images1 = new ArrayList<>(List.of(image1, image2, image3));
            Item product1Item1 = new Item (
                    "221235",
                    "Medium",
                    "Navy blue",
                    7000,
                    images1,
                    product1,
                    5
            );
            images1.forEach(image -> image.setImageItem(product1Item1));

            ImageData image4 = new ImageData(
                    "greenPolo1.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\greenPolo1.jpeg"
            );
            ImageData image5 = new ImageData(
                    "greenPolo2.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\greenPolo2.jpeg"
            );
            ImageData image6 = new ImageData(
                    "greenPolo3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\greenPolo3.jpeg"
            );
            List<ImageData> images2 = new ArrayList<>(List.of(image4, image5, image6));
            Item product1Item2 = new Item (
                    "887263",
                    "Medium",
                    "Green",
                    7000,
                    images2,
                    product1,
                    5
            );
            images2.forEach(image -> image.setImageItem(product1Item2));

            ImageData image7 = new ImageData(
                    "grayPolo1.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\grayPolo1.jpeg"
            );
            ImageData image8 = new ImageData(
                    "grayPolo2.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\grayPolo2.jpeg"
            );
            ImageData image9 = new ImageData(
                    "grayPolo3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\grayPolo3.jpeg"
            );
            List<ImageData> images3 = new ArrayList<>(List.of(image7, image8, image9));
            Item product1Item3 = new Item (
                    "992812",
                    "Medium",
                    "Gray",
                    7000,
                    images3,
                    product1,
                    5
            );
            images3.forEach(image -> image.setImageItem(product1Item3));

            ImageData product1Image10 = new ImageData(
                    "pinkPolo1.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\pinkPolo1.jpeg"
            );
            ImageData product1Image11 = new ImageData(
                    "pinkPolo2.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\pinkPolo2.jpeg"
            );
            ImageData product1Image12 = new ImageData(
                    "pinkPolo3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\pinkPolo3.jpeg"
            );
            List<ImageData> product1Images4 = new ArrayList<>(List.of(product1Image10, product1Image11, product1Image12));
            Item product1Item4 = new Item (
                    "772873",
                    "Small",
                    "Pink",
                    12000,
                    product1Images4,
                    product1,
                    5
            );
            product1Images4.forEach(image -> image.setImageItem(product1Item4));

            ImageData product1Image13 = new ImageData(
                    "redPolo1.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\redPolo1.jpeg"
            );
            ImageData product1Image14 = new ImageData(
                    "redPolo2.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\redPolo2.jpeg"
            );
            ImageData product1Image15 = new ImageData(
                    "redPolo3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\redPolo3.jpeg"
            );
            List<ImageData> product1Images5 = new ArrayList<>(List.of(product1Image13, product1Image14, product1Image15));
            Item product1Item5 = new Item (
                    "129928",
                    "Small",
                    "Red",
                    12000,
                    product1Images5,
                    product1,
                    5
            );
            product1Images5.forEach(image -> image.setImageItem(product1Item5));

            ImageData product1Image16 = new ImageData(
                    "whitePolo31.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\whitePolo1.jpeg"
            );
            ImageData product1Image17 = new ImageData(
                    "whitePolo32.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\whitePolo2.jpeg"
            );
            ImageData product1Image18 = new ImageData(
                    "whitePolo3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\whitePolo3.jpeg"
            );
            List<ImageData> product1Images6 = new ArrayList<>(List.of(product1Image16, product1Image17, product1Image18));
            Item product1Item6 = new Item (
                    "331425",
                    "Small",
                    "White",
                    12000,
                    product1Images6,
                    product1,
                    5
            );
            product1Images6.forEach(image -> image.setImageItem(product1Item6));

            //
            // MANGO ALMOND DRESS
            //
            Product product2 = new Product(
                    "Mango",
                    "Almond dress"
            );

            ImageData product2Image1 = new ImageData(
                    "orangeDress1.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\orangeDress1.jpeg"
            );
            ImageData product2Image2 = new ImageData(
                    "orangeDress2.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\orangeDress2.jpeg"
            );
            ImageData product2Image3 = new ImageData(
                    "orangeDress3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\orangeDress3.jpeg"
            );
            List<ImageData> product2Images1 = new ArrayList<>(List.of(product2Image1, product2Image2, product2Image3));
            Item product2Item1 = new Item (
                    "928343",
                    "Small",
                    "Orange",
                    28000,
                    product2Images1,
                    product2,
                    5
            );
            product2Images1.forEach(image -> image.setImageItem(product2Item1));

            ImageData product2Image4 = new ImageData(
                    "blackDress1.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\blackDress1.jpeg"
            );
            ImageData product2Image5 = new ImageData(
                    "blackDress2.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\blackDress2.jpeg"
            );
            ImageData product2Image6 = new ImageData(
                    "blackDress3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\blackDress3.jpeg"
            );
            List<ImageData> product2Images2 = new ArrayList<>(List.of(product2Image4, product2Image5, product2Image6));
            Item product2Item2 = new Item (
                    "413324",
                    "Small",
                    "Black",
                    28000,
                    product2Images2,
                    product2,
                    5
            );
            product2Images2.forEach(image -> image.setImageItem(product2Item2));

            //
            // Striped linen shirt
            //
            Product product3 = new Product(
                "Polo Ralph Lauren",
                "Striped Linen Shirt"
            );

            ImageData product3Image1 = new ImageData(
                    "blueShirt1.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\blueShirt1.jpeg"
            );
            ImageData product3Image2 = new ImageData(
                    "blueShirt2.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\blueShirt2.jpeg"
            );
            ImageData product3Image3 = new ImageData(
                    "blueShirt3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\blueShirt3.jpeg"
            );
            List<ImageData> product3Images1 = new ArrayList<>(List.of(product3Image1, product3Image2, product3Image3));
            Item product3Item1 = new Item (
                    "613617",
                    "Medium",
                    "Blue",
                    28000,
                    product3Images1,
                    product3,
                    5
            );
            product3Images1.forEach(image -> image.setImageItem(product3Item1));

            ImageData product3Image4 = new ImageData(
                    "whiteShirt1.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\whiteShirt1.jpeg"
            );
            ImageData product3Image5 = new ImageData(
                    "whiteShirt2.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\whiteShirt2.jpeg"
            );
            ImageData product3Image6 = new ImageData(
                    "whiteShirt3.jpeg", "image/jpeg",
                    "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\whiteShirt3.jpeg"
            );
            List<ImageData> product3Images2 = new ArrayList<>(List.of(product3Image4, product3Image5, product3Image6));
            Item product3Item2 = new Item (
                    "132765",
                    "Medium",
                    "White",
                    28000,
                    product3Images2,
                    product3,
                    5
            );
            product3Images2.forEach(image -> image.setImageItem(product3Item2));

            //
            // POLO RALPH LAUREN CHINOS
            //
            Product product4 = new Product(
                    "Polo Ralph Lauren",
                    "Chino"
            );

            List<ImageData> product4Images1 = new ArrayList<>(List.of(
                    new ImageData(
                            "aviatorNavyChino1.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\aviatorNavyChino1.jpeg"
                    ),
                    new ImageData(
                            "aviatorNavyChino2.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\aviatorNavyChino2.jpeg"
                    ),
                    new ImageData(
                            "aviatorNavyChino3.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\aviatorNavyChino3.jpeg"
                    )
            ));
            Item product4Item1 = new Item (
                    "113324",
                    "Medium",
                    "Montana Khaki",
                    31000,
                    product4Images1,
                    product4,
                    5
            );
            product4Images1.forEach(image -> image.setImageItem(product4Item1));

            List<ImageData> product4Images2 = new ArrayList<>(List.of(
                new ImageData(
                        "montanaKhakiChino1.jpeg", "image/jpeg",
                        "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\montanaKhakiChino1.jpeg"
                ),
                new ImageData(
                        "montanaKhakiChino2.jpeg", "image/jpeg",
                        "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\montanaKhakiChino2.jpeg"
                ),
                new ImageData(
                        "montanaKhakiChino3.jpeg", "image/jpeg",
                        "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\montanaKhakiChino3.jpeg"
                )
            ));
            Item product4Item2 = new Item (
                    "882763",
                    "Medium",
                    "Aviator Navy",
                    39000,
                    product4Images2,
                    product4,
                    5
            );
            product4Images2.forEach(image -> image.setImageItem(product4Item2));

            //
            // POLO RALPH LAUREN TANK TOP
            //
            Product product5 = new Product(
                    "Polo Ralph Lauren",
                    "Tank Top"
            );

            List<ImageData> product5Images1 = new ArrayList<>(List.of(
                    new ImageData(
                            "violetWhiteTankTop1.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\violetWhiteTankTop1.jpeg"
                    ),
                    new ImageData(
                            "violetWhiteTankTop2.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\violetWhiteTankTop2.jpeg"
                    ),
                    new ImageData(
                            "violetWhiteTankTop3.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\violetWhiteTankTop3.jpeg"
                    )
            ));
            Item product5Item1 = new Item (
                    "772624",
                    "Small",
                    "Violet Floral",
                    42000,
                    product5Images1,
                    product5,
                    5
            );
            product5Images1.forEach(image -> image.setImageItem(product5Item1));

            List<ImageData> product5Images2 = new ArrayList<>(List.of(
                    new ImageData(
                            "beigeTankTop1.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\beigeTankTop1.jpeg"
                    ),
                    new ImageData(
                            "beigeTankTop2.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\beigeTankTop2.jpeg"
                    ),
                    new ImageData(
                            "beigeTankTop3.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\beigeTankTop3.jpeg"
                    )
            ));
            Item product5Item2 = new Item (
                    "998261",
                    "Small",
                    "Basic Sand",
                    38745,
                    product5Images2,
                    product5,
                    5
            );
            product5Images2.forEach(image -> image.setImageItem(product5Item2));

            //
            // POLO RALPH LAUREN WOMEN'S STRIPED SHIRT
            //
            Product product6 = new Product(
                    "Polo Ralph Lauren",
                    "Relaxed Fit Striped Cotton Shirt"
            );

            List<ImageData> product6Images1 = new ArrayList<>(List.of(
                    new ImageData(
                            "greenStripedShirt1.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\greenStripedShirt1.jpeg"
                    ),
                    new ImageData(
                            "greenStripedShirt2.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\greenStripedShirt2.jpeg"
                    ),
                    new ImageData(
                            "greenStripedShirt3.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\greenStripedShirt3.jpeg"
                    )
            ));
            Item product6Item1 = new Item (
                    "882735",
                    "Small",
                    "Green/White Stripe",
                    42000,
                    product6Images1,
                    product6,
                    5
            );
            product6Images1.forEach(image -> image.setImageItem(product6Item1));


            List<ImageData> product6Images2 = new ArrayList<>(List.of(
                    new ImageData(
                            "navyWhiteStripeShirt1.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\navyWhiteStripeShirt1.jpeg"
                    ),
                    new ImageData(
                            "navyWhiteStripeShirt2.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\navyWhiteStripeShirt2.jpeg"
                    ),
                    new ImageData(
                            "navyWhiteStripeShirt3.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\navyWhiteStripeShirt3.jpeg"
                    )
            ));
            Item product6Item2 = new Item (
                    "882112",
                    "Small",
                    "Navy/White Stripe",
                    43200,
                    product6Images2,
                    product6,
                    5
            );
            product6Images2.forEach(image -> image.setImageItem(product6Item2));



            //
            // POLO RALPH LAUREN WOMEN'S CROPPED SHIRT
            //
            Product product7 = new Product(
                    "Polo Ralph Lauren",
                    "Wide Cropped Shirt"
            );

            List<ImageData> product7Images1 = new ArrayList<>(List.of(
                    new ImageData(
                            "lightGreenShirt1.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\lightGreenShirt1.jpeg"
                    ),
                    new ImageData(
                            "lightGreenShirt2.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\lightGreenShirt2.jpeg"
                    ),
                    new ImageData(
                            "lightGreenShirt3.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\lightGreenShirt3.jpeg"
                    )
            ));
            Item product7Item1 = new Item (
                    "552436",
                    "Small",
                    "Chambray",
                    54699,
                    product7Images1,
                    product7,
                    6
            );
            product7Images1.forEach(image -> image.setImageItem(product7Item1));

            List<ImageData> product7Images2 = new ArrayList<>(List.of(
                    new ImageData(
                            "bathPinkShirt1.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\bathPinkShirt1.jpeg"
                    ),
                    new ImageData(
                            "bathPinkShirt2.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\bathPinkShirt2.jpeg"
                    ),
                    new ImageData(
                            "bathPinkShirt3.jpeg", "image/jpeg",
                            "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\bathPinkShirt3.jpeg"
                    )
            ));
            Item product7Item2 = new Item (
                    "552152",
                    "Small",
                    "Bath pink",
                    54699,
                    product7Images2,
                    product7,
                    3
            );
            product7Images2.forEach(image -> image.setImageItem(product7Item2));

            //
            // DATABASE MANIPULATION
            //
            List<Product> productsToAdd = new ArrayList<>();
            productsToAdd.add(product1);
            productsToAdd.add(product2);
            productsToAdd.add(product3);
            productsToAdd.add(product4);
            productsToAdd.add(product5);
            productsToAdd.add(product6);
            productsToAdd.add(product7);

            productsToAdd.forEach(product -> {
                if(!(productRepository.existsByProductName(product.getProductName()) && productRepository.existsByManufacturer(product.getManufacturer()))) {
                    productRepository.save(product);
                }
            });

            List<Item> itemsToAdd = new ArrayList<>();
            itemsToAdd.add(product1Item1);
            itemsToAdd.add(product1Item2);
            itemsToAdd.add(product1Item3);
            itemsToAdd.add(product1Item4);
            itemsToAdd.add(product1Item5);
            itemsToAdd.add(product1Item6);
            itemsToAdd.add(product2Item1);
            itemsToAdd.add(product2Item2);
            itemsToAdd.add(product3Item1);
            itemsToAdd.add(product3Item2);
            itemsToAdd.add(product4Item1);
            itemsToAdd.add(product4Item2);
            itemsToAdd.add(product5Item1);
            itemsToAdd.add(product5Item2);
            itemsToAdd.add(product6Item1);
            itemsToAdd.add(product6Item2);
            itemsToAdd.add(product7Item1);
            itemsToAdd.add(product7Item2);

            itemsToAdd.forEach(item -> {
                if(!itemRepository.existsItemBySku(item.getSku())) {
                    itemRepository.save(item);
                }
            });
        };
    }
}
