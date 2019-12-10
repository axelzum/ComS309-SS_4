package com.ss4.opencampus.backend.database.buildings;

import com.ss4.opencampus.backend.database.OpenCampusEntity;

import javax.persistence.*;

/**
 * @author Willis Knox
 * <p>This is a class for the Buildings table in the open_campus database</p>
 */
@Entity
@Table(name = "Buildings")
public class Building implements OpenCampusEntity
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

  @Column(name = "FloorCount")
  private Integer floorCnt;

  /**
   * Constructor for the Building class
   */
  public Building()
  {

  }

  /**
   * Constructor for Dependency Injection
   *
   * @param buildingName
   *         name of Building
   * @param abbreviation
   *         abbreviation of Building name
   * @param address
   *         address of Building
   * @param latit
   *         latitude of Building
   * @param longit
   *         longitude of Building
   */
  public Building(String buildingName, String abbreviation, String address, Double latit, Double longit)
  {
    this.buildingName = buildingName;
    this.abbreviation = abbreviation;
    this.address = address;
    this.latit = latit;
    this.longit = longit;
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
   * Gets the Building's Floor Count
   *
   * @return Number of Floors in building
   */
  public Integer getFloorCnt()
  {
    return floorCnt;
  }

  /**
   * Sets the Building's floor count
   *
   * @param floorCnt
   *         new number of floors in the building
   */
  public void setFloorCnt(Integer floorCnt)
  {
    this.floorCnt = floorCnt;
  }

}
