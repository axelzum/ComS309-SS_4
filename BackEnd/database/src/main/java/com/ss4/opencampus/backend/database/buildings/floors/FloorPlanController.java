package com.ss4.opencampus.backend.database.buildings.floors;

import com.ss4.opencampus.backend.database.buildings.Building;
import com.ss4.opencampus.backend.database.buildings.BuildingRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
  private FloorPlanRepository floorPlanRepository;

  @Autowired
  private BuildingRepository buildingRepository;

  private final String path = "/target/images/floorplans/";

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
    try
    {
      saveFloorPlan(buildingId, floorPlan);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
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
    try
    {
      for (FloorPlan fp : floorPlans)
      {
        saveFloorPlan(buildingId, fp);
      }
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
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
    Iterable<FloorPlan> fpList = floorPlanRepository.findAllByBuildingId(buildingId,
                                                                         new Sort(Sort.Direction.ASC, "level"));
    for (FloorPlan fp : fpList)
      fp.setFpBytes(pathToBytes(fp.getFpImagePath()));
    return fpList;
    // might need to change because basement will be last? Could say 0 = basement... tiers will be weird but
    // if im being honest, i dont care about them
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
    Optional<FloorPlan> fp = floorPlanRepository.findByFpIdAndBuildingId(fpId, buildingId);
    if (fp.isPresent())
      fp.get().setFpBytes(pathToBytes(fp.get().getFpImagePath()));
    return fp;
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
    try
    {
      FloorPlan fp = floorPlanRepository.findByFpIdAndBuildingId(fpId, buildingId).get();
      fp.setName(floorPlan.getName());
      fp.setLevel(floorPlan.getLevel());
      byte[] bytes = floorPlan.getFpBytes();
      fp = newFpImage(fp, bytes);
      floorPlanRepository.save(fp);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
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
    try
    {
      FloorPlan fp = floorPlanRepository.findByFpIdAndBuildingId(fpId, buildingId).get();
      if (patch.containsKey("name"))
      {
        fp.setName((String) patch.get("name"));
      }
      if (patch.containsKey("level"))
      {
        fp.setLevel((String) patch.get("level"));
      }
      if (patch.containsKey("fpBytes"))
      {
        byte[] bytes = Base64.decodeBase64((String) patch.get("fpBytes"));
        fp = newFpImage(fp, bytes);
      }
      floorPlanRepository.save(fp);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
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
    try
    { // only delete the file if it isn't tied to "noimage.png"
      FloorPlan fp = floorPlanRepository.findByFpIdAndBuildingId(fpId, buildingId).get();
      File file = new File(fp.getFpImagePath());
      if (!(file.getAbsolutePath().equals("/target/images/noimage.png")))
        file.delete();
      floorPlanRepository.delete(fp);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  /**
   * Helper method to retrieve the image bytes of an image that is tied to a USpot. Used in GET Requests
   *
   * @param picPath
   *         Path in DB to look into to find the image
   *
   * @return byte array that is the image data
   *
   * @throws IOException
   *         Could potentially through an error if given a non-valid path.
   */
  private byte[] pathToBytes(String picPath) throws IOException
  {
    if (picPath == null)
      return null;
    return Files.readAllBytes(Paths.get(picPath));
  }

  /**
   * Helper method that is used by PUT and PATCH when trying to update the FloorPlan picture. If the FloorPlan is using
   * "noimage.png" as the image, we DON'T want to replace it and if it is using another image, we can go ahead and
   * overwrite it.
   * <p>
   * If the byte[] is null, it will simply keep the old image that is currently linked to the USpot.
   *
   * @param floorPlan
   *         FLoorPlan who's image is being changed
   * @param bytes
   *         array of base64 bytes of the new image
   *
   * @return The FloorPlan so it can be saved to the table
   *
   * @throws IOException
   *         Could potentially through an error if given a non-valid path.
   */
  private FloorPlan newFpImage(FloorPlan floorPlan, byte[] bytes) throws IOException
  {
    if (bytes != null) // just keep old picture if no new picture is given
    {
      floorPlan.setFpBytes(bytes);
      FileOutputStream fos;
      if (floorPlan.getFpImagePath().equals("/target/images/noimage.png")) //no previous photo for floor, so make a
      // new one... don't want to overwrite noimage.png!
      {
        floorPlan.setFpImagePath(
                path + "building" + floorPlan.getBuilding().getId() + "floor" + floorPlan.getLevel() + ".png");
        fos = new FileOutputStream(floorPlan.getFpImagePath());
      }
      else // can overwrite old photo, so we don't have to make a new picture like we do above
        fos = new FileOutputStream(floorPlan.getFpImagePath(), false);
      fos.write(floorPlan.getFpBytes());
      fos.close();
    }
    return floorPlan;
  }

  /**
   * Helper method to save a single FloorPlan to the database
   *
   * @param buildingId
   *         id of the Building the FloorPlan is for
   * @param fp
   *         The FloorPlan object in JSON format to be saved
   *
   * @throws IOException
   *         Could be thrown if an issue with saving the image occurs.
   */
  private void saveFloorPlan(Integer buildingId, FloorPlan fp) throws IOException
  {
    byte[] bytes = fp.getFpBytes();
    Building b = buildingRepository.findById(buildingId).get();
    if (bytes != null)
    {
      fp.setFpImagePath(path + "building" + b.getId() + "floor" + fp.getLevel());
      FileOutputStream fos = new FileOutputStream(fp.getFpImagePath() + ".png");
      fos.write(bytes);
      fos.close();
    }
    else
      fp.setFpImagePath("/target/images/noimage.png");
    fp.setBuilding(b);
    floorPlanRepository.save(fp);
  }
}
