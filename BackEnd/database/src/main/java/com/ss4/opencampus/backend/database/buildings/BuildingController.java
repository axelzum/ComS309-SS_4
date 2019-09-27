package com.ss4.opencampus.backend.database.buildings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/buildings")
public class BuildingController
{
  @Autowired
  private BuildingRepository buildingRepository;

  @PostMapping(path = "/add")
  public @ResponseBody
  String addSingleBuilding(@RequestBody Building building)
  {
    try
    {
      buildingRepository.save(building);
    }
    catch (Exception e)
    {
      return "Failed to add building to Buildings table";
    }
    return "New building added to Buildings table";
  }

  @PostMapping(path = "/addMultiple")
  public @ResponseBody
  String addMultipleBuildings(@RequestBody Building[] buildings)
  {
    int addedCnt = 0;
    try
    {
      for (Building b : buildings)
      {
        buildingRepository.save(b);
        addedCnt++;
      }
    }
    catch (Exception e)
    {
      return "Ran into error. Only added " + addedCnt + " building(s) to the Buildings table";
    }
    return "Added " + addedCnt + " buildings to the Buildings table.";
  }

  @GetMapping(path = "/search/all") // should be alphabetical
  public @ResponseBody
  Iterable<Building> getAllBuildings()
  {
    // use Java code names for HttpRequests. Not MySQL names
    return buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName"));
  }

  @GetMapping(path = "/search/name")
  public @ResponseBody
  Building getBuildingByName(@RequestParam String name)
  {
    return buildingRepository.findByBuildingName(name);
  }

  @GetMapping(path = "/search/nameStartsWith")
  public @ResponseBody
  Iterable<Building> getBuildingsStartingWith(@RequestParam String startsWith)
  {
    return buildingRepository.findAllByBuildingNameStartingWith(startsWith);
  }

  @GetMapping("/search/abbreviation")
  public @ResponseBody
  Building getBuildingByAbbrev(@RequestParam String abbreviation)
  {
    return buildingRepository.findByAbbreviation(abbreviation);
  }

  @GetMapping(path = "/search/abbreviationStartsWith")
  public @ResponseBody
  Iterable<Building> getBuildingsByAddressStartingWith(@RequestParam String startsWith)
  {
    return buildingRepository.findAllByAbbreviationStartingWith(startsWith);
  }

  @GetMapping(path = "/search/address")
  public @ResponseBody
  Building getBuildingByAddress(@RequestParam String address)
  {
    return buildingRepository.findByAddress(address);
  }

  @GetMapping(path = "/search/id/{id}")
  public @ResponseBody
  Optional<Building> getBuildingById(@PathVariable Integer id)
  {
    return buildingRepository.findById(id);
  }


  //TODO: Get this method working

  //  @GetMapping(path = "/search/location")
//  public @ResponseBody Building getBuildingByLocation(@RequestParam Double latit, @RequestParam Double longit)
//  {
//    return buildingRepository.findByLatitAndLongit(latit, longit);
//  }
}
