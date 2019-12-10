package com.ss4.opencampus.backend.database.uspots;

import com.ss4.opencampus.backend.database.OpenCampusEntity;

import javax.persistence.*;

/**
 * @author Willis Knox
 * <p>
 * This is the table for USpots on the MySQL server. Anything with the @Transient annotation will NOT be directly in the
 * database.
 */
@Entity
@Table(name = "USpots")
public class USpot implements OpenCampusEntity
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer usID;


  /*
   * Remember to use the instance variable names in the JSON!! Not the names in the MySQL table!!!
   */

  /**
   * Provided by Frontend
   */
  @Column(name = "USpotName")
  private String usName;

  /**
   * Provided by Frontend
   */
  @Column(name = "Rating")
  private Double usRating;

  /**
   * NOT provided by Frontend. Backend determines what this will be. Not saved to DB
   */
  @Column(name = "Number_Ratings")
  private Integer ratingCount;

  /**
   * NOT provided by Frontend. Backend determines what this will be. Not saved to DB
   */
  @Column(name = "Total_Rating")
  private Double ratingTotal;

  /**
   * Provided by Frontend
   */
  @Column(name = "Latitude")
  private Double usLatit;

  /**
   * Provided by Frontend
   */
  @Column(name = "Longitude")
  private Double usLongit;

  /**
   * Provided by Frontend... will probably be in a ComboBox/ListBox of options
   */
  @Column(name = "Category")
  private String usCategory;

  /**
   * NOT provided by Frontend. Backend determines what this will be. Saved to DB
   */
  @Column(name = "Picture_Directory")
  private String usImagePath;

  /**
   * Provided by Frontend
   */
  @Column(name = "Floor")
  private String floor;

  /**
   * References Building table. Used if USpot is located inside a building
   */
  @Column(name = "uspot_building_id")
  private Integer buildingId;

  /**
   * References the Student that made the USpot
   */
  @Column(name = "uspot_student_id")
  private Integer studentId;

  /**
   * Provided by Frontend but not saved directly to database
   */
  @Transient
  private byte[] picBytes;

  /**
   * Constructs a default USpot
   */
  public USpot()
  {

  }

  /**
   * Constructor for dependency injection
   *
   * @param name
   *         name for USpot
   * @param rating
   *         Rating for USpot
   * @param lat
   *         latitude of USpot
   * @param lon
   *         longitude of USpot
   * @param category
   *         category USpot is in
   * @param bytes
   *         byte array of image of USpot
   * @param floor
   *         floor level USpot is on
   * @param buildingId
   *         building USpot is in
   * @param studentId
   *         Id of student that made USpot
   */
  public USpot(String name, Double rating, Double lat, Double lon, String category, byte[] bytes, String floor,
               Integer buildingId, Integer studentId)
  {
    this.usName = name;
    this.usRating = rating;
    this.usLatit = lat;
    this.usLongit = lon;
    this.usCategory = category;
    this.picBytes = bytes;
    this.floor = floor;
    this.buildingId = buildingId;
    this.studentId = studentId;
    this.ratingCount = 1;
    this.ratingTotal = usRating;
  }

  public Integer getStudentId()
  {
    return studentId;
  }

  public void setStudentId(Integer studentId)
  {
    this.studentId = studentId;
  }

  /**
   * Gets the ID of the current USpot
   *
   * @return the id of the USpot
   */
  public Integer getId()
  {
    return usID;
  }

  /**
   * Sets the ID of the current USpot
   *
   * @param usID
   *         new ID for the USpot to have
   */
  public void setId(Integer usID)
  {
    this.usID = usID;
  }

  /**
   * Gets the name of the current USpot
   *
   * @return the name of the USpot
   */
  public String getUsName()
  {
    return usName;
  }

  /**
   * Sets the name of the USpot to the given value
   *
   * @param usName
   *         Value of new name
   */
  public void setUsName(String usName)
  {
    this.usName = usName;
  }

  /**
   * Gets the overall rating of the current USpot
   *
   * @return the rating of the USpot
   */
  public Double getUsRating()
  {
    return usRating;
  }

  /**
   * Initializes the amount of ratings for the given USpot to 1 on the total rating amount to the given rating.
   * ratingCount and ratingTotal are used to calculate the average rating that will be eventually saved in the
   * database.
   *
   * @param usRating
   *         Rating the USpot is given
   */
  public void setUsRating(Double usRating)
  {
    if (usRating > 5.0)
      usRating = 5.0;
    if (usRating < 0.0)
      usRating = 0.0;
    this.usRating = usRating;
    ratingCount = 1;
    ratingTotal = usRating;
  }

  /**
   * Will be used by USpotController in PATCH/PUT Request. i.e. when new users add their new rating to the USpot
   * Calculates the average rating of the USpot from all users and saves that number to the DB
   *
   * @param nextRating
   *         Rating the User gave to the EXISTING USpot.
   */
  public void updateRating(Double nextRating)
  {
    if (nextRating > 5.0)
      nextRating = 5.0;
    if (nextRating < 0.0)
      nextRating = 0.0;
    ratingCount++;
    ratingTotal += nextRating;
    usRating = ratingTotal / (double) ratingCount;
  }

  /**
   * Gets the latitude of the current USpot
   *
   * @return the latitude of the USpot
   */
  public Double getUsLatit()
  {
    return usLatit;
  }

  /**
   * Sets the latitude of the USpot to a new value
   *
   * @param usLatit
   *         new latitude for the USpot
   */
  public void setUsLatit(Double usLatit)
  {
    this.usLatit = usLatit;
  }

  /**
   * Gets the longitude of the current USpot
   *
   * @return the longitude of the USpot
   */
  public Double getUsLongit()
  {
    return usLongit;
  }

  /**
   * Sets the longitude of the USpot to a new value
   *
   * @param usLongit
   *         new longitude for the USpot
   */
  public void setUsLongit(Double usLongit)
  {
    this.usLongit = usLongit;
  }

  /**
   * Gets the category of the current USpot
   *
   * @return the category of the USpot
   */
  public String getUsCategory()
  {
    return usCategory;
  }

  /**
   * Sets the category to another category
   *
   * @param usCategory
   *         new category for the USpot
   */
  public void setUsCategory(String usCategory)
  {
    this.usCategory = usCategory;
  }

  /**
   * Gets the image path of the current USpot
   *
   * @return the image path of the USpot
   */
  public String getUsImagePath()
  {
    return usImagePath;
  }

  /**
   * Sets the image path to a new value
   *
   * @param usImagePath
   *         New path the the image for the USpot
   */
  public void setUsImagePath(String usImagePath)
  {
    this.usImagePath = usImagePath;
  }

  /**
   * Gets the byte[] of the current USpot
   *
   * @return the byte[] of the USpot
   */
  public byte[] getPicBytes()
  {
    return picBytes;
  }

  /**
   * Sets the byte[] to a new value. Changes the picture
   *
   * @param arr
   *         New byte[] for the USpot
   */
  public void setPicBytes(byte[] arr)
  {
    picBytes = arr;
  }

  /**
   * Gets the number of times the USpot has been rated. Used in avg calculation
   *
   * @return the number of ratings for the USpot
   */
  public Integer getRatingCount()
  {
    return ratingCount;
  }

  /**
   * Sets the number of times a USpot has been rated.
   *
   * @param ratingCount
   *         New number of times the USpot has been rated
   */
  public void setRatingCount(Integer ratingCount)
  {
    this.ratingCount = ratingCount;
  }

  /**
   * Gets the total number of the ratings added together. Used in avg calculation
   *
   * @return the rating total of the USpot
   */
  public Double getRatingTotal()
  {
    return ratingTotal;
  }

  /**
   * Sets the sum of all ratings of the USpot
   *
   * @param ratingTotal
   *         New sum for the ratings.
   */
  public void setRatingTotal(Double ratingTotal)
  {
    this.ratingTotal = ratingTotal;
  }

  /**
   * Gets the floor that the USpot is on. Can be null
   *
   * @return Floor the USpot is on.
   */
  public String getFloor()
  {
    return floor;
  }

  /**
   * Sets the floor the USpot is on.
   *
   * @param floor
   *         New floor for the USpot
   */
  public void setFloor(String floor)
  {
    this.floor = floor;
  }

  /**
   * Gets the ID of the Building the USpot is in
   *
   * @return ID of Building that the USpot is in
   */
  public Integer getBuildingId()
  {
    return buildingId;
  }

  /**
   * Sets the ID of the Building that the USpot is in. Would be used if the USpot was incorrectly placed in a building.
   *
   * @param buildingId
   *         New ID for Building
   */
  public void setBuildingId(Integer buildingId)
  {
    this.buildingId = buildingId;
  }


}
