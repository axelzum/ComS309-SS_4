package com.ss4.opencampus.backend.database.students.routes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss4.opencampus.backend.database.students.Student;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Willis Knox
 */
@Entity
@Table(name = "Routes")
public class Route
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "Route_Name")
  private String rtName;

  @Column(name = "Start_Lat")
  private Double originLat;

  @Column(name = "Start_Lng")
  private Double originLng;

  @Column(name = "End_Lat")
  private Double destLat;

  @Column(name = "End_Lng")
  private Double destLng;

  /**
   * Foreign key to the Student Table. Links the Student_ID to this Route. If the Student is deleted, so will
   * this Route.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "route_student_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private Student student;

  public Route()
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

  public String getRtName()
  {
    return rtName;
  }

  public void setRtName(String rtName)
  {
    this.rtName = rtName;
  }

  public Double getOriginLat()
  {
    return originLat;
  }

  public void setOriginLat(Double originLat)
  {
    this.originLat = originLat;
  }

  public Double getOriginLng()
  {
    return originLng;
  }

  public void setOriginLng(Double originLng)
  {
    this.originLng = originLng;
  }

  public Double getDestLat()
  {
    return destLat;
  }

  public void setDestLat(Double destLat)
  {
    this.destLat = destLat;
  }

  public Double getDestLng()
  {
    return destLng;
  }

  public void setDestLng(Double destLng)
  {
    this.destLng = destLng;
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