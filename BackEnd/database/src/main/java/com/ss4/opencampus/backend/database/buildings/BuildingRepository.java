package com.ss4.opencampus.backend.database.buildings;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Willis Knox
 * <p>
 * Repository for different ways to Query the MySQL Buildings table
 */
public interface BuildingRepository extends JpaRepository<Building, Integer>
{
  Iterable<Building> findByAddress(String address);

  Iterable<Building> findByBuildingName(String name);

  Iterable<Building> findByAbbreviation(String abbrev);

  Iterable<Building> findAllByBuildingNameStartingWith(String startOfName);

  Iterable<Building> findAllByAbbreviationStartingWith(String start);

  Iterable<Building> findByLatitAndLongit(Double latit, Double longit);
}
