package com.ss4.opencampus.backend.database.buildings;

import org.springframework.beans.factory.annotation.Autowired;
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
  private BuildingService buildingService;

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
    return Collections.singletonMap("response", buildingService.add(building));
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
    return Collections.singletonMap("response", buildingService.addMultipleBuildings(buildings));
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
    return buildingService.getBuildings(searchType, param1, param2);
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
    return buildingService.getById(id);
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
    return Collections.singletonMap("response", buildingService.put(building, id));
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
    return Collections.singletonMap("response", buildingService.patch(patch, id));
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
    return Collections.singletonMap("response", buildingService.delete(id));
  }
}
