package com.ss4.opencampus.backend.database.buildings.floors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss4.opencampus.backend.database.buildings.Building;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Willis Knox
 */
@Entity
@Table(name = "FloorPlans")
public class FloorPlan
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer fpId;

  @Column(name = "Name")
  private String name;

  @Column(name = "Floor Level")
  private String level;

  @Column(name = "Picture_Directory")
  private String fpImagePath;

  @Transient
  private byte[] fpBytes;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "building_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private Building building;

  public FloorPlan()
  {

  }

  public Integer getFpId()
  {
    return fpId;
  }

  public void setFpId(Integer fpId)
  {
    this.fpId = fpId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getLevel()
  {
    return level;
  }

  public void setLevel(String level)
  {
    this.level = level;
  }

  public String getFpImagePath()
  {
    return fpImagePath;
  }

  public void setFpImagePath(String fpImagePath)
  {
    this.fpImagePath = fpImagePath;
  }

  public byte[] getFpBytes()
  {
    return fpBytes;
  }

  public void setFpBytes(byte[] fpBytes)
  {
    this.fpBytes = fpBytes;
  }

  public Building getBuilding()
  {
    return building;
  }

  public void setBuilding(Building building)
  {
    this.building = building;
  }
}
