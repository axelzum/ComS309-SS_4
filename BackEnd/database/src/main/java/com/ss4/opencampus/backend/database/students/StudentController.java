package com.ss4.opencampus.backend.database.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Base URL for all Student related Requests:
 * <p>http://coms-309-ss-4.misc.iastate.edu:8080/students</p>
 * Everything in either @PostMapping or @GetMapping will be added on to the URL for the specific request. If the request
 * has added parameters, it will look something like this:
 * <p>http://coms-309-ss-4.misc.iastate.edu:8080/students/search/firstName?firstName="value_here_no_quotes"</p>
 * Obviously, lastName would have lastName instead of firstName etc.
 */
@RestController
@RequestMapping(path = "/students")
public class StudentController
{
  @Autowired
  private StudentRepository studentRepository;

  /**
   * Adds a new Student to the Students table
   *
   * @param student
   *         Student object to be added. Comes in as JSON
   *
   * @return Message notifying of success or failure. Can change to be more official later by creating a message class.
   */
  @PostMapping(path = "/add")
  public @ResponseBody
  Map<String, String> addNewStudent(@RequestBody Student student)
  {
    try
    {
      studentRepository.save(student);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", "Failed to add student to Students table");
    }
    return Collections.singletonMap("response", "Added student to Students table");
  }

  /**
   * Return list of all Students in Students table
   *
   * @return list of all Students in Students table in JSON format
   */
  @GetMapping(path = "/search/all")
  public @ResponseBody
  Iterable<Student> getAllStudents()
  {
    return studentRepository.findAll();
  }

  /**
   * Gives Client information about the Student with the given userName. userName's are unique, so only one user will be
   * returned.
   *
   * @param userName
   *         userName to look for
   *
   * @return Information about Student with given userName in JSON format
   */
  @GetMapping(path = "/search/userName")
  public @ResponseBody
  Student getStudentByUsername(@RequestParam String userName)
  {
    return studentRepository.findByUserName(userName);
  }

  /**
   * Gives Client information about Students with entered firstName.
   *
   * @param firstName
   *         firstName to search for
   *
   * @return List of Students in JSON format with given firstName
   */
  @GetMapping(path = "/search/firstName")
  public @ResponseBody
  Iterable<Student> getStudentsByFirstName(@RequestParam String firstName)
  {
    return studentRepository.findAllByFirstName(firstName);
  }

  /**
   * Gives Client information about Students with entered lastName.
   *
   * @param lastName
   *         lastName to search for
   *
   * @return List of Students in JSON format with given lastName
   */
  @GetMapping(path = "/search/lastName")
  public @ResponseBody
  Iterable<Student> getStudentsByLastName(@RequestParam String lastName)
  {
    return studentRepository.findAllByLastName(lastName);
  }

  /**
   * Gives Client information about Students with entered firstName and lastName.
   *
   * @param firstName
   *         firstName to look for
   * @param lastName
   *         lastName to look for
   *
   * @return List of Students with the same first and last names in JSON format
   */
  @GetMapping(path = "/search/firstNameAndLastName")
  public @ResponseBody
  Iterable<Student> getStudentsByFirstAndLastName(@RequestParam String firstName, @RequestParam String lastName)
  {
    return studentRepository.findAllByFirstNameAndLastName(firstName, lastName);
  }

  /**
   * Will rarely be used. If a Student clicks on an existing Student that 100% exists, this will be used to grab the
   * information about that Student.
   *
   * @param id
   *         id of Student that was clicked
   *
   * @return information about Student in JSON format
   */
  @GetMapping(path = "/search/id/{id}")
  public @ResponseBody
  Optional<Student> getStudentById(@PathVariable Integer id)
  {
    return studentRepository.findById(id);
  }

}
