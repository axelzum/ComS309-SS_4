package com.ss4.opencampus.backend.database.students.custommarkers;

import com.ss4.opencampus.backend.database.students.Student;
import com.ss4.opencampus.backend.database.students.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class CustomMarkerController
{
  @Autowired
  private CustomMarkerRepository customMarkerRepository;

  @Autowired
  private StudentRepository studentRepository;

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

  @GetMapping(path = "/students/{studentId}/customMarkers/id/{id}")
  public @ResponseBody
  Optional<CustomMarker> getCustomMarkerById(@PathVariable(value = "studentId") Integer studentId,
                                             @PathVariable(value = "id") Integer cmId)
  {
    return customMarkerRepository.findByCmIdAndStudentId(cmId, studentId);
  }
}
