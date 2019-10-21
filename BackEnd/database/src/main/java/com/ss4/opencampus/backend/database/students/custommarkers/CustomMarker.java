package com.ss4.opencampus.backend.database.students.custommarkers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss4.opencampus.backend.database.students.Student;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Willis Knox
 *
 * Table for CustomMarkers. These are tied to specific Students. A Student's CustomMarkers will NOT
 * be shared with other Students.
 */
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

  /**
   * Foreign key to the Student Table. Links the Student_ID to this CustomMarker.
   * If the Student is deleted, so will this CustomMarker.
   */
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
