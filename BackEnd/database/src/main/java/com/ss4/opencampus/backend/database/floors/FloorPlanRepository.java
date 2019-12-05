package com.ss4.opencampus.backend.database.floors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Repository for the FloorPlans. Has methods that implement ways to search for FloorPlans.
 */
public interface FloorPlanRepository extends JpaRepository<FloorPlan, Integer>
{
  /**
   * Returns all FloorPlans in a Building. Sorts them by level
   *
   * @param buildingId
   *         id of Building to look in
   * @param sort
   *         Way to sort the FloorPlans
   *
   * @return Iterable list of FloorPlans
   */
  Iterable<FloorPlan> findAllByBuildingId(Integer buildingId, Sort sort);

  /**
   * Returns a specific FloorPlan. Would be used when the Client clicks on a specific FloorPlan
   *
   * @param fpId
   *         id of the FloorPlan to get
   * @param buildingId
   *         id of the Building the FloorPlan is in
   *
   * @return A single FloorPlan
   */
  Optional<FloorPlan> findByFpIdAndBuildingId(Integer fpId, Integer buildingId);
  // don't really see a reason to search floors any other way...
}
