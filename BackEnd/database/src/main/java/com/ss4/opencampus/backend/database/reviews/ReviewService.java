package com.ss4.opencampus.backend.database.reviews;

import com.ss4.opencampus.backend.database.uspots.USpot;
import com.ss4.opencampus.backend.database.uspots.USpotRepository;
import com.ss4.opencampus.backend.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Service Class for Reviews
 */
@Service
public class ReviewService
{
  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private USpotRepository uSpotRepository;

  /**
   * Method that adds a Review to the DB. It is tied to an existing USpot
   *
   * @param uspotId
   *         USpot that the Review is for
   * @param review
   *         Review to be added
   *
   * @return Boolean of success or failure
   */
  public Boolean add(Integer uspotId, Review review)
  {
    try
    {
      USpot u = uSpotRepository.findById(uspotId).get();
      review.setuSpot(u);
      reviewRepository.save(review);
      WebSocketServer.onMessage(u.getId());
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Method to search DB for Reviews sorted by ID
   *
   * @param uspotId
   *         USpot to look at for Reviews
   * @param searchType
   *         way to organize Reviews
   *
   * @return Iterable list of Reviews
   */
  public Iterable<Review> getReviews(Integer uspotId, String searchType)
  {
    return reviewRepository.findAllByUSpotUsID(uspotId, new Sort(Sort.Direction.ASC, "rId"));
  }

  /**
   * Searches DB for a specific Review
   *
   * @param uspotId
   *         id of Student who made Review
   * @param rId
   *         id of Review to find
   *
   * @return Review obj
   */
  public Optional<Review> getById(Integer uspotId, Integer rId)
  {
    return reviewRepository.findByRIdAndUSpotUsID(rId, uspotId);
  }

  /**
   * Updates an existing Review with given info. Info not given is set to NULL
   *
   * @param review
   *         Info that Review will be updated with
   * @param uspotId
   *         id of Student who made Review
   * @param rId
   *         id of Review to be updated
   *
   * @return Boolean of success or failure
   */
  public Boolean put(Review review, Integer uspotId, Integer rId)
  {
    try
    {
      Review r = reviewRepository.findByRIdAndUSpotUsID(rId, uspotId).get();
      r.setText(review.getText());
      reviewRepository.save(r);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Updates Review with info given in map
   *
   * @param patch
   *         Map with info that Review will be updated with
   * @param uspotId
   *         id of Student who made the Review
   * @param rId
   *         id of the Review to be updated
   *
   * @return Boolean of success or failure
   */
  public Boolean patch(Map<String, Object> patch, Integer uspotId, Integer rId)
  {
    try
    {
      Review r = reviewRepository.findByRIdAndUSpotUsID(rId, uspotId).get();
      if (patch.containsKey("text"))
      {
        r.setText((String) patch.get("text"));
      }
      reviewRepository.save(r);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Finds the Review tied to the given USpot and removes the Review from the database
   *
   * @param rId
   *         id of Review to be deleted
   * @param uspotId
   *         id of USpot who made the route
   *
   * @return Boolean of success or failure to delete
   */
  public Boolean delete(Integer uspotId, Integer rId)
  {
    try
    {
      Review r = reviewRepository.findByRIdAndUSpotUsID(rId, uspotId).get();
      reviewRepository.delete(r);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

}
