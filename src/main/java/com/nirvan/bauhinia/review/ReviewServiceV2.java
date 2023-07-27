package com.nirvan.bauhinia.review;

import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ItemNotFoundException;
import com.nirvan.bauhinia.exception.ReviewNotFoundException;
import com.nirvan.bauhinia.item.Item;
import com.nirvan.bauhinia.item.ItemServiceV2;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceV2 {
    private final ReviewRepository REVIEW_REPOSITORY;
    private final ItemServiceV2 ITEM_SERVICE;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Review with the following id does not exist: %s";
    private static final String INVALID_TITLE_MESSAGE = "Title is invalid: %s";
    private static final String INVALID_CONTENT_MESSAGE = "Content is invalid: %s";

    public Review fetchReviewById(int reviewId) throws ReviewNotFoundException {
        return REVIEW_REPOSITORY.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, reviewId)));
    }

    public List<Review> fetchAllReviewsByItem(int itemId) {
        return ITEM_SERVICE.fetchItemById(itemId).getReviews();
    }

    public Boolean addReview(
            @NotNull Review review,
            @NotNull int itemId
    ) throws InvalidParameterException {
        final String TITLE = review.getTitle();
        final String CONTENT = review.getContent();
        //
        // Make sure the title is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(TITLE)) {
            throw new InvalidParameterException(String.format(INVALID_TITLE_MESSAGE, TITLE));
        }
        //
        // Make sure the content is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(CONTENT)) {
            throw new InvalidParameterException(String.format(INVALID_CONTENT_MESSAGE, CONTENT));
        }
        Item item = ITEM_SERVICE.fetchItemById(itemId);
        List<Review> reviewList = item.getReviews();
        reviewList.add(review);
        item.setReviews(reviewList);
        review.setItem(item);
        REVIEW_REPOSITORY.save(review);
        return true;
    }

    @Transactional
    public Boolean updateReview(
            int reviewId,
            String title,
            String content
    ) throws InvalidParameterException {
        final Review PERSISTED_REVIEW = fetchReviewById(reviewId);
        //
        // Make sure title is valid
        //
        if(title != null) {
            if(!VALIDATION.validNonBlankParam(title)) {
                throw new InvalidParameterException(String.format(INVALID_TITLE_MESSAGE, title));
            }
            PERSISTED_REVIEW.setTitle(title);
        }
        //
        // Make sure the content is valid
        //
        if(content != null) {
            if(!VALIDATION.validNonNullAndNonBlankParam(content)) {
                throw new InvalidParameterException(String.format(INVALID_CONTENT_MESSAGE, content));
            }
            PERSISTED_REVIEW.setContent(content);
        }
        REVIEW_REPOSITORY.save(PERSISTED_REVIEW);
        return true;
    }

    public Boolean deleteReview(int reviewId) {
        if(!REVIEW_REPOSITORY.existsById(reviewId)) {
            throw new ItemNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, reviewId));
        }
        REVIEW_REPOSITORY.deleteById(reviewId);
        return true;
    }
}
