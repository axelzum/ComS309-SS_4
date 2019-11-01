package com.ss4.opencampus.backend.database.students;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Willis Knox
 * <p>
 * Repository for different ways to Query the MySQL Students table
 */
public interface StudentRepository extends JpaRepository<Student, Integer>
{
  /**
   * Finds a Student by the given userName
   *
   * @param userName
   *         userName of Student to find
   *
   * @return Iterable list of 0 or 1 Students
   */
  Iterable<Student> findByUserName(String userName);

  /**
   * Finds a Student by the given email
   *
   * @param email
   *         email of the Student to find
   *
   * @return Iterable list of 0 or 1 Students
   */
  Iterable<Student> findByEmail(String email);

  /**
   * Finds a list of Students with the given Firstname
   *
   * @param firstName
   *         first name to look for
   *
   * @return Iterable List of Students
   */
  Iterable<Student> findAllByFirstName(String firstName);

  /**
   * Finds a list of Students with the given Lastname
   *
   * @param lastName
   *         lastName to look for
   *
   * @return Iterable List of Students
   */
  Iterable<Student> findAllByLastName(String lastName);

  /**
   * Finds a list of Students with the given first and last name
   *
   * @param firstName
   *         firstName to look for
   * @param lastName
   *         lastName to look for
   *
   * @return Iterable List of Students
   */
  Iterable<Student> findAllByFirstNameAndLastName(String firstName, String lastName);
}
