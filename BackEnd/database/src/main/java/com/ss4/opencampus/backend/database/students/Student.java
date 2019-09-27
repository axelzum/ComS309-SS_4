package com.ss4.opencampus.backend.database.students;

import javax.persistence.*;

/**
 * @author Willis Knox
 * <p>
 * This is a class for the Student table in the open_campus database.
 * </p>
 */

@Entity
@Table(name = "Students")
public class Student
{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "Firstname")
  private String firstName;

  @Column(name = "Lastname")
  private String lastName;

  @Column(name = "Username")
  private String userName;

  @Column(name = "Email")
  private String email;

  @Column(name = "Password")
  private String password;

  public Student()
  {

  }

  /**
   * Get the id for the current Student
   *
   * @return id of current Student
   */
  public Integer getId()
  {
    return id;
  }

  /**
   * Change the id of the current Student
   *
   * @param id
   *         value to change the ID to
   */
  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * Get the firstName of the given Student
   *
   * @return firstName of Student
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * Set the firstName of Student to something else
   *
   * @param firstName
   *         new value for firstName
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * Get the lastName of the given Student
   *
   * @return lastName of Student
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * Set the lastName of Student to something else
   *
   * @param lastName
   *         new value for lastName
   */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  /**
   * Get the userName of the Student
   *
   * @return userName of Student
   */
  public String getUserName()
  {
    return userName;
  }

  /**
   * Set the userName of the Student to something else
   *
   * @param userName
   *         new value for userName
   */
  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  /**
   * Get the email for the Student
   *
   * @return Student's email
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * Set the email of the Student to something else
   *
   * @param email
   *         new email for Student
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * Get the Student's password
   *
   * @return Student's password
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * Change the password for the Student
   *
   * @param password
   *         new password for Student's account
   */
  public void setPassword(String password)
  {
    this.password = password;
  }
}
