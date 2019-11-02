package com.ss4.opencampus.backend.database.uspots.reviews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss4.opencampus.backend.database.uspots.USpot;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "Reviews")
public class Review
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer rId;

  @Column(name = "Text")
  private String text;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "uspot_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private USpot uSpot;

  public Integer getrId()
  {
    return rId;
  }

  public void setrId(Integer rId)
  {
    this.rId = rId;
  }

  public String getText()
  {
    return text;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  public USpot getuSpot()
  {
    return uSpot;
  }

  public void setuSpot(USpot uSpot)
  {
    this.uSpot = uSpot;
  }

}
