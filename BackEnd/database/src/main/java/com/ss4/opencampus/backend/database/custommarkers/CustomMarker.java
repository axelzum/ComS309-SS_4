package com.ss4.opencampus.backend.database.custommarkers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss4.opencampus.backend.database.OpenCampusEntity;
import com.ss4.opencampus.backend.database.students.Student;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Willis Knox
 * <p>
 * Table for CustomMarkers. These are tied to specific Students. A Student's CustomMarkers will NOT be shared with other
 * Students.
 */
@Entity
@Table(name = "CustomMarkers")
public class CustomMarker implements OpenCampusEntity
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
   * Foreign key to the Student Table. Links the Student_ID to this CustomMarker. If the Student is deleted, so will
   * this CustomMarker.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private Student student;

  public CustomMarker()
  {

  }

  /**
   * Constructor for Dependency Injection
   *
   * @param name
   *         name of CustomMarker
   * @param desc
   *         Description of CustomMarker
   * @param cmLatit
   *         latitude of custommaker
   * @param cmLongit
   *         longitude of customMarker
   */
  public CustomMarker(String name, String desc, Double cmLatit, Double cmLongit)
  {
    this.name = name;
    this.desc = desc;
    this.cmLatit = cmLatit;
    this.cmLongit = cmLongit;
  }

  /**
   * Gets the ID of the CustomMarker
   *
   * @return ID of the CustomMarker
   */
  public Integer getId()
  {
    return cmId;
  }

  /**
   * Sets the ID to a new value
   *
   * @param cmId
   *         New ID for the CustomMarker
   */
  public void setId(Integer cmId)
  {
    this.cmId = cmId;
  }

  /**
   * Gets the name of the CustomMarker
   *
   * @return Name of the CustomMarker
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the name to a new value
   *
   * @param name
   *         new name for the CustomMarker
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Gets the description of the CustomMarker
   *
   * @return Description of the CustomMarker
   */
  public String getDesc()
  {
    return desc;
  }

  /**
   * Sets the description of the CustomMarker
   *
   * @param desc
   *         New description for the CustomMarker
   */
  public void setDesc(String desc)
  {
    this.desc = desc;
  }

  /**
   * Gets the latitude of the CustomMarker
   *
   * @return The latitude of the CustomMarker
   */
  public Double getCmLatit()
  {
    return cmLatit;
  }

  /**
   * Sets the latitude of the CustomMarker
   *
   * @param cmLatit
   *         New latitude for the CustomMarker
   */
  public void setCmLatit(Double cmLatit)
  {
    this.cmLatit = cmLatit;
  }

  /**
   * Gets the longitude of the CustomMarker
   *
   * @return longitude of the CustomMarker
   */
  public Double getCmLongit()
  {
    return cmLongit;
  }

  /**
   * Sets the longitude to a new value
   *
   * @param cmLongit
   *         new longitude for the CustomMarker
   */
  public void setCmLongit(Double cmLongit)
  {
    this.cmLongit = cmLongit;
  }

  /**
   * Gets the Student that this CustomMarker is for
   *
   * @return Student that made this CustomMarker
   */
  public Student getStudent()
  {
    return student;
  }

  /**
   * Sets the Student to a new Student
   *
   * @param student
   *         New Student for the CustomMarker... shouldn't really happen
   */
  public void setStudent(Student student)
  {
    this.student = student;
  }
}
