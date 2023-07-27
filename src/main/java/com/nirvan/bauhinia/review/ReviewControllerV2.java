package com.nirvan.bauhinia.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v2/reviews")
@RequiredArgsConstructor
public class ReviewControllerV2 {
    private final ReviewServiceV2 REVIEW_SERVICE;

    /**
     * Provides the information regarding a specific review
     * */
    @GetMapping("/find")
    public ResponseEntity<Review> fetchReviewById(@RequestParam(name = "id") int reviewId) {
        return new ResponseEntity<>(REVIEW_SERVICE.fetchReviewById(reviewId), HttpStatus.OK);
    }

    /**
     * Provides all the reviews for a specific item
     * @param itemId id of the targeted item
     * */
    @GetMapping("/find/item")
    public ResponseEntity<List<Review>> fetchAllReviewsByItem(@RequestParam("itemId") int itemId) {
        return new ResponseEntity<>(REVIEW_SERVICE.fetchAllReviewsByItem(itemId), HttpStatus.OK);
    }

    /**
     * Adds a new review for a particular item
     * @param review review object
     * @param itemId the targeted item
     * */
    @PostMapping
    public ResponseEntity<Boolean> addReview(
            @RequestBody Review review,
            @RequestParam("itemId") int itemId
    ) {
        return new ResponseEntity<>(REVIEW_SERVICE.addReview(review, itemId), HttpStatus.CREATED);
    }

    /**
     * Updates an existing review
     * @param reviewId id of the targeted review
     * @param title the new title. Optional
     * @param content the new content. Optional
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateReview(
            @RequestParam("id") int reviewId,
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ) {
        return new ResponseEntity<>(REVIEW_SERVICE.updateReview(reviewId, title, content), HttpStatus.OK);
    }

    /**
     * Deletes an existing review
     * @param reviewId id of the targeted review
     * */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteReview(@RequestParam("id") int reviewId) {
        return new ResponseEntity<>(REVIEW_SERVICE.deleteReview(reviewId), HttpStatus.OK);
    }
}
