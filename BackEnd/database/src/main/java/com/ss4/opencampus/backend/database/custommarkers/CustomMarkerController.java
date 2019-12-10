package com.ss4.opencampus.backend.database.custommarkers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Controller for CustomMarkers. Handles POST/GET/PUT/PATCH/DELETE Requests
 */
@RestController
public class CustomMarkerController
{
  @Autowired
  private CustomMarkerService customMarkerService;

  /**
   * POSTs a new CustomMarker into the DB.
   *
   * @param studentId
   *         id of Student who is making the CustomMarker
   * @param customMarker
   *         JSON format of CustomMarker information to be added
   *
   * @return JSON formatted response so the Frontend can tell if CustomMarker was added correctly.
   */
  @PostMapping(path = "/students/{studentId}/customMarkers")
  public @ResponseBody
  Map<String, Boolean> addCustomMarker(@PathVariable(value = "studentId") Integer studentId,
                                       @RequestBody CustomMarker customMarker)
  {
    return Collections.singletonMap("response", customMarkerService.add(studentId, customMarker));
  }

  /**
   * GET Requests. Handle the different ways a Student can search through their CustomMarkers.
   *
   * @param studentId
   *         id of Student making the request
   * @param searchType
   *         Way the Student wants their CMs to be filtered
   * @param param
   *         optional parameter to help the filter decide what to display
   *
   * @return List of CustomMarkers as JSON to the Frontend so the Client will be able to see the results
   */
  @GetMapping(path = "/students/{studentId}/customMarkers/{searchType}")
  public @ResponseBody
  Iterable<CustomMarker> getCustomMarkers(@PathVariable(value = "studentId") Integer studentId,
                                          @PathVariable(value = "searchType") String searchType,
                                          @RequestParam(required = false) String param)
  {
    return customMarkerService.getCustomMarkers(studentId, searchType, param);
  }

  /**
   * GET Request for searching by CM id.
   *
   * @param studentId
   *         id of Student making the request
   * @param cmId
   *         id of CM the Student clicked on
   *
   * @return JSON of CustomMarker that was accessed.
   */
  @GetMapping(path = "/students/{studentId}/customMarkers/id/{id}")
  public @ResponseBody
  Optional<CustomMarker> getCustomMarkerById(@PathVariable(value = "studentId") Integer studentId,
                                             @PathVariable(value = "id") Integer cmId)
  {
    return customMarkerService.getById(studentId, cmId);
  }

  /**
   * PUT Request to update a CM.
   *
   * @param studentId
   *         id of Student trying to update the marker
   * @param cmId
   *         id of CM to be updated
   * @param customMarker
   *         Updated info for the CM
   *
   * @return JSON formatted response so the Frontend can tell if CustomMarker was updated correctly.
   */
  @PutMapping(path = "/students/{studentId}/customMarkers/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateCustomMarker(@PathVariable(value = "studentId") Integer studentId,
                                          @PathVariable(value = "id") Integer cmId,
                                          @RequestBody CustomMarker customMarker)
  {
    return Collections.singletonMap("response", customMarkerService.put(customMarker, studentId, cmId));
  }

  /**
   * PATCH Request that updates a specific part(s) of an existing CM
   *
   * @param studentId
   *         id of Student trying to patch the CM
   * @param cmId
   *         id of CM to be patched
   * @param patch
   *         Map with the key = parameter to be updated; value = new value for key. Can have multiple things updated at
   *         once
   *
   * @return JSON formatted response so the Frontend can tell if CustomMarker was patched correctly.
   */
  @PatchMapping(path = "/students/{studentId}/customMarkers/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchCustomMarker(@PathVariable(value = "studentId") Integer studentId,
                                         @PathVariable(value = "id") Integer cmId,
                                         @RequestBody Map<String, Object> patch)
  {
    return Collections.singletonMap("response", customMarkerService.patch(patch, studentId, cmId));
  }

  /**
   * Request to DELETE an existing CM from the DB.
   *
   * @param studentId
   *         id of Student making request
   * @param cmId
   *         id of CM to be deleted
   *
   * @return JSON formatted response so the Frontend can tell if CustomMarker was actually deleted.
   */
  @DeleteMapping(path = "/students/{studentId}/customMarkers/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteCustomMarker(@PathVariable(value = "studentId") Integer studentId,
                                          @PathVariable(value = "id") Integer cmId)
  {
    return Collections.singletonMap("response", customMarkerService.delete(studentId, cmId));
  }
}
