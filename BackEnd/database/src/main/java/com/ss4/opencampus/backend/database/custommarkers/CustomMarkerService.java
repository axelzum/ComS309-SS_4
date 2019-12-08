package com.ss4.opencampus.backend.database.custommarkers;

import com.ss4.opencampus.backend.database.students.Student;
import com.ss4.opencampus.backend.database.students.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Service Class for CustomMarkers
 */
@Service
public class CustomMarkerService
{
  @Autowired
  private CustomMarkerRepository customMarkerRepository;

  @Autowired
  private StudentRepository studentRepository;

  public Boolean add(Integer studentId, CustomMarker customMarker)
  {
    try
    {
      Student student = studentRepository.findById(studentId).get();
      customMarker.setStudent(student);
      customMarkerRepository.save(customMarker);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  public Iterable<CustomMarker> getCustomMarkers(Integer studentId, String searchType, String param)
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

  public Optional<CustomMarker> getById(Integer studentId, Integer cmId)
  {
    return customMarkerRepository.findByCmIdAndStudentId(cmId, studentId);
  }

  public Boolean put(CustomMarker customMarker, Integer studentId, Integer cmId)
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
      return false;
    }
    return true;
  }

  public Boolean patch(Map<String, Object> patch, Integer studentId, Integer cmId)
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
      return false;
    }
    return true;
  }

  public Boolean delete(Integer studentId, Integer cmId)
  {
    try
    {
      CustomMarker cm = customMarkerRepository.findByCmIdAndStudentId(cmId, studentId).get();
      customMarkerRepository.delete(cm);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
}
