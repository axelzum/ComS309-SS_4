package com.ss4.opencampus.backend.database.floors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss4.opencampus.backend.database.OpenCampusEntity;
import com.ss4.opencampus.backend.database.buildings.Building;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Willis Knox
 * <p>
 * FloorPlans will store information about the floor of the different Buildings on campus. A FloorPlan is a Weak Entity
 * Set of a Building. FloorPlans have pictures that will be stored on the remote server
 */
@Entity
@Table(name = "FloorPlans")
public class FloorPlan implements OpenCampusEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer fpId;

  @Column(name = "name")
  private String name;

  @Column(name = "floorLevel")
  private String level;

  @Column(name = "picDirectory")
  private String fpImagePath;

  @Transient
  private byte[] fpBytes;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "building_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private Building building;

  /**
   * Constructor for the FloorPlan class
   */
  public FloorPlan()
  {

  }

  /**
   * Constructor for Dependency Injection
   *
   * @param name
   *         name of floorplan
   * @param level
   *         level floorplan is on
   * @param fpBytes
   *         byte[] of floorplan image
   */
  public FloorPlan(String name, String level, byte[] fpBytes)
  {
    this.name = name;
    this.level = level;
    this.fpBytes = fpBytes;
  }

  /**
   * Gets the ID for the Floor Plan
   *
   * @return The Floor Plan's ID
   */
  public Integer getId()
  {
    return fpId;
  }

  /**
   * Sets the Floor Plan's ID to a new value
   *
   * @param fpId
   *         New ID for the Floor Plan
   */
  public void setId(Integer fpId)
  {
    this.fpId = fpId;
  }

  /**
   * Gets the Floor Plan's name
   *
   * @return Name of the Floor Plan
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the Floor Plan's name to a new value
   *
   * @param name
   *         New name of the Floor Plan
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Gets the level in a Building the Floor Plan is of
   *
   * @return Level that the Floor Plan represents
   */
  public String getLevel()
  {
    return level;
  }

  /**
   * Sets the level of the Floor Plan to a new value
   *
   * @param level
   *         New level for the Floor Plan
   */
  public void setLevel(String level)
  {
    this.level = level;
  }

  /**
   * Gets the image path where the picture of the Floor Plan is stored
   *
   * @return The directory where the image is stored.
   */
  public String getFpImagePath()
  {
    return fpImagePath;
  }

  /**
   * Sets the directory where the Floor Plan is saved to a new value
   *
   * @param fpImagePath
   *         New directory location for the Floor Plan's image
   */
  public void setFpImagePath(String fpImagePath)
  {
    this.fpImagePath = fpImagePath;
  }

  /**
   * Gets the byte[] of the Floor Plan picture
   *
   * @return byte[] of the Floor Plan
   */
  public byte[] getFpBytes()
  {
    return fpBytes;
  }

  /**
   * Sets the Floor Plan byte[] to a new value. This means the picture is being updated/changed.
   *
   * @param fpBytes
   *         New byte[] for the Floor Plan
   */
  public void setFpBytes(byte[] fpBytes)
  {
    this.fpBytes = fpBytes;
  }

  /**
   * Gets the Building that the Floor Plan is associated with
   *
   * @return Building the Floor Plan is in
   */
  public Building getBuilding()
  {
    return building;
  }

  /**
   * Sets the Building to a new Building
   *
   * @param building
   *         New Building the Floor Plan is in
   */
  public void setBuilding(Building building)
  {
    this.building = building;
  }
}
