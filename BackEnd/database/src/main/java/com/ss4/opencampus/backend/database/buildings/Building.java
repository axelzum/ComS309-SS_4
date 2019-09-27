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
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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

  public Building()
  {

  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getBuildingName()
  {
    return buildingName;
  }

  public void setBuildingName(String buildingName)
  {
    this.buildingName = buildingName;
  }

  public String getAbbreviation()
  {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation)
  {
    this.abbreviation = abbreviation;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public Double getLatit()
  {
    return latit;
  }

  public void setLatit(Double latit)
  {
    this.latit = latit;
  }

  public Double getLongit()
  {
    return longit;
  }

  public void setLongit(Double longit)
  {
    this.longit = longit;
  }
}
