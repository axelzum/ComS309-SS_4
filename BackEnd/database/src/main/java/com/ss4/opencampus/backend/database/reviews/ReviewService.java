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

  public Iterable<Review> getReviews(Integer uspotId, String searchType)
  {
    return reviewRepository.findAllByUSpotUsID(uspotId, new Sort(Sort.Direction.ASC, "rId"));
  }

  public Optional<Review> getById(Integer uspotId, Integer rId)
  {
    return reviewRepository.findByRIdAndUSpotUsID(rId, uspotId);
  }

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
