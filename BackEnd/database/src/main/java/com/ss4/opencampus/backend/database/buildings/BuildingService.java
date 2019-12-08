package com.ss4.opencampus.backend.database.buildings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class BuildingService
{
  @Autowired
  private BuildingRepository buildingRepository;

  public Boolean addSingleBuilding(Building building)
  {
    try
    {
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

  public Iterable<Building> getBuildings(String searchType, Object param, Object param2)
  {
    switch (searchType)
    {
      case "nameStartsWith":
        return buildingRepository.findAllByBuildingNameStartingWith((String) param);
      case "abbreviationStartsWith":
        return buildingRepository.findAllByAbbreviationStartingWith((String) param);
      case "name":
        return buildingRepository.findByBuildingName((String) param);
      case "abbreviation":
        return buildingRepository.findByAbbreviation((String) param);
      case "address":
        return buildingRepository.findByAddress((String) param);
      case "location":
        Double lat = Double.parseDouble((String) param);
        Double lon = Double.parseDouble((String) param2);
        return buildingRepository.findByLatitAndLongit(lat, lon);
      default:
        return buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName"));
    }
  }

  public Optional<Building> getBuildingById(Integer id)
  {
    return buildingRepository.findById(id);
  }

  public Boolean putBuilding(Building building, Integer id)
  {
    try
    {
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

  public Boolean patchBuilding(Map<String, Object> patch, Integer id)
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

  public Boolean deleteBuilding(Integer id)
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
