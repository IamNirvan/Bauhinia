package com.nirvan.bauhinia.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v2/reviews")
@RequiredArgsConstructor
@CrossOrigin
public class ReviewControllerV2 {
    private final ReviewServiceV2 REVIEW_SERVICE;

    /**
     * Provides the information regarding a specific review
     * */
    @GetMapping("/find/{id}")
    public ResponseEntity<Review> fetchReviewById(@PathVariable("id") int reviewId) {
        return new ResponseEntity<>(REVIEW_SERVICE.fetchReviewById(reviewId), HttpStatus.OK);
    }

    /**
     * Provides all the reviews for a specific item
     * @param itemId id of the targeted item
     * */
    @GetMapping("/find/item/id/{itemId}")
    public ResponseEntity<List<Review>> fetchAllReviewsByItem(@PathVariable("itemId") int itemId) {
        return new ResponseEntity<>(REVIEW_SERVICE.fetchAllReviewsByItemId(itemId), HttpStatus.OK);
    }

    /**
     * Provides all the reviews for a specific item based on the item's SKU
     * @param sku SKU of the targeted item
     * */
    @GetMapping("/find/item/sku/{sku}")
    public ResponseEntity<List<Review>> fetchAllReviewsByItem(@PathVariable("sku") String sku) {
        return new ResponseEntity<>(REVIEW_SERVICE.fetchAllReviewsByItemSku(sku), HttpStatus.OK);
    }

    /**
     * Adds a new review for a particular item
     * @param review review object
     * @param itemId the targeted item
     * */
    @PostMapping("/{itemId}")
    public ResponseEntity<Boolean> addReview(
            @RequestBody Review review,
            @PathVariable("itemId") int itemId
    ) {
        return new ResponseEntity<>(REVIEW_SERVICE.addReview(review, itemId), HttpStatus.CREATED);
    }

    /**
     * Updates an existing review
     * @param reviewId id of the targeted review
     * @param title the new title. Optional
     * @param content the new content. Optional
     * */
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateReview(
            @PathVariable("id") int reviewId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "rating" ,required = false) String rating
    ) {
        return new ResponseEntity<>(REVIEW_SERVICE.updateReview(reviewId, title, content, rating), HttpStatus.OK);
    }

    /**
     * Deletes an existing review
     * @param reviewId id of the targeted review
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable("id") int reviewId) {
        return new ResponseEntity<>(REVIEW_SERVICE.deleteReview(reviewId), HttpStatus.OK);
    }
}
