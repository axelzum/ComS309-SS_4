package com.ss4.opencampus.backend.database.floors;

import com.ss4.opencampus.backend.database.buildings.Building;
import com.ss4.opencampus.backend.database.buildings.BuildingRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Service Class for FloorPlans
 */
@Service
public class FloorPlanService
{
  @Autowired
  private FloorPlanRepository floorPlanRepository;

  @Autowired
  private BuildingRepository buildingRepository;

  private final String path = "/target/images/floorplans/";

  /**
   * Adds a floorplan to the DB
   *
   * @param buildingId
   *         id of Building FloorPlan is for
   * @param floorPlan
   *         FloorPlan to be added
   *
   * @return Boolean of success or failure
   */
  public Boolean add(Integer buildingId, FloorPlan floorPlan)
  {
    try
    {
      saveFloorPlan(buildingId, floorPlan);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Adds an array of FloorPlans to the DB
   *
   * @param buildingId
   *         id of Building FloorPlans are for
   * @param floorPlans
   *         FloorPlans to be added
   *
   * @return Boolean of success or failure
   */
  public Boolean addMultiple(Integer buildingId, FloorPlan[] floorPlans)
  {
    try
    {
      for (FloorPlan floorPlan : floorPlans)
      {
        saveFloorPlan(buildingId, floorPlan);
      }
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Returns list of FloorPlans sorted by their levels
   *
   * @param buildingId
   *         id of Building to find FloorPlans for
   *
   * @return Iterable List of FloorPlans
   *
   * @throws IOException
   *         could throw an exception if image is not present
   */
  public Iterable<FloorPlan> getFloorPlans(Integer buildingId) throws IOException
  {
    Iterable<FloorPlan> fpList = floorPlanRepository.findAllByBuildingId(buildingId, new Sort(Sort.Direction.ASC,
                                                                                              "level"));
    for (FloorPlan fp : fpList)
    {
      fp.setFpBytes(pathToBytes(fp.getFpImagePath()));
    }
    return fpList;
  }

  /**
   * Finds a specific FloorPlan in DB
   *
   * @param buildingId
   *         id of Building to look in
   * @param fpId
   *         id of FloorPlan to find
   *
   * @return FloorPlan obj
   *
   * @throws IOException
   *         could throw an exception if image is not present
   */
  public Optional<FloorPlan> getById(Integer buildingId, Integer fpId) throws IOException
  {
    Optional<FloorPlan> fp = floorPlanRepository.findByFpIdAndBuildingId(fpId, buildingId);
    if (fp.isPresent())
      fp.get().setFpBytes(pathToBytes(fp.get().getFpImagePath()));
    return fp;
  }

  /**
   * Updates FloorPlan obj. Info not given is set to NULL
   *
   * @param buildingId
   *         id of Building FloorPlan is in
   * @param fpId
   *         id of FloorPlan to update
   * @param floorPlan
   *         Info to update with
   *
   * @return Boolean of success or failure
   */
  public Boolean put(Integer buildingId, Integer fpId, FloorPlan floorPlan)
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
      return false;
    }
    return true;
  }

  /**
   * Patches an existing FloorPlan with given info in map
   *
   * @param buildingId
   *         id of Building FloorPlan is in
   * @param fpId
   *         id of FloorPlan to be updated
   * @param patch
   *         Map with Updated info
   *
   * @return Boolean of success or failure
   */
  public Boolean patch(Integer buildingId, Integer fpId, Map<String, Object> patch)
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
      return false;
    }
    return true;
  }

  /**
   * Finds the FloorPlan tied to the given Building and removes the Route from the database
   *
   * @param fpId
   *         id of FloorPlan to be deleted
   * @param buildingId
   *         id of Building who made the route
   *
   * @return Boolean of success or failure to delete
   */
  public Boolean delete(Integer buildingId, Integer fpId)
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
      return false;
    }
    return true;
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
      fp.setFpImagePath(path + "building" + b.getId() + "floor" + fp.getLevel() + ".png");
      FileOutputStream fos = new FileOutputStream(fp.getFpImagePath());
      fos.write(bytes);
      fos.close();
    }
    else
      fp.setFpImagePath("/target/images/noimage.png");
    fp.setBuilding(b);
    floorPlanRepository.save(fp);
  }

}
