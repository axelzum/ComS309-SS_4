package com.ss4.opencampus.backend.database.uspots;

import com.ss4.opencampus.backend.database.OpenCampusService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Service Class for USpots
 */
@Service
public class USpotService implements OpenCampusService
{
  @Autowired
  private USpotRepository uSpotRepository;

  /**
   * Default path for a USpot image
   */
  private final String path = "/target/images/uspots/";

  /**
   * Adds a USpot to the DB
   *
   * @param t
   *         USpot to be added
   * @param <T>
   *         Generic = USpot
   *
   * @return Boolean of success or failure
   */
  @Override
  public <T> Boolean add(T t)
  {
    try
    {
      USpot uSpot = (USpot) t;
      byte[] bytes = uSpot.getPicBytes();
      int length = uSpotRepository.findAll().size();
      if (bytes != null)
      {
        uSpot.setUsImagePath(path + "uspot" + (length + 1) + ".png");
        FileOutputStream fos = new FileOutputStream(uSpot.getUsImagePath());
        fos.write(bytes);
        fos.close();
      }
      else
        uSpot.setUsImagePath("/target/images/noimage.png");
      uSpot.setRatingCount(1);
      uSpot.setRatingTotal(uSpot.getUsRating());
      uSpotRepository.save(uSpot);
    }
    catch (IOException | DataAccessException ex)
    {
      return false;
    }
    return true;
  }

  /**
   * Method used to search DB and return lists of USpots depending on given params
   *
   * @param searchType
   *         way to search DB
   * @param param1
   *         optional param
   * @param param2
   *         optional param
   *
   * @return Iterable list of USpot objs
   *
   * @throws IOException
   *         could throw an exception if image is not present
   */
  public Iterable<USpot> getUSpots(String searchType, Object param1, Object param2) throws IOException
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
        Double rating = Double.parseDouble((String) param1);
        uList = uSpotRepository.findAllByUsRatingGreaterThanEqual(rating);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      case "building":
        Integer buildingId = Integer.parseInt((String) param1);
        String floor = (String) param2;
        uList = uSpotRepository.findAllByBuildingIdAndFloor(buildingId, floor);
        for (USpot u : uList)
          u.setPicBytes(pathToBytes(u.getUsImagePath()));
        return uList;
      case "student":
        Integer studentId = Integer.parseInt((String) param1);
        uList = uSpotRepository.findAllByStudentId(studentId, new Sort(Sort.Direction.ASC, "usName"));
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

  /**
   * Searches DB for USpot with given ID
   *
   * @param id
   *         id of USpot to find
   *
   * @return USpot object
   */
  @SuppressWarnings("unchecked")
  @Override
  public Optional<USpot> getById(Integer id)
  {
    Optional<USpot> u = uSpotRepository.findById(id);
    if (u.isPresent())
    {
      try
      {
        u.get().setPicBytes(pathToBytes(u.get().getUsImagePath()));
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return u;
  }

  /**
   * Updates a USpot with given info. Info not given is set to NULL.
   *
   * @param t
   *         Updated USpot information
   * @param id
   *         id of USpot to be updated
   * @param <T>
   *         Generic = USpot
   *
   * @return Boolean of success or failure
   */
  @Override
  public <T> Boolean put(T t, Integer id)
  {
    try
    {
      USpot newUSpot = (USpot) t;
      USpot u = uSpotRepository.findById(id).get();
      u.setUsName(newUSpot.getUsName());
      u.setUsCategory(newUSpot.getUsCategory());
      u.setUsLatit(newUSpot.getUsLatit());
      u.setUsLongit(newUSpot.getUsLongit());
      u.setUsRating(newUSpot.getUsRating());
      u.setFloor(newUSpot.getFloor());
      u.setStudentId(newUSpot.getStudentId());
      u.setBuildingId(newUSpot.getBuildingId());
      byte[] bytes = newUSpot.getPicBytes();
      u = newUSpotImage(u, bytes);
      uSpotRepository.save(u);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Updates a USpot with given info. Info not given remains the same
   *
   * @param patch
   *         Map with updated information
   * @param id
   *         id of USpot to be updated
   *
   * @return Boolean of success or failure
   */
  @Override
  public Boolean patch(Map<String, Object> patch, Integer id)
  {
    try
    {
      USpot u = uSpotRepository.findById(id).get();
      if (patch.containsKey("usName"))
      {
        u.setUsName((String) patch.get("usName"));
      }
      if (patch.containsKey("usRating"))
      {
        u.updateRating(((Double) patch.get("usRating"))); //calculate average rating
      }
      if (patch.containsKey("usLatit"))
      {
        u.setUsLatit(((Double) patch.get("usLatit")));
      }
      if (patch.containsKey("usLongit"))
      {
        u.setUsLongit(((Double) patch.get("usLongit")));
      }
      if (patch.containsKey("usCategory"))
      {
        u.setUsCategory((String) patch.get("usCategory"));
      }
      if (patch.containsKey("floor"))
      {
        u.setFloor((String) patch.get("floor"));
      }
      if (patch.containsKey("buildingId"))
      {
        u.setBuildingId(((Integer) patch.get("buildingId")));
      }
      if (patch.containsKey("studentId"))
      {
        u.setStudentId(((Integer) patch.get("studentId")));
      }
      if (patch.containsKey("picBytes"))
      {
        byte[] bytes = Base64.decodeBase64((String) patch.get("picBytes"));
        u = newUSpotImage(u, bytes);
      }
      uSpotRepository.save(u);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Finds the USpot with the given ID and deletes it from the database
   *
   * @param id
   *         id of USpot to be deleted
   *
   * @return Boolean of success or failure
   */
  @Override
  public Boolean delete(Integer id)
  {
    try
    {
      USpot u = uSpotRepository.findById(id).get();
      File file = new File(u.getUsImagePath());
      if (!(file.getAbsolutePath().equals("/target/images/noimage.png")))
        file.delete();
      uSpotRepository.deleteById(id);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Helper method to retrieve the image bytes of an image that is tied to a USpot. Used in GET Requests
   *
   * @param picPath
   *         Path in DB to look into to find the image
   *
   * @return byte array that is the image data
   *
   * @throws IOException
   *         Could potentially through an error if given a non-valid path.
   */
  private byte[] pathToBytes(String picPath) throws IOException
  {
    if (picPath == null)
      return null;
    return Files.readAllBytes(Paths.get(picPath));
  }

  /**
   * Helper method that is used by PUT and PATCH when trying to update the USpot picture. If the USpot is using
   * "noimage.png" as the image, we DON'T want to replace it and if it is using another image, we can go ahead and
   * overwrite it.
   * <p>
   * If the byte[] is null, it will simply keep the old image that is currently linked to the USpot.
   *
   * @param u
   *         USpot who's image is being changed
   * @param bytes
   *         array of base64 bytes of the new image
   *
   * @return The USpot so it can be saved to the table
   *
   * @throws IOException
   *         Could potentially through an error if given a non-valid path.
   */
  private USpot newUSpotImage(USpot u, byte[] bytes) throws IOException
  {
    if (bytes != null) //if null, just keep old picture
    {
      u.setPicBytes(bytes);
      FileOutputStream fos;
      if (u.getUsImagePath().equals("/target/images/noimage.png"))
      {
        u.setUsImagePath(path + "uspot_fresh_image_" + u.getId() + ".png");
        fos = new FileOutputStream(u.getUsImagePath());
      }
      else
        fos = new FileOutputStream(u.getUsImagePath(), false);
      fos.write(u.getPicBytes());
      fos.close();
    }
    return u;
  }

}
