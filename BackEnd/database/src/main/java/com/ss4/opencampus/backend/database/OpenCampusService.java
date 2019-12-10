package com.ss4.opencampus.backend.database;

import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Service Interface for the OpenCampus application. All services have similar methods so an interface to ensure these
 * methods are implemented was logical.
 */
public interface OpenCampusService
{
  <T> Boolean add(T t);

  <T> Optional<T> getById(Integer id);

  <T> Boolean put(T t, Integer id);

  Boolean patch(Map<String, Object> patch, Integer id);

  Boolean delete(Integer id);
}
