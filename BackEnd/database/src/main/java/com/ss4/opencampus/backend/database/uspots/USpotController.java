package com.ss4.opencampus.backend.database.uspots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 */
@RestController
@RequestMapping(path = "/uspots")
public class USpotController
{
  @Autowired
  private USpotRepository uSpotRepository;

  private final String path = "PATH_TO_PHOTOS_HERE";

  @PostMapping("/add")
  public @ResponseBody
  Map<String, Boolean> addNewUSpot(@RequestBody USpot uSpot)
  {
    try
    {
      //janky fix for pathname setting but works for now.
      int length = uSpotRepository.findAll().size();
      uSpot.setUsImagePath(path + (length + 1) + ".png");
      //works!!
      byte[] bytes = uSpot.getPicBytes();
      FileOutputStream fos = new FileOutputStream(uSpot.getUsImagePath());
      fos.write(bytes);
      fos.close();
      uSpotRepository.save(uSpot);
    }
    catch (IOException | DataAccessException ex)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  @GetMapping(path = "/search/{searchType}")
  public @ResponseBody
  Iterable<USpot> getUSpotLists(@PathVariable String searchType, @RequestParam(required = false) Object param1,
                                @RequestParam(required = false) Object param2) throws IOException
  {
    Iterable<USpot> uList;
    switch (searchType)
    {
      case "name":
        uList = uSpotRepository.findByUsName((String) param1);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      case "nameStartsWith":
        uList = uSpotRepository.findAllByUsNameStartingWith((String) param1);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      case "category":
        uList = uSpotRepository.findAllByUsCategory((String) param1);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      case "minRating":
        uList = uSpotRepository.findAllByUsRatingGreaterThanEqual((Double) param1);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      default:
        uList = uSpotRepository.findAll(new Sort(Sort.Direction.ASC, "usName"));
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
    }
  }

  @GetMapping(path = "/search/id/{id}")
  public @ResponseBody
  Optional<USpot> getUSpotById(@PathVariable Integer id) throws IOException
  {
    Optional<USpot> u = uSpotRepository.findById(id);
    if (u.isPresent())
      u.get().setPicBytes(pathToBytes(u.get().getUsImagePath()));
    return u;
  }

  private byte[] pathToBytes(String picPath) throws IOException
  {
    return Files.readAllBytes(Paths.get(picPath));
  }
}
