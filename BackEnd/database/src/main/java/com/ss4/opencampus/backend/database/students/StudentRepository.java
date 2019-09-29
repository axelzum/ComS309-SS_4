package com.ss4.opencampus.backend.database.students;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Willis Knox
 * <p>
 * Repository for different ways to Query the MySQL Students table
 * </p>
 */
public interface StudentRepository extends JpaRepository<Student, Integer>
{
  Student findByUserName(String userName);

  Iterable<Student> findAllByFirstName(String firstName);

  Iterable<Student> findAllByLastName(String lastName);

  Iterable<Student> findAllByFirstNameAndLastName(String firstName, String lastName);
}
