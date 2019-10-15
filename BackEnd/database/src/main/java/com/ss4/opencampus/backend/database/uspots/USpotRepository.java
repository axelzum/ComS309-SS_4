package com.ss4.opencampus.backend.database.uspots;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Willis Knox
 */
public interface USpotRepository extends JpaRepository<USpot, Integer>
{
  Iterable<USpot> findAllByUsCategory(String category);

  Iterable<USpot> findByUsName(String name);

  Iterable<USpot> findAllByUsNameStartingWith(String nameStart);

  Iterable<USpot> findAllByUsRatingGreaterThanEqual(Double minRating);
  //TODO: add more useful searches
}
