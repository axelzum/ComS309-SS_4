package com.ss4.opencampus.backend.database;

/**
 * @author Willis Knox
 * <p>
 * Interface for all entities that will be in OpenCampus Database. Right now, the only thing an Entity HAS to have is an
 * ID, so all that needs to be implemented in every class that implements this inteface are getId() and setId().
 */
public interface OpenCampusEntity
{
  Integer getId();

  void setId(Integer id);
}
