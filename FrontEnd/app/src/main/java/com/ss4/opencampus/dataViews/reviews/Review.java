package com.ss4.opencampus.dataViews.reviews;

import java.io.Serializable;

    /**
     * @author Morgan Smith
     * Review class to store Review object data
     * Getter and setter methods for Review properties
     **/
    public class Review implements Serializable {

        public String reviewTitle;

        public String reviewDetails;

        /**
         * Constructs a Review with nothing given
         */
        public Review() {
        }

        /**
         * Constructs a Review with the given parameters
         * @param reviewTitle title of Review
         * @param reviewDetails details of Review
         */
        public Review(String reviewTitle, String reviewDetails) {
            this.reviewTitle = reviewTitle;
            this.reviewTitle = reviewDetails;
        }

        /**
         * Gets Title of the Review
         * @return reviewTitle
         */
        public String getReviewTitle() { return reviewTitle; }

        /**
         * Gets the Details of the Review
         * @return reviewDetails
         */
        public String getReviewDetails() {
            return reviewDetails;
        }

        /**
         * Sets the Title of the Review to a new value
         * @param reviewTitle new id for the Review
         */
        public void setReviewTitle(String reviewTitle) {
            this.reviewTitle = reviewTitle;
        }

        /**
         * Sets the Details of the Review to a new value
         * @param reviewDetails new id for the Review
         */
        public void setReviewDetails(String reviewDetails) {
            this.reviewDetails = reviewDetails;
    }
}
