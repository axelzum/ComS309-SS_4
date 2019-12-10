package com.ss4.opencampus.backend.database.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 *
 * Controller Class that handles Requests that deal with Routes
 */
@RestController
public class RouteController
{
  @Autowired
  private RouteService routeService;

  @PostMapping(path = "/students/{studentId}/routes")
  public @ResponseBody
  Map<String, Boolean> addRoute(@PathVariable(value = "studentId") Integer studentId, @RequestBody Route route)
  {
    return Collections.singletonMap("response", routeService.add(studentId, route));
  }

  @GetMapping(path = "/students/{studentId}/routes/{searchType}")
  public @ResponseBody
  Iterable<Route> getRoutes(@PathVariable(value = "studentId") Integer studentId,
                            @PathVariable(value = "searchType") String searchType,
                            @RequestParam(required = false) String param)
  {
    return routeService.getRoutes(studentId, searchType, param);
  }

  @GetMapping(path = "/students/{studentId}/routes/id/{id}")
  public @ResponseBody
  Optional<Route> getRouteById(@PathVariable(value = "id") Integer id,
                               @PathVariable(value = "studentId") Integer studentId)
  {
    return routeService.getById(id, studentId);
  }

  @PutMapping(path = "/students/{studentId}/routes/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateRoute(@PathVariable(value = "id") Integer id,
                                   @PathVariable(value = "studentId") Integer studentId,
                                   @RequestBody Route newRoute)

  {
    return Collections.singletonMap("response", routeService.put(id, studentId, newRoute));
  }

  @PatchMapping(path = "/students/{studentId}/routes/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchRoute(@PathVariable(value = "id") Integer id,
                                  @PathVariable(value = "studentId") Integer studentId,
                                  @RequestBody Map<String, Object> patch)
  {
    return Collections.singletonMap("response", routeService.patch(id, studentId, patch));
  }

  @DeleteMapping(path = "/students/{studentId}/routes/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteRoute(@PathVariable(value = "id") Integer id,
                                   @PathVariable(value = "studentId") Integer studentId)
  {
    return Collections.singletonMap("response", routeService.delete(id, studentId));
  }
}
