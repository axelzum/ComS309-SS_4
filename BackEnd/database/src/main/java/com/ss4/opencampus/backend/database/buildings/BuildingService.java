package com.ss4.opencampus.backend.database.buildings;

import com.ss4.opencampus.backend.database.OpenCampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Service Class for Buildings
 */
@Service
public class BuildingService implements OpenCampusService
{
  @Autowired
  private BuildingRepository buildingRepository;

  /**
   * Adds a single Building to the DB
   *
   * @param t
   *         Building to be added
   * @param <T>
   *         Generic = Building
   *
   * @return Boolean of success or failure
   */
  @Override
  public <T> Boolean add(T t)
  {
    try
    {
      Building building = (Building) t;
      buildingRepository.save(building);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Adds multiple buildings to the DB at once
   *
   * @param buildings
   *         array of Buildings to be added
   *
   * @return Boolean of success or failure
   */
  public Boolean addMultipleBuildings(Building[] buildings)
  {
    try
    {
      for (Building building : buildings)
      {
        buildingRepository.save(building);
      }
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Method that searches DB in different ways depending on given parameters
   *
   * @param searchType
   *         type of search to perform
   * @param param1
   *         optional param
   * @param param2
   *         optional param
   *
   * @return Iterable list of Building objects
   */
  public Iterable<Building> getBuildings(String searchType, Object param1, Object param2)
  {
    switch (searchType)
    {
      case "nameStartsWith":
        return buildingRepository.findAllByBuildingNameStartingWith((String) param1);
      case "abbreviationStartsWith":
        return buildingRepository.findAllByAbbreviationStartingWith((String) param1);
      case "name":
        return buildingRepository.findByBuildingName((String) param1);
      case "abbreviation":
        return buildingRepository.findByAbbreviation((String) param1);
      case "address":
        return buildingRepository.findByAddress((String) param1);
      case "location":
        Double lat = Double.parseDouble((String) param1);
        Double lon = Double.parseDouble((String) param2);
        return buildingRepository.findByLatitAndLongit(lat, lon);
      default:
        return buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName"));
    }
  }

  /**
   * Searches DB for Building with given id
   *
   * @param id
   *         id of Building to find
   *
   * @return Building object
   */
  @SuppressWarnings("unchecked")
  @Override
  public Optional<Building> getById(Integer id)
  {
    return buildingRepository.findById(id);
  }

  /**
   * Updates an existing building obj in DB with info. Info not given is set to NULL
   *
   * @param t
   *         Updated info
   * @param id
   *         id of Building to be updated
   * @param <T>
   *         Generic = Building
   *
   * @return Boolean of success or failure
   */
  @Override
  public <T> Boolean put(T t, Integer id)
  {
    try
    {
      Building building = (Building) t;
      Building b = buildingRepository.findById(id).get();
      b.setBuildingName(building.getBuildingName());
      b.setAddress(building.getAddress());
      b.setAbbreviation(building.getAbbreviation());
      b.setLatit(building.getLatit());
      b.setLongit(building.getLongit());
      b.setFloorCnt(building.getFloorCnt());
      buildingRepository.save(b);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Updates an existing building obj in DB with info. Info not given remains the same
   *
   * @param patch
   *         Map with info to be updated
   * @param id
   *         id of Building to be updated
   *
   * @return Boolean of success or failure
   */
  @Override
  public Boolean patch(Map<String, Object> patch, Integer id)
  {
    try
    {
      Building building = buildingRepository.findById(id).get();
      if (patch.containsKey("buildingName"))
      {
        building.setBuildingName((String) patch.get("buildingName"));
      }
      if (patch.containsKey("address"))
      {
        building.setAddress((String) patch.get("address"));
      }
      if (patch.containsKey("abbreviation"))
      {
        building.setAbbreviation((String) patch.get("abbreviation"));
      }
      if (patch.containsKey("latit"))
      {
        building.setLatit(((Double) patch.get("latit")));
      }
      if (patch.containsKey("longit"))
      {
        building.setLongit(((Double) patch.get("longit")));
      }
      if (patch.containsKey("floorCnt"))
      {
        building.setFloorCnt(((Integer) patch.get("floorCnt")));
      }
      buildingRepository.save(building);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Finds the Building with the given ID and deletes it from the database
   *
   * @param id
   *         id of Building to be deleted
   *
   * @return Boolean of success or failure
   */
  @Override
  public Boolean delete(Integer id)
  {
    try
    {
      buildingRepository.deleteById(id);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
}
