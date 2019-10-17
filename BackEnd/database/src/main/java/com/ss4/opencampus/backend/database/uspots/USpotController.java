package com.ss4.opencampus.backend.database.uspots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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
  Map<String, String> addNewUSpot(@RequestBody USpot uSpot)
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
      uSpotRepository.save(uSpot);
    }
    catch (IOException | DataAccessException ex)
    {
      return Collections.singletonMap("response", ex.getMessage());
    }
    return Collections.singletonMap("response", "true");
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
        uList = uSpotRepository.findAllByUsRatingGreaterThanEqual((Double) param1);
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
}
