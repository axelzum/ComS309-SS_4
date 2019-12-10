package com.ss4.opencampus.backend.database.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Controller that handles all requests to Reviews. Less options for reviews because they only have text that needs to
 * be displayed to the end users.
 */
@RestController
public class ReviewController
{
  @Autowired
  private ReviewService reviewService;

  /**
   * Handles POST Requests. Will save a new Review given a particular USpot and Review JSON data to the database.
   *
   * @param uspotId
   *         id of USpot that the Review is for
   * @param review
   *         JSON data that contains information about the review
   *
   * @return JSON formatted response letting users know of success or failure
   */
  @PostMapping(path = "/uspots/{uspotId}/reviews")
  public @ResponseBody
  Map<String, Boolean> addReview(@PathVariable(value = "uspotId") Integer uspotId, @RequestBody Review review)
  {
    return Collections.singletonMap("response", reviewService.add(uspotId, review));
  }

  /**
   * GET mapping that returns all Reviews for a given USpot. This will sort the reviews by ID, which is a pseudo date
   * sort because the IDs autoincrement.
   *
   * @param uspotId
   *         id of USpot to find the Reviews of
   * @param searchType
   *         Way to search for the Reviews. Only one search currently, so it doesn't matter at the moment
   *
   * @return Iterable List of Reviews
   */
  @GetMapping(path = "/uspots/{uspotId}/reviews/{searchType}")
  public @ResponseBody
  Iterable<Review> getReviews(@PathVariable(value = "uspotId") Integer uspotId,
                              @PathVariable(value = "searchType") String searchType)
  {
    return reviewService.getReviews(uspotId, searchType);
  }

  /**
   * GET Request that returns only one Review
   *
   * @param uspotsId
   *         id of USpot that the Review is for
   * @param rId
   *         id of Review to get
   *
   * @return A single Review Object (in JSON)
   */
  @GetMapping(path = "/uspots/{uspotsId}/reviews/id/{id}")
  public @ResponseBody
  Optional<Review> getById(@PathVariable(value = "uspotsId") Integer uspotsId, @PathVariable(value = "id") Integer rId)
  {
    return reviewService.getById(uspotsId, rId);
  }

  /**
   * Updates a review completely. Since the only thing the Client can really see is the Text, it only updates the text.
   *
   * @param review
   *         New Review object information that will be used to update the existing Review
   * @param uspotsId
   *         id of USpot that the Review is in
   * @param rId
   *         id of the Review that needs to be updated
   *
   * @return JSON response telling Frontend of success or failure
   */
  @PutMapping(path = "/uspots/{uspotsId}/reviews/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateReview(@RequestBody Review review,
                                    @PathVariable(value = "uspotsId") Integer uspotsId,
                                    @PathVariable(value = "id") Integer rId)
  {
    return Collections.singletonMap("response", reviewService.put(review, uspotsId, rId));
  }

  /**
   * PATCH mapping that will only update a part of a Review. Does not expect an entire Review object of information from
   * the Frontend.
   *
   * @param patch
   *         JSON formatted key and value that will be updated. Key will be which part of the Review needs updated and
   *         value will be what it will be updated with
   * @param uspotsId
   *         id of USpot that the Review is for
   * @param rId
   *         id of Review to be updated
   *
   * @return JSON response telling Frontend of success or failure
   */
  @PatchMapping(path = "/uspots/{uspotsId}/reviews/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchReview(@RequestBody Map<String, Object> patch,
                                   @PathVariable(value = "uspotsId") Integer uspotsId,
                                   @PathVariable(value = "id") Integer rId)
  {
    return Collections.singletonMap("response", reviewService.patch(patch, uspotsId, rId));
  }

  /**
   * DELETE mapping. Deletes a single Review from the database.
   *
   * @param uspotsId
   *         id of USpot the Review is (or should I say was) for
   * @param rId
   *         id of Review to be deleted
   *
   * @return JSON response telling Frontend of success or failure
   */
  @DeleteMapping(path = "/uspots/{uspotsId}/reviews/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteReview(@PathVariable(value = "uspotsId") Integer uspotsId,
                                    @PathVariable(value = "id") Integer rId)
  {
    return Collections.singletonMap("response", reviewService.delete(uspotsId, rId));
  }
}

