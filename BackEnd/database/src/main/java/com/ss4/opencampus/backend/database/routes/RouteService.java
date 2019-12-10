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
 *
 * Service Class for Routes
 */
@Service
public class RouteService
{
  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private StudentRepository studentRepository;

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

  public Iterable<Route> getRoutes(Integer studentId, String searchType, String param)
  {
    switch (searchType)
    {
      case "rtName":
        return routeRepository.findByRtNameAndStudentId(param, studentId);
      default:
        return routeRepository.findAllByStudentId(studentId, new Sort(Sort.Direction.ASC, "rtName"));
    }
  }

  public Optional<Route> getById(Integer id, Integer studentId)
  {
    return routeRepository.findByIdAndStudentId(id, studentId);
  }

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

  public Boolean patch(Integer id, Integer studentId, Map<String, Object> patch)
  {
    try
    {
      Route route = routeRepository.findByIdAndStudentId(id, studentId).get();
      if(patch.containsKey("rtName"))
      {
        route.setRtName((String) patch.get("rtName"));
      }
      if(patch.containsKey("originLat"))
      {
        route.setOriginLat(((Double) patch.get("originLat")));
      }
      if(patch.containsKey("originLng"))
      {
        route.setOriginLng(((Double) patch.get("originLng")));
      }
      if(patch.containsKey("destLat"))
      {
        route.setDestLat(((Double) patch.get("destLat")));
      }
      if(patch.containsKey("destLng"))
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
