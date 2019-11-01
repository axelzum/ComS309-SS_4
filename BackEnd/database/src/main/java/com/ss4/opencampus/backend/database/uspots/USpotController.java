package com.ss4.opencampus.backend.database.uspots;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
 * Controller class for USpots
 * <p>
 * Base URL for all USpot related Requests:
 * <p>http://coms-309-ss-4.misc.iastate.edu:8080/uspots/</p>
 */
@RestController
@RequestMapping(path = "/uspots")
public class USpotController
{
  @Autowired
  private USpotRepository uSpotRepository;

  /**
   * Default path for a USpot image
   */
  private final String path = "/target/images/uspots/";

  /**
   * POST Request to handle adding a new USpot to the database.
   *
   * @param uSpot
   *         USpot to be added. Provided by Frontend in JSON format
   *
   * @return A JSON readable response that tells the Frontend whether the object was successfully added
   */
  @PostMapping("/add")
  public @ResponseBody
  Map<String, Boolean> addNewUSpot(@RequestBody USpot uSpot)
  {
    try
    {
      //get picture bytes from Client
      byte[] bytes = uSpot.getPicBytes();
      //janky fix for pathname setting but works for now.
      int length = uSpotRepository.findAll().size();
      if (bytes != null) // if a picture was present
      {
        uSpot.setUsImagePath(path + "uspot" + (length + 1) + ".png");
        FileOutputStream fos = new FileOutputStream(uSpot.getUsImagePath());
        fos.write(bytes);
        fos.close();
      }
      else // no given picture
        uSpot.setUsImagePath("/target/images/noimage.png");
      uSpot.setRatingCount(1);
      uSpot.setRatingTotal(uSpot.getUsRating());
      // Don't need to make a BuildingRepo because we KNOW that we are getting a valid building_id
      // from the FRONTEND. It will come in as (I hope): { "building_id": X }
      // and then the USpot will be able to parse it and save the info.
      // it might come in as: { "building": X } with X being the ID.
      // At worst, I just change this method to be like the POST in FloorPlanController.java
      uSpotRepository.save(uSpot);
    }
    catch (IOException | DataAccessException ex)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  /**
   * Collection of GET Requests (besides the by ID) that the Client can make to the Backend. Grabs the data for the
   * requested USpots. Needs to find the images since they are not stored in the database directly, so a helper method
   * called pathToBytes(String path) is called to find the directory for the specific USpot and retrieve its image.
   *
   * @param searchType
   *         Type of search the Frontend is making
   * @param param1
   *         Optional parameter used to refine searches
   * @param param2
   *         Another optional parameter used to refine searches. Not currently used but easily could be
   *
   * @return Iterable list of JSON formatted USpots objects that meet the specified search parameters.
   *
   * @throws IOException
   *         Potentially will throw this exception if the image path is not valid. Should not happen
   */
  @GetMapping(path = "/search/{searchType}")
  public @ResponseBody
  Iterable<USpot> getUSpotLists(@PathVariable String searchType, @RequestParam(required = false) Object param1,
                                @RequestParam(required = false) Object param2) throws IOException
  {
    Iterable<USpot> uList;
    switch (searchType)
    {
      case "name":
        uList = uSpotRepository.findByUsName((String) param1);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      case "nameStartsWith":
        uList = uSpotRepository.findAllByUsNameStartingWith((String) param1);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      case "category":
        uList = uSpotRepository.findAllByUsCategory((String) param1);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      case "minRating":
        Double rating = Double.parseDouble((String) param1);
        uList = uSpotRepository.findAllByUsRatingGreaterThanEqual(rating);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      case "building":
        Integer buildingId = Integer.parseInt((String) param1);
        String floorLvl = (String) param2;
        uList = uSpotRepository.findAllByBuildingIdAndFloorLvl(buildingId, floorLvl);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      default:
        uList = uSpotRepository.findAll(new Sort(Sort.Direction.ASC, "usName"));
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
    }
  }

  /**
   * GET Request to find a specific USpot by its ID. Would be used when the Frontend "clicks" on a USpot directly and
   * asks for more information.
   *
   * @param id
   *         ID of USpot that Client wants more information about
   *
   * @return JSON formatted USpot object to Client.
   *
   * @throws IOException
   *         Potentially will throw this exception if the image path is not valid. Should not happen
   */
  @GetMapping(path = "/search/id/{id}")
  public @ResponseBody
  Optional<USpot> getUSpotById(@PathVariable Integer id) throws IOException
  {
    Optional<USpot> u = uSpotRepository.findById(id);
    if (u.isPresent())
      u.get().setPicBytes(pathToBytes(u.get().getUsImagePath()));
    return u;
  }

  /**
   * Method that handles PUT Requests. These are requests where the whole USpot is being updated. Everything should be
   * provided from the Frontend to the Backend. Anything NOT provided will be set to NULL. If you only want to update
   * part of the USpot, use the PATCH Request.
   *
   * @param newUSpot
   *         Info of the USpot that will be placed into the table
   * @param id
   *         USpot that exists in Table that will be updated
   *
   * @return JSON formatted response telling Frontend of success or failure
   */
  @PutMapping(path = "/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateUSpot(@RequestBody USpot newUSpot, @PathVariable Integer id)
  {
    try
    {
      // add way to update Building
      USpot u = uSpotRepository.findById(id).get();
      u.setUsName(newUSpot.getUsName());
      u.setUsCategory(newUSpot.getUsCategory());
      u.setUsLatit(newUSpot.getUsLatit());
      u.setUsLongit(newUSpot.getUsLongit());
      u.setUsRating(newUSpot.getUsRating());
      u.setFloorLvl(newUSpot.getFloorLvl());
      byte[] bytes = newUSpot.getPicBytes();
      u = newUSpotImage(u, bytes);
      uSpotRepository.save(u);
      return Collections.singletonMap("response", true);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
  }

  /**
   * Method that handles PATCH Requests. These requests only update the part of the USpot that was given by the
   * Frontend. So, if you only want to change part of the USpot, use this request. These are much more common than PUT
   * Requests.
   *
   * @param patch
   *         Map of the different fields that will be updated
   * @param id
   *         Current USpot that will be updated
   *
   * @return JSON formatted response telling Frontend of success or failure
   */
  @PatchMapping(path = "/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchUSpot(@RequestBody Map<String, Object> patch, @PathVariable Integer id)
  {
    try
    {
      USpot u = uSpotRepository.findById(id).get();
      if (patch.containsKey("usName"))
      {
        u.setUsName((String) patch.get("usName"));
      }
      if (patch.containsKey("usRating"))
      {
        u.updateRating(((Double) patch.get("usRating"))); //calculate average rating
      }
      if (patch.containsKey("usLatit"))
      {
        u.setUsLatit(((Double) patch.get("usLatit")));
      }
      if (patch.containsKey("usLongit"))
      {
        u.setUsLongit(((Double) patch.get("usLongit")));
      }
      if (patch.containsKey("usCategory"))
      {
        u.setUsCategory((String) patch.get("usCategory"));
      }
      if (patch.containsKey("floorLvl"))
      {
        u.setFloorLvl((String) patch.get("floorLvl"));
      }
      if (patch.containsKey("picBytes"))
      {
        byte[] bytes = Base64.decodeBase64((String) patch.get("picBytes"));
        u = newUSpotImage(u, bytes);
      }
      // add way to update Building
      uSpotRepository.save(u);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  /**
   * Method to handle DELETE Requests. Will delete the USpot with the given ID. Also removes its image from the server
   * IF the image is not the "noimage.png"
   *
   * @param id
   *         ID of USpot to be removed
   *
   * @return JSON formatted response telling Frontend of success or failure
   */
  @DeleteMapping("/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteUSpot(@PathVariable Integer id)
  {
    try
    {
      USpot u = uSpotRepository.findById(id).get();
      File file = new File(u.getUsImagePath());
      if (!(file.getAbsolutePath().equals("/target/images/noimage.png")))
        file.delete();
      uSpotRepository.deleteById(id);
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
    return Files.readAllBytes(Paths.get(picPath));
  }

  /**
   * Helper method that is used by PUT and PATCH when trying to update the USpot picture. If the USpot is using
   * "noimage.png" as the image, we DON'T want to replace it and if it is using another image, we can go ahead and
   * overwrite it.
   * <p>
   * If the byte[] is null, it will simply keep the old image that is currently linked to the USpot.
   *
   * @param u
   *         USpot who's image is being changed
   * @param bytes
   *         array of base64 bytes of the new image
   *
   * @return The USpot so it can be saved to the table
   *
   * @throws IOException
   *         Could potentially through an error if given a non-valid path.
   */
  private USpot newUSpotImage(USpot u, byte[] bytes) throws IOException
  {
    if (bytes != null) //if null, just keep old picture
    {
      u.setPicBytes(bytes);
      FileOutputStream fos;
      if (u.getUsImagePath().equals("/target/images/noimage.png"))
      {
        u.setUsImagePath(path + "uspot_fresh_image_" + u.getUsID() + ".png");
        fos = new FileOutputStream(u.getUsImagePath());
      }
      else
        fos = new FileOutputStream(u.getUsImagePath(), false);
      fos.write(u.getPicBytes());
      fos.close();
    }
    return u;
  }
}
