package com.ss4.opencampus.backend.database.uspots;

import javax.persistence.*;

/**
 * @author Willis Knox
 * <p>
 * This is the table for USpots on the MySQL server. Anything with the @Transient annotation will NOT be directly in the
 * database.
 */
@Entity
@Table(name = "USpots")
public class USpot
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
  @Transient
  private Integer ratingCount;

  /**
   * NOT provided by Frontend. Backend determines what this will be. Not saved to DB
   */
  @Transient
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
   * Provided by Frontend but not saved directly to database
   */
  @Transient
  private byte[] picBytes;

  public USpot()
  {
    // default value for rating. Can be set in MySQL
    this.setUsRating(2.5);
  }

  /*
   * Unless noted, everything below is a basic Getter/Setter method
   */

  public Integer getUsID()
  {
    return usID;
  }

  public void setUsID(Integer usID)
  {
    this.usID = usID;
  }

  public String getUsName()
  {
    return usName;
  }

  public void setUsName(String usName)
  {
    this.usName = usName;
  }

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
    double avgRating = ratingTotal / (double) ratingCount;
    this.setUsRating(avgRating);
  }

  public Double getUsLatit()
  {
    return usLatit;
  }

  public void setUsLatit(Double usLatit)
  {
    this.usLatit = usLatit;
  }

  public Double getUsLongit()
  {
    return usLongit;
  }

  public void setUsLongit(Double usLongit)
  {
    this.usLongit = usLongit;
  }

  public String getUsCategory()
  {
    return usCategory;
  }

  public void setUsCategory(String usCategory)
  {
    this.usCategory = usCategory;
  }

  public String getUsImagePath()
  {
    return usImagePath;
  }

  public void setUsImagePath(String usImagePath)
  {
    this.usImagePath = usImagePath;
  }

  public byte[] getPicBytes()
  {
    return picBytes;
  }

  public void setPicBytes(byte[] arr)
  {
    picBytes = arr;
  }

}
