package com.ss4.opencampus.backend.database.uspots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/uspots")
public class USpotController
{
  @Autowired
  private USpotRepository uSpotRepository;

  @PostMapping("/add")
  public @ResponseBody
  Map<String, Boolean> addNewUSpot(@RequestBody USpot uSpot)
  {
    try
    {
      // will have to change to save/add here when saving picture
      uSpotRepository.save(uSpot);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  /*
  This will be refactored when Image-Getting is implemented.
   */
  @GetMapping(path = "/search/{searchType}")
  public @ResponseBody
  Iterable<USpot> getUSpotLists(@PathVariable String searchType, @RequestParam(required = false) Object param1, @RequestParam(required = false) Object param2)
  {
    switch (searchType)
    {
      case "name":
        return uSpotRepository.findByUsName((String) param1);
      case "nameStartsWith":
        return uSpotRepository.findAllByUsNameStartingWith((String) param1);
      case "category":
        return uSpotRepository.findAllByUsCategory((String) param1);
      case "minRating":
        return uSpotRepository.findAllByUsRatingGreaterThanEqual((Double) param1);
      default:
        return uSpotRepository.findAll(new Sort(Sort.Direction.ASC, "usName"));
    }
  }

  @GetMapping(path = "/search/id/{id}")
  public @ResponseBody
  Optional<USpot> getUSpotById(@PathVariable Integer id)
  {
    return uSpotRepository.findById(id);
  }
}
