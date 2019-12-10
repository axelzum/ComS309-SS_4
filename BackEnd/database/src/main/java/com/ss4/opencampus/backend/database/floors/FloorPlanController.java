package com.ss4.opencampus.backend.database.floors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Controller for FloorPlans. These will be tied to specific Buildings. When the Building is deleted, so are its
 * respective FloorPlans
 */
@RestController
public class FloorPlanController
{
  @Autowired
  private FloorPlanService floorPlanService;

  /**
   * POST a single FloorPlan to the database. Calls the saveFloorPlan helper method to do the heavy lifting
   *
   * @param buildingId
   *         id of Building that the FloorPlan is for
   * @param floorPlan
   *         FloorPlan in JSON that will be saved
   *
   * @return JSON formatted response tells Frontend of success or failure
   */
  @PostMapping(path = "/buildings/{buildingId}/floorPlans")
  public @ResponseBody
  Map<String, Boolean> addFloorPlan(@PathVariable(value = "buildingId") Integer buildingId,
                                    @RequestBody FloorPlan floorPlan)
  {
    return Collections.singletonMap("response", floorPlanService.add(buildingId, floorPlan));
  }

  /**
   * POST an array of FloorPlans to the database. Calls the saveFloorPlan helper method to do the heavy lifting
   *
   * @param buildingId
   *         id of Building that the FloorPlan is for
   * @param floorPlans
   *         array of FloorPlans in JSON that will be saved
   *
   * @return JSON formatted response tells Frontend of success or failure
   */
  @PostMapping(path = "/buildings/{buildingId}/floorPlans/addMultiple")
  public @ResponseBody
  Map<String, Boolean> addMultiFloorPlan(@PathVariable(value = "buildingId") Integer buildingId,
                                         @RequestBody FloorPlan[] floorPlans)
  {
    return Collections.singletonMap("response", floorPlanService.addMultiple(buildingId, floorPlans));
  }

  /**
   * GET Request to return ALL FloorPlans in a single Building
   *
   * @param buildingId
   *         id of Building to look in
   *
   * @return Iterable list of FloorPlans in JSON to Frontend
   *
   * @throws IOException
   *         In-case fetching the image fails, we need to be careful!!
   */
  @GetMapping(path = "/buildings/{buildingId}/floorPlans/all")
  public @ResponseBody
  Iterable<FloorPlan> getFloorPlans(@PathVariable(value = "buildingId") Integer buildingId) throws IOException
  {
    return floorPlanService.getFloorPlans(buildingId);
  }

  /**
   * GET Request to return a single FloorPlan by using its id and the Building id
   *
   * @param buildingId
   *         id of Building the FloorPlan is in
   * @param fpId
   *         id of FloorPlan to find
   *
   * @return A single FloorPlan in JSON
   *
   * @throws IOException
   *         In-case fetching the image fails, we need to be careful!!
   */
  @GetMapping(path = "/buildings/{buildingId}/floorPlans/id/{id}")
  public @ResponseBody
  Optional<FloorPlan> getFloorPlanById(@PathVariable(value = "buildingId") Integer buildingId, @PathVariable(value = "id") Integer fpId) throws IOException
  {
    return floorPlanService.getById(buildingId, fpId);
  }

  /**
   * PUT Request to update a FloorPlan with the given information. Anything NOT provided will be set to NULL because
   * that is how PUT Requests work
   *
   * @param buildingId
   *         id of Building the FloorPlan is in
   * @param fpId
   *         id of FloorPlan to be updated
   * @param floorPlan
   *         Updated information for the floorPlan
   *
   * @return JSON formatted response tells Frontend of success or failure
   */
  @PutMapping(path = "/buildings/{buildingId}/floorPlans/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateFloorPlan(@PathVariable(value = "buildingId") Integer buildingId,
                                       @PathVariable(value = "id") Integer fpId,
                                       @RequestBody FloorPlan floorPlan)
  {
    return Collections.singletonMap("response", floorPlanService.put(buildingId, fpId, floorPlan));
  }

  /**
   * PATCH Request that will only update the given fields for a specific FloorPlan. All other fields will remain the
   * same because that is how PATCHes work. Uses less bandwidth than a PUT, so these are usually preferred.
   *
   * @param buildingId
   *         id of Building the FloorPlan is in
   * @param fpId
   *         id of FloorPlan to be patched
   * @param patch
   *         JSON formatted information that the FloorPlan will be updated with
   *
   * @return JSON formatted response tells Frontend of success or failure
   */
  @PatchMapping(path = "/buildings/{buildingId}/floorPlans/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchFloorPlan(@PathVariable(value = "buildingId") Integer buildingId,
                                      @PathVariable(value = "id") Integer fpId,
                                      @RequestBody Map<String, Object> patch)
  {
    return Collections.singletonMap("response", floorPlanService.patch(buildingId, fpId, patch));
  }

  /**
   * DELETE Request. Will delete the specific FloorPlan from the database
   *
   * @param buildingId
   *         id of Building the FloorPlan is in
   * @param fpId
   *         id of FloorPlan to be deleted
   *
   * @return JSON formatted response tells Frontend of success or failure
   */
  @DeleteMapping(path = "/buildings/{buildingId}/floorPlans/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteFloorPlan(@PathVariable(value = "buildingId") Integer buildingId,
                                       @PathVariable(value = "id") Integer fpId)
  {
    return Collections.singletonMap("response", floorPlanService.delete(buildingId, fpId));
  }

}
