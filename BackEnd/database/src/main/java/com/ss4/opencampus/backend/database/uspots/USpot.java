package com.ss4.opencampus.backend.database.uspots;

import javax.persistence.*;

@Entity
@Table(name = "USpots")
public class USpot
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer usID;

  @Column(name = "USpotName")
  private String usName;

  @Column(name = "Rating")
  private Double usRating;

  //used internally to keep track of overall rating of USpot
  @Transient
  private Integer ratingCount;

  //used internally to keep track of overall rating of USpot
  @Transient
  private Double ratingTotal;

  @Column(name = "Latitude")
  private Double usLatit;

  @Column(name = "Longitude")
  private Double usLongit;

  @Column(name = "Category")
  private String usCategory;

  @Column(name = "Picture_Directory")
  private String usImagePath;

  @Transient
  private byte[] picBytes;

  public USpot()
  {
    // default value for rating. Can be set in MySQL
    this.setUsRating(2.5);
  }

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

  public void setUsRating(Double usRating)
  {
    if(usRating > 5.0)
      usRating = 5.0;
    if(usRating < 0.0)
      usRating = 0.0;
    this.usRating = usRating;
    ratingCount = 1;
    ratingTotal = usRating;
  }

  // Will be used by USpotController in PATCH/PUT Request. i.e. when new users
  // add their new rating to the USpot
  public void updateRating(Double nextRating)
  {
    if(nextRating > 5.0)
      nextRating = 5.0;
    if(nextRating < 0.0)
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
