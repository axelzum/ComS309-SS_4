package com.ss4.opencampus.backend.database.uspots;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Willis Knox
 * <p>
 * Collection of the implemented search methods for USpots
 */
public interface USpotRepository extends JpaRepository<USpot, Integer>
{
  /**
   * Finds a list of USpots in the selected category
   *
   * @param category
   *         category to search
   *
   * @return Iterable List of USpots
   */
  Iterable<USpot> findAllByUsCategory(String category);

  /**
   * Finds list of USpots with the given name
   *
   * @param name
   *         name to search for
   *
   * @return Iterable List of USpots
   */
  Iterable<USpot> findByUsName(String name);

  /**
   * Finds a list of USpots who's names start with the given parameter
   *
   * @param nameStart
   *         start of the USpot name
   *
   * @return Iterable List of USpots
   */
  Iterable<USpot> findAllByUsNameStartingWith(String nameStart);

  /**
   * Returns a list of USpots with ratings greater than equal to the given rating
   *
   * @param minRating
   *         Minimum rating to search for
   *
   * @return Iterable List of USpots
   */
  Iterable<USpot> findAllByUsRatingGreaterThanEqual(Double minRating);

  /**
   * Finds all USpots in a given Building on a given Floor
   *
   * @param buildingId
   *         id of building to look in
   * @param floor
   *         floor of building to look in
   *
   * @return Iterable List of USpots
   */
  Iterable<USpot> findAllByBuildingIdAndFloorLvl(Integer buildingId, String floor);
}
