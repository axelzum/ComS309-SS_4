package com.ss4.opencampus.backend.database.buildings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Base URL for all Building related Requests:
 * <p>http://coms-309-ss-4.misc.iastate.edu:8080/buildings</p>
 * Everything in either @PostMapping or @GetMapping will be added on to the URL for the specific request. If the request
 * has added parameters, it will look something like this:
 * <p>http://coms-309-ss-4.misc.iastate.edu:8080/buildings/search/abbreviation?abbreviation="value_here_no_quotes"</p>
 * If you are trying to go by address, replace abbreviation with what's in the @GetMapping for address
 */
@RestController
@RequestMapping(path = "/buildings")
public class BuildingController
{
  @Autowired
  private BuildingRepository buildingRepository;

  /**
   * Add new Building to Buildings table
   *
   * @param building
   *         Building info in JSON format to be added
   *
   * @return Message notifying either success or failure of POST
   */
  @PostMapping(path = "/add")
  public @ResponseBody
  Map<String, String> addSingleBuilding(@RequestBody Building building)
  {
    try
    {
      buildingRepository.save(building);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", "Failed to add building to Buildings table");
    }
    return Collections.singletonMap("response", "New building added to Buildings table");
  }

  /**
   * Used at beginning of table. Added large list of ISU buildings to the Buildings table. Will not use again unless
   * needed.
   *
   * @param buildings
   *         Array of Buildings in JSON format to be added
   *
   * @return Message notifying success or failure of POST
   */
  @PostMapping(path = "/addMultiple")
  public @ResponseBody
  Map<String, String> addMultipleBuildings(@RequestBody Building[] buildings)
  {
    int addedCnt = 0;
    try
    {
      for (Building b : buildings)
      {
        buildingRepository.save(b);
        addedCnt++;
      }
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response","Ran into error. Only added " + addedCnt + " building(s) to the Buildings table" );
    }
    return Collections.singletonMap("response", "Added " + addedCnt + " buildings to the Buildings table.");
  }

  /**
   * Get a list of all buildings in the database
   *
   * @return List of all buildings sorted alphabetically in JSON format
   */
  @GetMapping(path = "/search/all") // should be alphabetical
  public @ResponseBody
  Iterable<Building> getAllBuildings()
  {
    // use Java code names for HttpRequests. Not MySQL names
    return buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName"));
  }

  /**
   * Find the building with the given name
   *
   * @param name
   *         Name of building to look for
   *
   * @return JSON data of building
   */
  @GetMapping(path = "/search/name")
  public @ResponseBody
  Building getBuildingByName(@RequestParam String name)
  {
    return buildingRepository.findByBuildingName(name);
  }

  /**
   * Find buildings with names that start with given parameter
   *
   * @param startsWith
   *         Characters buildings could start with
   *
   * @return List of Building data in JSON format that meet parameters
   */
  @GetMapping(path = "/search/nameStartsWith")
  public @ResponseBody
  Iterable<Building> getBuildingsStartingWith(@RequestParam String startsWith)
  {
    return buildingRepository.findAllByBuildingNameStartingWith(startsWith);
  }

  /**
   * Find Building with given abbreviation
   *
   * @param abbreviation
   *         Building abbreviation to search for
   *
   * @return Building data in JSON format of Building with given abbreviation
   */
  @GetMapping("/search/abbreviation")
  public @ResponseBody
  Building getBuildingByAbbrev(@RequestParam String abbreviation)
  {
    return buildingRepository.findByAbbreviation(abbreviation);
  }

  /**
   * Find List of buildings whose abbreviations start with given text
   *
   * @param startsWith
   *         text abbreviations are to start with
   *
   * @return List of Building data in JSON format of Buildings with start of given abbreviation
   */
  @GetMapping(path = "/search/abbreviationStartsWith")
  public @ResponseBody
  Iterable<Building> getBuildingsByAddressStartingWith(@RequestParam String startsWith)
  {
    return buildingRepository.findAllByAbbreviationStartingWith(startsWith);
  }

  /**
   * Get building that has given address
   *
   * @param address
   *         Address to look up
   *
   * @return data in JSON format of Building with given address
   */
  @GetMapping(path = "/search/address")
  public @ResponseBody
  Building getBuildingByAddress(@RequestParam String address)
  {
    return buildingRepository.findByAddress(address);
  }

  /**
   * If A Student clicks on a Building and wants to know information about it, we know that the building exists, so we
   * will use this method to get the information about the Building
   *
   * @param id
   *         id of Building that was clicked on
   *
   * @return data in JSON format of Building with given ID
   */
  @GetMapping(path = "/search/id/{id}")
  public @ResponseBody
  Optional<Building> getBuildingById(@PathVariable Integer id)
  {
    return buildingRepository.findById(id);
  }

  /**
   * Return a Building's information if given it's exact latitude and longitude
   *
   * @param latit
   *         latitude of building
   * @param longit
   *         longitude of building
   *
   * @return data in JSON format about Building at given coordinates
   */
  @GetMapping(path = "/search/location")
  public @ResponseBody
  Building getBuildingByLocation(@RequestParam Double latit, @RequestParam Double longit)
  {
    return buildingRepository.findByLatitAndLongit(latit, longit);
  }
}
