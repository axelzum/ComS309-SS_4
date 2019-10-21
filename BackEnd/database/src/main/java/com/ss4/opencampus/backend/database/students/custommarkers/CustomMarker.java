package com.ss4.opencampus.backend.database.students.custommarkers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss4.opencampus.backend.database.students.Student;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "CustomMarkers")
public class CustomMarker
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer cmId;

  @Column(name = "Name")
  private String name;

  @Column(name = "Description")
  private String desc;

  @Column(name = "Latitude")
  private Double cmLatit;

  @Column(name = "Longitude")
  private Double cmLongit;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private Student student;

  public Integer getCmID()
  {
    return cmId;
  }

  public void setCmID(Integer cmId)
  {
    this.cmId = cmId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDesc()
  {
    return desc;
  }

  public void setDesc(String desc)
  {
    this.desc = desc;
  }

  public Double getCmLatit()
  {
    return cmLatit;
  }

  public void setCmLatit(Double cmLatit)
  {
    this.cmLatit = cmLatit;
  }

  public Double getCmLongit()
  {
    return cmLongit;
  }

  public void setCmLongit(Double cmLongit)
  {
    this.cmLongit = cmLongit;
  }

  public Student getStudent()
  {
    return student;
  }

  public void setStudent(Student student)
  {
    this.student = student;
  }
}
