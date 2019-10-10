package com.ss4.opencampus.backend.database.uspots;

import org.springframework.data.jpa.repository.JpaRepository;

public interface USpotRepository extends JpaRepository<USpot, Integer>, USpotRepositorySaveImage
{
  Iterable<USpot> findAllByUsCategory(String category);
  Iterable<USpot> findByUsName(String name);
  Iterable<USpot> findAllByUsNameStartingWith(String nameStart);
  Iterable<USpot> findAllByUsRatingGreaterThanEqual(Double minRating);
  //TODO: add more useful searches
}
