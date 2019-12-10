package com.ss4.opencampus.backend.database.reviews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss4.opencampus.backend.database.OpenCampusEntity;
import com.ss4.opencampus.backend.database.uspots.USpot;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Willis Knox
 * <p>
 * Weak Entity Set to USpots. They cannot exists without a parent USpot. Reviews will be for telling other Student what
 * you think a particular USpot on the map
 */
@Entity
@Table(name = "Reviews")
public class Review implements OpenCampusEntity
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

  /**
   * Default Constructor
   */
  public Review()
  {

  }

  /**
   * Constuctor for Dependency Injection
   *
   * @param text
   *         text that review will get
   */
  public Review(String text)
  {
    this.text = text;
  }

  /**
   * Gets the ID of the Review
   *
   * @return Review's ID
   */
  public Integer getId()
  {
    return rId;
  }

  /**
   * Sets the ID of the Review to a new value
   *
   * @param rId
   *         new ID for Review
   */
  public void setId(Integer rId)
  {
    this.rId = rId;
  }

  /**
   * Gets the particular review's text description
   *
   * @return the Text for the Review
   */
  public String getText()
  {
    return text;
  }

  /**
   * Sets the text of a review to a new value
   *
   * @param text
   *         New text for the review
   */
  public void setText(String text)
  {
    this.text = text;
  }

  /**
   * Gets the USpot that the Review is tied to
   *
   * @return USpot for the Review
   */
  public USpot getuSpot()
  {
    return uSpot;
  }

  /**
   * Sets the USpot for the Review
   *
   * @param uSpot
   *         new USpot for this Review
   */
  public void setuSpot(USpot uSpot)
  {
    this.uSpot = uSpot;
  }

}
