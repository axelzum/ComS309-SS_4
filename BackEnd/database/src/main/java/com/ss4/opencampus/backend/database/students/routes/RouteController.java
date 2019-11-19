package com.ss4.opencampus.backend.database.students.routes;

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
 */
@RestController
public class RouteController
{
  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private StudentRepository studentRepository;

  @PostMapping(path = "/students/{studentId}/routes")
  public @ResponseBody
  Map<String, Boolean> addRoute(@PathVariable(value = "studentId") Integer studentId, @RequestBody Route route)
  {
    try
    {
      Student student = studentRepository.findById(studentId).get();
      route.setStudent(student);
      routeRepository.save(route);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  @GetMapping(path = "/students/{studentId}/routes/{searchType}")
  public @ResponseBody
  Iterable<Route> getRoutes(@PathVariable(value = "studentId") Integer studentId,
                            @PathVariable(value = "searchType") String searchType,
                            @RequestParam(required = false) String param)
  {
    switch (searchType)
    {
      case "rtName":
        return routeRepository.findByRtNameAndStudentId(param, studentId);
      default:
        return routeRepository.findAllByStudentId(studentId, new Sort(Sort.Direction.ASC, "rtName"));
    }
  }

  @GetMapping(path = "/students/{studentId}/routes/id/{id}")
  public @ResponseBody
  Optional<Route> getRouteById(@PathVariable(value = "id") Integer id,
                               @PathVariable(value = "studentId") Integer studentId)
  {
    return routeRepository.findByIdAndStudentId(id, studentId);
  }

  @PutMapping(path = "/students/{studentId}/routes/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateRoute(@PathVariable(value = "id") Integer id,
                                   @PathVariable(value = "studentId") Integer studentId,
                                   @RequestBody Route newRoute)

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
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  @PatchMapping(path = "/students/{studentId}/routes/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchRoute(@PathVariable(value = "id") Integer id,
                                  @PathVariable(value = "studentId") Integer studentId,
                                  @RequestBody Map<String, Object> patch)
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
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  @DeleteMapping(path = "/students/{studentId}/routes/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteRoute(@PathVariable(value = "id") Integer id,
                                   @PathVariable(value = "studentId") Integer studentId)
  {
    try
    {
      Route route = routeRepository.findByIdAndStudentId(id, studentId).get();
      routeRepository.delete(route);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }
}
