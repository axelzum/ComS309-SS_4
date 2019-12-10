package com.ss4.opencampus.backend.database.routes;

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
 * Service Class for Routes
 */
@Service
public class RouteService
{
  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private StudentRepository studentRepository;

  /**
   * Adds a Route to the DB
   *
   * @param studentId
   *         id of Student the Route is for
   * @param route
   *         Route object to be added
   *
   * @return Boolean of success or failure
   */
  public Boolean add(Integer studentId, Route route)
  {
    try
    {
      Student student = studentRepository.findById(studentId).get();
      route.setStudent(student);
      routeRepository.save(route);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Method that searches DB in different ways depending on params to find lists of Routes
   *
   * @param studentId
   *         id of Student to look at for Routes
   * @param searchType
   *         way to find Routes
   * @param param
   *         optional param to help search
   *
   * @return Iterable list of Routes
   */
  public Iterable<Route> getRoutes(Integer studentId, String searchType, String param)
  {
    if (searchType.equals("rtName"))
      return routeRepository.findByRtNameAndStudentId(param, studentId);
    return routeRepository.findAllByStudentId(studentId, new Sort(Sort.Direction.ASC, "rtName"));
  }

  /**
   * Searches DB for a specific Route
   *
   * @param studentId
   *         id of Student who made Route
   * @param id
   *         id of Route to find
   *
   * @return Route obj
   */
  public Optional<Route> getById(Integer id, Integer studentId)
  {
    return routeRepository.findByIdAndStudentId(id, studentId);
  }

  /**
   * Updates an existing Route with given info. Info not given is set to NULL
   *
   * @param newRoute
   *         Info that Route will be updated with
   * @param studentId
   *         id of Student who made Route
   * @param id
   *         id of Route to be updated
   *
   * @return Boolean of success or failure
   */
  public Boolean put(Integer id, Integer studentId, Route newRoute)
  {
    try
    {
      Route route = routeRepository.findByIdAndStudentId(id, studentId).get();
      route.setRtName(newRoute.getRtName());
      route.setOriginLat(newRoute.getOriginLat());
      route.setOriginLng(newRoute.getOriginLng());
      route.setDestLat(newRoute.getDestLat());
      route.setDestLng(newRoute.getDestLng());
      routeRepository.save(route);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Updates Route with info given in map
   *
   * @param patch
   *         Map with info that Route will be updated with
   * @param studentId
   *         id of Student who made the Route
   * @param id
   *         id of the Route to be updated
   *
   * @return Boolean of success or failure
   */
  public Boolean patch(Integer id, Integer studentId, Map<String, Object> patch)
  {
    try
    {
      Route route = routeRepository.findByIdAndStudentId(id, studentId).get();
      if (patch.containsKey("rtName"))
      {
        route.setRtName((String) patch.get("rtName"));
      }
      if (patch.containsKey("originLat"))
      {
        route.setOriginLat(((Double) patch.get("originLat")));
      }
      if (patch.containsKey("originLng"))
      {
        route.setOriginLng(((Double) patch.get("originLng")));
      }
      if (patch.containsKey("destLat"))
      {
        route.setDestLat(((Double) patch.get("destLat")));
      }
      if (patch.containsKey("destLng"))
      {
        route.setDestLng(((Double) patch.get("destLng")));
      }
      routeRepository.save(route);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Finds the Route tied to the given Student and removes the Route from the database
   *
   * @param id
   *         id of Route to be deleted
   * @param studentId
   *         id of Student who made the route
   *
   * @return Boolean of success or failure to delete
   */
  public Boolean delete(Integer id, Integer studentId)
  {
    try
    {
      Route route = routeRepository.findByIdAndStudentId(id, studentId).get();
      routeRepository.delete(route);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

}
