package com.ss4.opencampus.backend.database.students;

import com.ss4.opencampus.backend.database.OpenCampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Service Class for Students
 */
@Service
public class StudentService implements OpenCampusService
{
  @Autowired
  private StudentRepository studentRepository;

  /**
   * Adds a Student to the database
   *
   * @param t
   *         Student to be added
   * @param <T>
   *         Generic
   *
   * @return Boolean of success or failure
   */
  @Override
  public <T> Boolean add(T t)
  {
    try
    {
      Student student = (Student) t;
      studentRepository.save(student);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Method to search the database and return different lists of Students depending on given params
   *
   * @param searchType
   *         type of search to perform
   * @param param1
   *         first optional param. Could be firstName for example
   * @param param2
   *         second optional param. Could be lastName for example
   *
   * @return Iterable list of Students
   */
  public Iterable<Student> getStudents(String searchType, String param1, String param2)
  {
    switch (searchType)
    {
      case "firstName":
        return studentRepository.findAllByFirstName(param1);
      case "lastName":
        return studentRepository.findAllByLastName(param1);
      case "firstNameAndLastName":
        return studentRepository.findAllByFirstNameAndLastName(param1, param2);
      case "userName":
        return studentRepository.findByUserName(param1);
      case "email":
        return studentRepository.findByEmail(param1);
      default: // default is returning a list of students sorted by last name. There needs to be some text after "search/" otherwise it will not work?
        return studentRepository.findAll(new Sort(Sort.Direction.ASC, "lastName"));
    }
  }

  /**
   * Method to search DB for a single student with a given ID
   *
   * @param id
   *         id of Student to find
   *
   * @return Optional object of student
   */
  @SuppressWarnings("unchecked")
  @Override
  public Optional<Student> getById(Integer id)
  {
    return studentRepository.findById(id);
  }

  /**
   * Updates the Student with the given ID in the database
   *
   * @param t
   *         Information for Student to be given
   * @param id
   *         id of Student in database
   * @param <T>
   *         Generic. Type = Student
   *
   * @return Boolean of success or failure
   */
  @Override
  public <T> Boolean put(T t, Integer id)
  {
    try
    {
      Student student = (Student) t;
      Student s = studentRepository.findById(id).get();
      s.setFirstName(student.getFirstName());
      s.setLastName(student.getLastName());
      s.setEmail(student.getEmail());
      s.setUserName(student.getUserName());
      s.setPassword(student.getPassword());
      studentRepository.save(s);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Updates part of a Student in the DB with the given info. Does NOT set other info to NULL, unlike put()
   *
   * @param patch
   *         Map of all changes to be made
   * @param id
   *         id of Student to be updated
   *
   * @return Boolean of success or failure
   */
  @Override
  public Boolean patch(Map<String, Object> patch, Integer id)
  {
    try
    {
      Student student = studentRepository.findById(id).get();
      if (patch.containsKey("firstName"))
      {
        student.setFirstName((String) patch.get("firstName"));
      }
      if (patch.containsKey("lastName"))
      {
        student.setLastName((String) patch.get("lastName"));
      }
      if (patch.containsKey("userName"))
      {
        student.setUserName((String) patch.get("userName"));
      }
      if (patch.containsKey("email"))
      {
        student.setEmail((String) patch.get("email"));
      }
      if (patch.containsKey("password"))
      {
        student.setPassword((String) patch.get("password"));
      }
      studentRepository.save(student);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * Finds the Student with the given ID and deletes it from the database
   *
   * @param id
   *         id of Student to be deleted
   *
   * @return Boolean of success or failure
   */
  @Override
  public Boolean delete(Integer id)
  {
    try
    {
      studentRepository.deleteById(id);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

}
