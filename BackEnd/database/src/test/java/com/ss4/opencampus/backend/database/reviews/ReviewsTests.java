package com.ss4.opencampus.backend.database.reviews;

import com.ss4.opencampus.backend.database.uspots.USpot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Axel Zumwalt
 * <p>
 * Test class for USpot Reviews. Makes sure that the ReviewController is setup properly and handles requests
 * appropiately.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReviewsTests
{
  private static Review rev1;
  private static Review rev2;

  private static USpot u1;
  private static USpot u2;

  @Mock
  private ReviewRepository reviewRepository;

  @InjectMocks
  private ReviewService reviewService;

  /**
   * Initialize fake Reviews and Students that created the Reviews before every test
   */
  @Before
  public void init()
  {
    u1 = new USpot();
    u1.setId(1);
    u1.setStudentId(1);
    u1.setUsName("LeBaron Bike Rack");
    u1.setUsRating(4.5);
    u1.setRatingTotal(9.0);
    u1.setRatingCount(2);
    u1.setUsImagePath(null);
    u1.setPicBytes(null);
    u1.setUsCategory("Bike Rack");
    u1.setUsLatit(42.00001);
    u1.setUsLongit(-93.10000);

    u2 = new USpot();
    u2.setId(2);
    u2.setStudentId(1);
    u2.setBuildingId(1);
    u2.setFloor("B");
    u2.setUsName("Secret Bathroom");
    u2.setUsRating(3.0);
    u2.setRatingTotal(3.0);
    u2.setRatingCount(1);
    u2.setUsImagePath(null);
    u2.setPicBytes(null);
    u2.setUsCategory("Bathroom");
    u2.setUsLatit(42.87654);
    u2.setUsLongit(-93.12345);

    rev1 = new Review();
    rev1.setId(1);
    rev1.setText("This is review 1");
    rev1.setuSpot(u1);

    rev2 = new Review();
    rev2.setId(2);
    rev2.setText("This is review 2");
    rev2.setuSpot(u1);


  }

  /**
   * Test to see if the Controller returns all of the correct Reviews given a USpot
   */
  @Test
  public void findAll()
  {
    Mockito.when(reviewRepository.findAllByUSpotUsID(u1.getId(), new Sort(Sort.Direction.ASC, "rId")))
            .thenReturn(Arrays.asList(rev1, rev2));
    Iterable<Review> rev = reviewService.getReviews(u1.getId(), "all");
    assertEquals(reviewRepository.findAllByUSpotUsID(u1.getId(), new Sort(Sort.Direction.ASC, "rId")), rev);

  }

  /**
   * Test to see if the Controller returns an empty list if given a USpot with no Reviews
   */
  @Test
  public void findStudentWithNoCMs()
  {
    Mockito.when(reviewRepository.findAllByUSpotUsID(u2.getId(), new Sort(Sort.Direction.ASC, "rId")))
            .thenReturn(Collections.emptyList());
    Iterable<Review> rev = reviewService.getReviews(u2.getId(), "all");
    assertEquals(reviewRepository.findAllByUSpotUsID(u2.getId(), new Sort(Sort.Direction.ASC, "rId")), rev);
  }

  /**
   * Test to see if the Controller returns a single Review if given a ReviewID and USpotID
   */
  @Test
  public void findByRIdAndUSID()
  {
    Mockito.when(reviewRepository.findByRIdAndUSpotUsID(rev2.getId(), u1.getId())).thenReturn(Optional.of(rev2));
    Optional<Review> rev = reviewService.getById(u1.getId(), rev2.getId());
    assertTrue(rev.isPresent());
    assertEquals(rev2, rev.get());
  }
}
