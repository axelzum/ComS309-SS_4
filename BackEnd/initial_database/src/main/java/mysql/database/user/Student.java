package mysql.database.user;

import javax.persistence.*;

/**
 * @author Willis Knox
 */

/**This file is for the Student Object that will be placed into our database. A Student currently
 * has an auto-generated ID, a firstName, lastName, userName, email, and password. Eventually, I want the userName,
 * email, and id's to all be unique, but for now I am just happy that this is running.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id", "username"}))
public class Student
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "firstname")
  private String firstName;

  @Column(name = "lastname")
  private String lastName;

  @Column(name = "username")
  private String userName;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  /**
   * Basic contructor for the Student object, not needed, but doesn't hurt to have.
   */
  public Student()
  {

  }

  /**
   * Creates a Student object based off of what is placed in the parameters. Do not need id because it is
   * auto-generated.
   *
   * @param firstName
   *         firstName of the new Student
   * @param lastName
   *         lastName of the new Student
   * @param userName
   *         the new Student's userName for OpenCampus. This is what other users will see
   * @param email
   *         new Student's email. Will be used to verify who is who. Send notifications/emails eventually?
   * @param password
   *         password for the new Student's account. This will need to be encrypted or something to ensure security
   */
  public Student(String firstName, String lastName, String userName, String email, String password)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.email = email;
    this.password = password;
  }

  /**
   * Simple getter for returning the current Student's ID
   *
   * @return this.id
   */
  public Integer getId()
  {
    return id;
  }

  /**
   * Simple setter for changing the Students's ID. Might be removed eventually
   *
   * @param id new id value
   */
  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * Simple getter for returning the current Student's userName
   *
   * @return this.userName
   */
  public String getUserName()
  {
    return userName;
  }

  /**
   * Simple setter for changing the Student's userName. Will probably have some restrictions on this eventually. Such
   * as two Student's cannot have the same userName.
   *
   * @param userName new userName for the Student
   */
  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  /**
   * Simple getter for returning the current Student's firstname
   *
   * @return this.firstName
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * Simple setter for changing the Student's firstName.
   *
   * @param firstName new firstName for the Student
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * Simple getter for returning the current Student's lastName
   *
   * @return this.lastName
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * Simple setter for changing the Student's lastName.
   *
   * @param lastName new lastName for the Student
   */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  /**
   * Simple getter for returning the current Student's email
   *
   * @return this.email
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * Simple setter for changing the Student's email. Will need a confirmation of some sort. Don't know how this will
   * work quite yet.
   *
   * @param email new email for the Student
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * Simple getter for returning the current Student's password
   *
   * @return this.password
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * Simple setter for changing the Student's password. Will need to be a little more secure than what it is right now
   *
   * @param password new password for the Student
   */
  public void setPassword(String password)
  {
    this.password = password;
  }
}
