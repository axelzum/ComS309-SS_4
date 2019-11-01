package com.ss4.opencampus.backend.database.buildings;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Willis Knox
 * <p>
 * Repository for different ways to Query the MySQL Buildings table
 */
public interface BuildingRepository extends JpaRepository<Building, Integer>
{
  /**
   * Finds a specific Building by the given address.
   *
   * @param address
   *         Address of Building to find
   *
   * @return Iterable List with 0 or 1 Buildings.
   */
  Iterable<Building> findByAddress(String address);

  /**
   * Finds a specific Building with the given name
   *
   * @param name
   *         Name of the Building to find
   *
   * @return Iterable List with 0 or 1 Buildings.
   */
  Iterable<Building> findByBuildingName(String name);

  /**
   * Finds a specific Building with the given abbreviation
   *
   * @param abbrev
   *         Abbreviation of the Building to find
   *
   * @return Iterable List with 0 or 1 Buildings.
   */
  Iterable<Building> findByAbbreviation(String abbrev);

  /**
   * Returns a list of Buildings where their names start with the given parameter
   *
   * @param startOfName
   *         Start of the Building(s) name
   *
   * @return Iterable List of Buildings who's names start with the given parameter
   */
  Iterable<Building> findAllByBuildingNameStartingWith(String startOfName);

  /**
   * Returns a list of Buildings where their abbreviation starts with the given parameter
   *
   * @param start
   *         Start of the Building(s) abbreviation
   *
   * @return Iterable List of Buildings who's abbreviations start with the given parameter
   */
  Iterable<Building> findAllByAbbreviationStartingWith(String start);

  /**
   * Finds a Building at the given location
   *
   * @param latit
   *         latitude of Building
   * @param longit
   *         longitude of Building
   *
   * @return Iterable List with 0 or 1 Buildings.
   */
  Iterable<Building> findByLatitAndLongit(Double latit, Double longit);
}
