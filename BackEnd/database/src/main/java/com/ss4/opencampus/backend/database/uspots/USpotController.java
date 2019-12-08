package com.ss4.opencampus.backend.database.uspots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
  private USpotService uSpotService;

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
    return Collections.singletonMap("response", uSpotService.add(uSpot));
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
    return uSpotService.getUSpots(searchType, param1, param2);
  }

  /**
   * GET Request to find a specific USpot by its ID. Would be used when the Frontend "clicks" on a USpot directly and
   * asks for more information.
   *
   * @param id
   *         ID of USpot that Client wants more information about
   *
   * @return JSON formatted USpot object to Client.
   */
  @GetMapping(path = "/search/id/{id}")
  public @ResponseBody
  Optional<USpot> getUSpotById(@PathVariable Integer id)
  {
    return uSpotService.getById(id);
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
    return Collections.singletonMap("response", uSpotService.put(newUSpot, id));
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
    return Collections.singletonMap("response", uSpotService.patch(patch, id));
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
    return Collections.singletonMap("response", uSpotService.delete(id));
  }
}
