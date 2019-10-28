package com.ss4.opencampus.backend.database.buildings.floors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Willis Knox
 */
public interface FloorPlanRepository extends JpaRepository<FloorPlan, Integer>
{
  Iterable<FloorPlan> findAllByBuildingId(Integer buildingId, Sort sort);
  Optional<FloorPlan> findByFpIdAndBuildingId(Integer fpId, Integer buildingId);
  // don't really see a reason to search floors any other way...
}
