package com.ss4.opencampus.backend.database.students.custommarkers;

import com.ss4.opencampus.backend.database.students.Student;
import com.ss4.opencampus.backend.database.students.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
  private CustomMarkerRepository customMarkerRepository;

  @Autowired
  private StudentRepository studentRepository;

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
    try
    {
      Student student = studentRepository.findById(studentId).get();
      customMarker.setStudent(student);
      customMarkerRepository.save(customMarker);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
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
    switch (searchType)
    {
      case "name":
        return customMarkerRepository.findByNameAndStudentId(param, studentId);
      case "nameStartsWith":
        return customMarkerRepository.findAllByNameStartingWithAndStudentId(param, studentId);
      default:
        return customMarkerRepository.findAllByStudentId(studentId, new Sort(Sort.Direction.ASC, "name"));
    }
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
    return customMarkerRepository.findByCmIdAndStudentId(cmId, studentId);
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
    try
    {
      CustomMarker cm = customMarkerRepository.findByCmIdAndStudentId(cmId, studentId).get();
      cm.setName(customMarker.getName());
      cm.setDesc(customMarker.getDesc());
      cm.setCmLatit(customMarker.getCmLatit());
      cm.setCmLongit(customMarker.getCmLongit());
      customMarkerRepository.save(cm);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
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
    try
    {
      CustomMarker cm = customMarkerRepository.findByCmIdAndStudentId(cmId, studentId).get();
      if (patch.containsKey("name"))
      {
        cm.setName((String) patch.get("name"));
      }
      if (patch.containsKey("desc"))
      {
        cm.setDesc((String) patch.get("desc"));
      }
      if (patch.containsKey("cmLatit"))
      {
        cm.setCmLatit(((Double) patch.get("cmLatit")));
      }
      if (patch.containsKey("cmLongit"))
      {
        cm.setCmLongit(((Double) patch.get("cmLongit")));
      }
      customMarkerRepository.save(cm);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
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
    try
    {
      CustomMarker cm = customMarkerRepository.findByCmIdAndStudentId(cmId, studentId).get();
      customMarkerRepository.delete(cm);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }
}
