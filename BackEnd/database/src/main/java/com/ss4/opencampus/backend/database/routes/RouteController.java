package com.ss4.opencampus.backend.database.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Controller Class that handles Requests that deal with Routes
 */
@RestController
public class RouteController
{
  @Autowired
  private RouteService routeService;

  /**
   * POSTs a new Route to the DB
   *
   * @param studentId
   *         id of Student the Route is for
   * @param route
   *         JSON of Route to be added
   *
   * @return JSON response of success or failure
   */
  @PostMapping(path = "/students/{studentId}/routes")
  public @ResponseBody
  Map<String, Boolean> addRoute(@PathVariable(value = "studentId") Integer studentId, @RequestBody Route route)
  {
    return Collections.singletonMap("response", routeService.add(studentId, route));
  }

  /**
   * GET Request to get list of Routes that the given Student has saved
   *
   * @param studentId
   *         id of Student looking for Routes
   * @param searchType
   *         Way to organize Routes
   * @param param
   *         optional param
   *
   * @return Iterable JSON list of Routes
   */
  @GetMapping(path = "/students/{studentId}/routes/{searchType}")
  public @ResponseBody
  Iterable<Route> getRoutes(@PathVariable(value = "studentId") Integer studentId,
                            @PathVariable(value = "searchType") String searchType,
                            @RequestParam(required = false) String param)
  {
    return routeService.getRoutes(studentId, searchType, param);
  }

  /**
   * GET Request to find a Route by its ID
   *
   * @param id
   *         id of Route to find
   * @param studentId
   *         id of Student the Route is for
   *
   * @return Route JSON obj
   */
  @GetMapping(path = "/students/{studentId}/routes/id/{id}")
  public @ResponseBody
  Optional<Route> getRouteById(@PathVariable(value = "id") Integer id,
                               @PathVariable(value = "studentId") Integer studentId)
  {
    return routeService.getById(id, studentId);
  }

  /**
   * PUT Request that Updates the Route with the given info
   *
   * @param id
   *         id of Route to be updated
   * @param studentId
   *         id of Student who owns the Route
   * @param newRoute
   *         New information for the Route
   *
   * @return JSON formatted response notifying Frontend of success or failure
   */
  @PutMapping(path = "/students/{studentId}/routes/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateRoute(@PathVariable(value = "id") Integer id,
                                   @PathVariable(value = "studentId") Integer studentId,
                                   @RequestBody Route newRoute)

  {
    return Collections.singletonMap("response", routeService.put(id, studentId, newRoute));
  }

  /**
   * PATCH Request to update a part of an existing Route
   *
   * @param id
   *         id of Route to be updated
   * @param studentId
   *         id of Student that owns the Route
   * @param patch
   *         JSON formatted map with info to be put into the Route
   *
   * @return JSON formatted response notifying Frontend of success or failure
   */
  @PatchMapping(path = "/students/{studentId}/routes/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchRoute(@PathVariable(value = "id") Integer id,
                                  @PathVariable(value = "studentId") Integer studentId,
                                  @RequestBody Map<String, Object> patch)
  {
    return Collections.singletonMap("response", routeService.patch(id, studentId, patch));
  }

  /**
   * DELETE Request that deletes a Route from the database.
   *
   * @param id
   *         id of Route to be deleted
   * @param studentId
   *         id of Student the Route is for
   *
   * @return JSON formatted response notifying Frontend of success or failure
   */
  @DeleteMapping(path = "/students/{studentId}/routes/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteRoute(@PathVariable(value = "id") Integer id,
                                   @PathVariable(value = "studentId") Integer studentId)
  {
    return Collections.singletonMap("response", routeService.delete(id, studentId));
  }
}
