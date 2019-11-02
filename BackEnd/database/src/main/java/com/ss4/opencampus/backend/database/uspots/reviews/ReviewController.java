package com.ss4.opencampus.backend.database.uspots.reviews;

import com.ss4.opencampus.backend.database.uspots.USpot;
import com.ss4.opencampus.backend.database.uspots.USpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class ReviewController
{

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private USpotRepository uSpotRepository;

  @PostMapping(path = "/uspots/{uspotId}/reviews")
  public @ResponseBody
  Map<String, Boolean> addReview(@PathVariable(value = "uspotId") Integer uspotId, @RequestBody Review review)
  {
    try
    {
      USpot u = uSpotRepository.findById(uspotId).get();
      review.setuSpot(u);
      reviewRepository.save(review);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  @GetMapping(path = "/uspots/{uspotId}/reviews/{searchType}")
  public @ResponseBody
  Iterable<Review> getReviews(@PathVariable(value = "uspotId") Integer uspotId,
                              @PathVariable(value = "searchType") String searchType)
  {
    return reviewRepository.findAllByUSpotUsID(uspotId, new Sort(Sort.Direction.ASC, "rId"));
  }

  @GetMapping(path = "/uspots/{uspotsId}/reviews/id/{id}")
  public @ResponseBody
  Optional<Review> getById(@PathVariable(value = "uspotsId") Integer uspotsId, @PathVariable(value = "id") Integer rId)
  {
    return reviewRepository.findByRIdAndUSpotUsID(rId, uspotsId);
  }

  @PutMapping(path = "/uspots/{uspotsId}/reviews/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateReview(@RequestBody Review review,
                                    @PathVariable(value = "uspotsId") Integer uspotsId,
                                    @PathVariable(value = "id") Integer rId)
  {
    try
    {
      Review r = reviewRepository.findByRIdAndUSpotUsID(rId, uspotsId).get();
      r.setText(review.getText());
      reviewRepository.save(r);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  @PatchMapping(path = "/uspots/{uspotsId}/reviews/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchReview(@RequestBody Map<String, Object> patch,
                                   @PathVariable(value = "uspotsId") Integer uspotsId,
                                   @PathVariable(value = "id") Integer rId)
  {
    try
    {
      Review r = reviewRepository.findByRIdAndUSpotUsID(rId, uspotsId).get();
      if (patch.containsKey("text"))
      {
        r.setText((String) patch.get("text"));
      }
      reviewRepository.save(r);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  @DeleteMapping(path = "/uspots/{uspotsId}/reviews/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteReview(@PathVariable(value = "uspotsId") Integer uspotsId,
                                    @PathVariable(value = "id") Integer rId)
  {
    try
    {
      Review r = reviewRepository.findByRIdAndUSpotUsID(rId, uspotsId).get();
      reviewRepository.delete(r);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }
}

