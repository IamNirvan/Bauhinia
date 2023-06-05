package com.nirvan.bauhinia.review;

import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ItemNotFoundException;
import com.nirvan.bauhinia.exception.ReviewNotFoundException;
import com.nirvan.bauhinia.item.Item;
import com.nirvan.bauhinia.item.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReviewService {

    private final ReviewRepository REVIEW_REPOSITORY;
    private final ItemRepository ITEM_REPOSITORY;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ItemRepository itemRepository) {
        REVIEW_REPOSITORY = reviewRepository;
        ITEM_REPOSITORY = itemRepository;
    }


    private Review getReview(int id) {
        return REVIEW_REPOSITORY.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id (" + id + ") does not exist"));
    }

    public List<Review> select() {
        return REVIEW_REPOSITORY.findAll();
    }

    public Review selectById(int id) {
        return getReview(id);
    }

    public Review insert(Review review) {
        // Make sure the title is valid
        if(review.getTitle().length() == 0) {
            throw new InvalidParameterException("Title (" + review.getTitle() + ") is invalid");
        }

        // Make sure the content is valid
        if(review.getContent().length() == 0) {
            throw new InvalidParameterException("Content (" + review.getContent() + ") is invalid");
        }

        // Make sure the selected item is valid.
        // If an item was provided, set the reviews for that item
        if(review.getItem() != null) {
            if(!ITEM_REPOSITORY.existsById(review.getItem().getId())) {
                throw new ItemNotFoundException("Item with id (" + review.getItem().getId() + ") does not exist");
            }

            int itemId = review.getItem().getId();
            Item item = ITEM_REPOSITORY.findById(itemId)
                    .orElseThrow(() -> new ItemNotFoundException("Item with id (" + itemId + ") does not exist"));

            List<Review> reviews = item.getReviews();
            reviews.add(review);
            item.setReviews(reviews);
            review.setItem(item);
            ITEM_REPOSITORY.save(item);
        }

        REVIEW_REPOSITORY.save(review);
        return review;
    }

    public Review insert(Review review, int itemId) {
        // Make sure the title is valid
        if(review.getTitle().length() == 0) {
            throw new InvalidParameterException("Title (" + review.getTitle() + ") is invalid");
        }

        // Make sure the content is valid
        if(review.getContent().length() == 0) {
            throw new InvalidParameterException("Content (" + review.getContent() + ") is invalid");
        }

        // Assign the review to the item
        Item item = ITEM_REPOSITORY.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item with id (" + itemId + ") does not exist"));

        List<Review> reviews = item.getReviews();
        reviews.add(review);
        item.setReviews(reviews);
        review.setItem(item);
        ITEM_REPOSITORY.save(item);
        REVIEW_REPOSITORY.save(review);

        return review;
    }

    @Transactional
    public Review update(Review updatedReview) {
        Review persistedReview = getReview(updatedReview.getId());

        // Make sure title is valid
        if(updatedReview.getTitle().length() != 0) {
            if(!Objects.equals(persistedReview.getTitle(), updatedReview.getTitle())) {
                persistedReview.setTitle(updatedReview.getTitle());
            }
        }
        else {
            throw new InvalidParameterException("Title (" + updatedReview.getTitle() + ") is invalid");
        }

        // Make sure the content is valid
        if(updatedReview.getContent().length() != 0) {
            if(!Objects.equals(persistedReview.getContent(), updatedReview.getContent())) {
                persistedReview.setContent(updatedReview.getContent());
            }
        }
        else {
            throw new InvalidParameterException("Content (" + updatedReview.getContent() + ") is invalid");
        }

        // Make sure the content is valid
        if(updatedReview.getItem() != null) {
            if(!Objects.equals(persistedReview.getItem().getId(), updatedReview.getItem().getId())) {
                persistedReview.setItem(updatedReview.getItem());
            }
        }
        else {
            throw new ItemNotFoundException("Item cannot be null");
        }

        return persistedReview;
    }

    @Transactional
    public Review assignReviewToItem(int reviewId, int itemId) {
        Review review = getReview(reviewId);
        Item item = ITEM_REPOSITORY.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item with id (" + itemId + ") does not exist"));

        List<Review> reviews = item.getReviews();
        reviews.add(review);
        item.setReviews(reviews);
        review.setItem(item);

        ITEM_REPOSITORY.save(item);
        REVIEW_REPOSITORY.save(review);
        return review;
    }

    public void delete(int reviewId) {
        if(!REVIEW_REPOSITORY.existsById(reviewId)) {
            throw new ItemNotFoundException("Review with id (" + reviewId + ") does not exist");
        }
        REVIEW_REPOSITORY.deleteById(reviewId);
    }


}
