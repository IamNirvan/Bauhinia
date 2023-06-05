package com.nirvan.bauhinia.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/reviews")
public class ReviewController {

    private final ReviewService REVIEW_SERVICE;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        REVIEW_SERVICE = reviewService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Review>> fetchAll() {
        return new ResponseEntity<>(REVIEW_SERVICE.select(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Review> fetchReviewById(@RequestParam(name = "reviewId") int id) {
        return new ResponseEntity<>(REVIEW_SERVICE.selectById(id), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        return new ResponseEntity<>(REVIEW_SERVICE.insert(review), HttpStatus.CREATED);
    }

    @PostMapping("/insert/item")
    public ResponseEntity<Review> addReview(
            @RequestBody Review review,
            @RequestParam(name = "itemId") int itemId
    ) {
        return new ResponseEntity<>(REVIEW_SERVICE.insert(review, itemId), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Review> updateReview(@RequestBody Review updatedReview) {
        return new ResponseEntity<>(REVIEW_SERVICE.update(updatedReview), HttpStatus.OK);
    }

    @PutMapping("/{reviewId}/Item/{itemId}")
    public ResponseEntity<Review> assignReviewToItem(
            @PathVariable("reviewId") int reviewId,
            @PathVariable("itemId") int itemId) {
        return new ResponseEntity<>(REVIEW_SERVICE.assignReviewToItem(reviewId, itemId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") int reviewId) {
        REVIEW_SERVICE.delete(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
