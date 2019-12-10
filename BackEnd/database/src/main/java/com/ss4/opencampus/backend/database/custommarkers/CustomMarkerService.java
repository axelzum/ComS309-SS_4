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

  /**
   * Adds a CM to the DB
   *
   * @param studentId
   *         id of Student the CM is for
   * @param customMarker
   *         CM object to be added
   *
   * @return Boolean of success or failure
   */
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

  /**
   * Method that searches DB in different ways depending on params to find lists of CMs
   *
   * @param studentId
   *         id of Student to look at for CMs
   * @param searchType
   *         way to find CMs
   * @param param
   *         optional param to help search
   *
   * @return Iterable list of CMs
   */
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

  /**
   * Searches DB for a specific CM
   *
   * @param studentId
   *         id of Student who made CM
   * @param cmId
   *         id of CM to find
   *
   * @return CM obj
   */
  public Optional<CustomMarker> getById(Integer studentId, Integer cmId)
  {
    return customMarkerRepository.findByCmIdAndStudentId(cmId, studentId);
  }

  /**
   * Updates an existing CM with given info. Info not given is set to NULL
   *
   * @param customMarker
   *         Info that CM will be updated with
   * @param studentId
   *         id of Student who made CM
   * @param cmId
   *         id of CM to be updated
   *
   * @return Boolean of success or failure
   */
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

  /**
   * Updates CM with info given in map
   *
   * @param patch
   *         Map with info that CM will be updated with
   * @param studentId
   *         id of Student who made the CM
   * @param cmId
   *         id of the CM to be updated
   *
   * @return Boolean of success or failure
   */
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

  /**
   * Finds the CustomMarker tied to the given Student and removes the CM from the database
   *
   * @param cmId
   *         id of CM to be deleted
   * @param studentId
   *         id of Student who made the route
   *
   * @return Boolean of success or failure to delete
   */
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
