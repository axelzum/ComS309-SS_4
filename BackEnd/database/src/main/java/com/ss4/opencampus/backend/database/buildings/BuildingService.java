package com.ss4.opencampus.backend.database.buildings;

import com.ss4.opencampus.backend.database.OpenCampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 *
 * Service Class for Buildings
 */
@Service
public class BuildingService implements OpenCampusService
{
  @Autowired
  private BuildingRepository buildingRepository;

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

  @Override
  public Optional<Building> getById(Integer id)
  {
    return buildingRepository.findById(id);
  }

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
