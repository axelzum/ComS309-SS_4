package com.ss4.opencampus.backend.database.buildings;

import javax.persistence.*;

/**
 * @author Willis Knox
 * <p>This is a class for the Buildings table in the open_campus database</p>
 */
@Entity
@Table(name = "Buildings")
public class Building
{
  /**
   * Needs to be IDENTITY otherwise the IDs from other Tables will increment off of each other
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "Building_Name")
  private String buildingName;

  @Column(name = "Abbreviation")
  private String abbreviation;

  @Column(name = "Address")
  private String address;

  @Column(name = "Latitude")
  private Double latit;

  @Column(name = "Longitude")
  private Double longit;

  @Column(name = "Floor Count")
  private Integer floorCount;

  /**
   * Constructor for the Building class
   */
  public Building()
  {

  }

  /**
   * Gets the ID for the Building
   *
   * @return id of Building
   */
  public Integer getId()
  {
    return id;
  }

  /**
   * Sets the id of the Building to given value
   *
   * @param id
   *         new ID for Building
   */
  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * Gets the name for the Building
   *
   * @return The building's name
   */
  public String getBuildingName()
  {
    return buildingName;
  }

  /**
   * Sets the Building name to a new value
   *
   * @param buildingName
   *         New name for Building
   */
  public void setBuildingName(String buildingName)
  {
    this.buildingName = buildingName;
  }

  /**
   * Gets the abbreviation for the Building
   *
   * @return the Building's abbreviation
   */
  public String getAbbreviation()
  {
    return abbreviation;
  }

  /**
   * Sets the Building abbreviation to a new value
   *
   * @param abbreviation
   *         New abbreviation for the Building
   */
  public void setAbbreviation(String abbreviation)
  {
    this.abbreviation = abbreviation;
  }

  /**
   * Gets the Building's address
   *
   * @return the Building's address
   */
  public String getAddress()
  {
    return address;
  }

  /**
   * Sets the Building's address to a new value
   *
   * @param address
   *         New address for the Building
   */
  public void setAddress(String address)
  {
    this.address = address;
  }

  /**
   * Gets the Building's latitude
   *
   * @return the latitude of the Building
   */
  public Double getLatit()
  {
    return latit;
  }

  /**
   * Sets the Building's latitude to a new value
   *
   * @param latit
   *         New latitude for the Building
   */
  public void setLatit(Double latit)
  {
    this.latit = latit;
  }

  /**
   * Gets the Building's longitude
   *
   * @return The Building's longitude
   */
  public Double getLongit()
  {
    return longit;
  }

  /**
   * Sets the Building's longitude to a new value
   *
   * @param longit
   *         New longitude for the Building
   */
  public void setLongit(Double longit)
  {
    this.longit = longit;
  }

  /**
   * Gets the number of floors in the Building
   *
   * @return The number of floors the Building has
   */
  public Integer getFloorCount()
  {
    return floorCount;
  }

  /**
   * Sets the number of floors to a new value
   *
   * @param floorCount
   *         New number of floors for the Building
   */
  public void setFloorCount(Integer floorCount)
  {
    this.floorCount = floorCount;
  }
}
