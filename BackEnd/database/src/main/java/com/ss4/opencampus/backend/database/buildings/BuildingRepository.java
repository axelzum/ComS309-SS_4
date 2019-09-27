package com.ss4.opencampus.backend.database.buildings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Willis Knox
 * <p>
 * Repository for different ways to Query the MySQL Buildings table
 * </p>
 */
public interface BuildingRepository extends JpaRepository<Building, Integer>
{
  Building findByAddress(String address);

  Building findByBuildingName(String name);

  Building findByAbbreviation(String abbrev);

  Iterable<Building> findAllByBuildingNameStartingWith(String startOfName);

  Iterable<Building> findAllByAbbreviationStartingWith(String start);


  // TODO: Implement this search
//  @Query(value = "SELECT b FROM Buildings b WHERE b.Latitude = :latit AND b.Longitude = :longit")
//  Building findByLatitAndLongit(@Param("latit") Double latit, @Param("longit") Double longit);
}
