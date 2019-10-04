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
  Map<String, Boolean> addSingleBuilding(@RequestBody Building building)
  {
    try
    {
      buildingRepository.save(building);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
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
  Map<String, Boolean> addMultipleBuildings(@RequestBody Building[] buildings)
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
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  /**
   * Mapping for the different searches that will return lists of Buildings in the database. If the Client is trying to
   * see a list of Buildings, this method will be called
   *
   * @param searchType
   *         Type of list the client wants to see. Can be all Buildings, searching by name Start, or searching by
   *         abbreviation start
   * @param param
   *         What the client is searching with. Not a required parameter for this method to work.
   *
   * @return A list of Buildings that meet what the client wants. Or null if they somehow search something they
   * shouldn't be able to search.
   */
  @GetMapping(path = "/search/lists/{searchType}")
  public @ResponseBody
  Iterable<Building> getBuildingLists(@PathVariable String searchType, @RequestParam(required = false) String param)
  {
    switch (searchType)
    {
      case "all":
        return buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName"));
      case "nameStartsWith":
        return buildingRepository.findAllByBuildingNameStartingWith(param);
      case "abbreviationStartsWith":
        return buildingRepository.findAllByAbbreviationStartingWith(param);
      default:
        return null;
    }
  }

  /**
   * Mapping for the different searches that will return a single Building in the database. If the Client is trying to
   * find a single Building, this method will be called
   *
   * @param searchType
   *         How the client wants to find their specific Building.
   * @param param1
   *         Parameter to refine search. This is parameter is required otherwise the Backend does not know what to
   *         search for
   * @param param2
   *         Parameter used when search by location. This is for Longitude. This parameter is optional
   *
   * @return A single Building that meets the parameters the Client entered
   */
  @GetMapping(path = "/search/specific/{searchType}")
  public @ResponseBody
  Building getBuildingBy(@PathVariable String searchType, @RequestParam Object param1,
                         @RequestParam(required = false) Object param2)
  {
    switch (searchType)
    {
      case "name":
        return buildingRepository.findByBuildingName((String) param1);
      case "abbreviation":
        return buildingRepository.findByAbbreviation((String) param1);
      case "address":
        return buildingRepository.findByAddress((String) param1);
      case "location":
        return buildingRepository.findByLatitAndLongit((Double) param1, (Double) param2);
      default:
        return null;
    }
  }

  /**
   * If A Student clicks on a Building and wants to know information about it, we know that the building exists, so we
   * will use this method to get the information about the Building.
   * <p>
   * Has to be separate because searching by ID returns an Optional List of Buildings
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

  /*
   * Below are old mappings that have been combined into 2 methods instead of many. The combination will make
   * it easier to add onto if needed at a later date.
   * */
//  /**
//   * Get a list of all buildings in the database
//   *
//   * @return List of all buildings sorted alphabetically in JSON format
//   */
//  @GetMapping(path = "/search/all") // should be alphabetical
//  public @ResponseBody
//  Iterable<Building> getAllBuildings()
//  {
//    // use Java code names for HttpRequests. Not MySQL names
//    return buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName"));
//  }
//
//  /**
//   * Find the building with the given name
//   *
//   * @param name
//   *         Name of building to look for
//   *
//   * @return JSON data of building
//   */
//  @GetMapping(path = "/search/name")
//  public @ResponseBody
//  Building getBuildingByName(@RequestParam String name)
//  {
//    return buildingRepository.findByBuildingName(name);
//  }
//
//  /**
//   * Find buildings with names that start with given parameter
//   *
//   * @param startsWith
//   *         Characters buildings could start with
//   *
//   * @return List of Building data in JSON format that meet parameters
//   */
//  @GetMapping(path = "/search/nameStartsWith")
//  public @ResponseBody
//  Iterable<Building> getBuildingsStartingWith(@RequestParam String startsWith)
//  {
//    return buildingRepository.findAllByBuildingNameStartingWith(startsWith);
//  }
//
//  /**
//   * Find Building with given abbreviation
//   *
//   * @param abbreviation
//   *         Building abbreviation to search for
//   *
//   * @return Building data in JSON format of Building with given abbreviation
//   */
//  @GetMapping("/search/abbreviation")
//  public @ResponseBody
//  Building getBuildingByAbbrev(@RequestParam String abbreviation)
//  {
//    return buildingRepository.findByAbbreviation(abbreviation);
//  }
//
//  /**
//   * Find List of buildings whose abbreviations start with given text
//   *
//   * @param startsWith
//   *         text abbreviations are to start with
//   *
//   * @return List of Building data in JSON format of Buildings with start of given abbreviation
//   */
//  @GetMapping(path = "/search/abbreviationStartsWith")
//  public @ResponseBody
//  Iterable<Building> getBuildingsByAddressStartingWith(@RequestParam String startsWith)
//  {
//    return buildingRepository.findAllByAbbreviationStartingWith(startsWith);
//  }
//
//  /**
//   * Get building that has given address
//   *
//   * @param address
//   *         Address to look up
//   *
//   * @return data in JSON format of Building with given address
//   */
//  @GetMapping(path = "/search/address")
//  public @ResponseBody
//  Building getBuildingByAddress(@RequestParam String address)
//  {
//    return buildingRepository.findByAddress(address);
//  }
//
//  /**
//   * Return a Building's information if given it's exact latitude and longitude
//   *
//   * @param latit
//   *         latitude of building
//   * @param longit
//   *         longitude of building
//   *
//   * @return data in JSON format about Building at given coordinates
//   */
//  @GetMapping(path = "/search/location")
//  public @ResponseBody
//  Building getBuildingByLocation(@RequestParam Double latit, @RequestParam Double longit)
//  {
//    return buildingRepository.findByLatitAndLongit(latit, longit);
//  }
}
