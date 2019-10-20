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
   * Mapping for the different searches that will return Buildings in the database. If the Client is trying to see
   * either a list of Buildings or a singular Building, this method will be called. Singluar Buildings will be returned
   * in a List of size 1.
   *
   * @param searchType
   *         Type of list the client wants to see. Can be all Buildings, searching by name Start, or searching by
   *         abbreviation start
   * @param param1
   *         What the client is searching with. Not a required parameter for this method to work.
   * @param param2
   *         What the client is searching with. Not a required parameter for this method to work.
   *
   * @return A list of Buildings that meet what the client wants. Or null if they somehow search something they
   * shouldn't be able to search.
   */
  @GetMapping(path = "/search/{searchType}")
  public @ResponseBody
  Iterable<Building> getBuildingLists(@PathVariable String searchType, @RequestParam(required = false) Object param1,
                                      @RequestParam(required = false) Object param2)
  {
    switch (searchType)
    {
      case "nameStartsWith":
        return buildingRepository.findAllByBuildingNameStartingWith((String) param1);
      case "abbreviationStartsWith":
        return buildingRepository.findAllByAbbreviationStartingWith((String) param1);
      case "name":
        return buildingRepository.findByBuildingName((String) param1);
      case "abbreviation":
        return buildingRepository.findByAbbreviation((String) param1);
      case "address":
        return buildingRepository.findByAddress((String) param1);
      case "location":
        Double lat = Double.parseDouble((String) param1);
        Double lon = Double.parseDouble((String) param2);
        return buildingRepository.findByLatitAndLongit(lat, lon);
      default: // default is returning a list of all Buildings sorted by their names. There needs to be some text after "search/" otherwise it will not work?
        return buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName"));
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

  /**
   * Method that handles PUT Requests. These are requests where the whole Building is being updated. Everything should
   * be provided from the Frontend to the Backend. Anything NOT provided will be set to NULL. If you only want to update
   * part of the Building, use the PATCH Request.
   *
   * @param building
   *         Info of the Building that will be placed into the table
   * @param id
   *         ID of the Building that exists in Table that will be updated
   *
   * @return JSON formatted response telling Frontend of success or failure
   */
  @PutMapping(path = "/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateBuilding(@RequestBody Building building, @PathVariable Integer id)
  {
    try
    {
      Building b = buildingRepository.findById(id).get();
      b.setBuildingName(building.getBuildingName());
      b.setAddress(building.getAddress());
      b.setAbbreviation(building.getAbbreviation());
      b.setLatit(building.getLatit());
      b.setLongit(building.getLongit());
      buildingRepository.save(b);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  /**
   * Method that handles PATCH Requests. These requests only update the part of the Student that was given by the
   * Frontend. So, if you only want to change part of the USpot, use this request. These are much more common than PUT
   * Requests.
   *
   * @param patch
   *         Map of the different fields that will be updated
   * @param id
   *         ID of the Building that exists in Table that will be updated
   *
   * @return JSON formatted response telling Frontend of success or failure
   */
  @PatchMapping(path = "/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchBuilding(@RequestBody Map<String, Object> patch,
                                     @PathVariable Integer id)
  {
    try
    {
      Building building = buildingRepository.findById(id).get();
      if (patch.containsKey("buildingName"))
      {
        building.setBuildingName((String) patch.get("buildingName"));
      }
      if (patch.containsKey("address"))
      {
        building.setAddress((String) patch.get("address"));
      }
      if (patch.containsKey("abbreviation"))
      {
        building.setAbbreviation((String) patch.get("abbreviation"));
      }
      if (patch.containsKey("latit"))
      {
        building.setLatit(((Double) patch.get("latit")));
      }
      if (patch.containsKey("longit"))
      {
        building.setLongit(((Double) patch.get("longit")));
      }
      buildingRepository.save(building);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  /**
   * Method to handle DELETE Requests. Will delete the Building with the given ID if it is in the table
   *
   * @param id
   *         ID of the Building that will be deleted
   *
   * @return JSON formatted response telling Frontend of success or failure
   */
  @DeleteMapping(path = "/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteBuilding(@PathVariable Integer id)
  {
    try
    {
      buildingRepository.deleteById(id);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }
}
