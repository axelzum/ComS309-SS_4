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
  Map<String, Boolean> addNewStudent(@RequestBody Student student)
  {
    try
    {
      studentRepository.save(student);
    }
    catch (Exception e)
    {
      return Collections.singletonMap("response", false);
    }
    return Collections.singletonMap("response", true);
  }

  /**
   * Mapping for the different searches that will return lists of Students in the database. If the Client is trying to
   * see a list of Students, this method will be called
   *
   * @param searchType
   *         Type of list the client wants to see. Can be all Students, searching by name Start, or searching by
   *         abbreviation start
   * @param param1
   *         What the client is searching with. Not a required parameter for this method to work.
   * @param param2
   *         Another optional parameter to help specific what the Client wants in the returned list
   *
   * @return A list of Students that meet what the client wants. Or null if they somehow search something they shouldn't
   * be able to search.
   */
  @GetMapping(path = "/search/lists/{searchType}")
  public @ResponseBody
  Iterable<Student> getStudentLists(@PathVariable String searchType, @RequestParam(required = false) String param1, @RequestParam(required = false) String param2)
  {
    switch (searchType)
    {
      case "all":
        return studentRepository.findAll();
      case "firstName":
        return studentRepository.findAllByFirstName(param1);
      case "lastName":
        return studentRepository.findAllByLastName(param1);
      case "firstNameAndLastName":
        return studentRepository.findAllByFirstNameAndLastName(param1, param2);
      default:
        return null;
    }
  }

  /**
   * Mapping for the different searches that will return a single Student in the database. If the Client is trying to
   * find a single Student, this method will be called. Currently, the only way to search for a single Student is by
   * userName. Can easily be added onto.
   *
   * @param searchType
   *         How the client wants to find their specific Student.
   * @param param
   *         Parameter to refine search. This is parameter is required otherwise the Backend does not know what to
   *         search for
   *
   * @return A Student with the matching info the was entered. Will be null if error.
   */
  @GetMapping(path = "/search/specific/{searchType}")
  public @ResponseBody
  Student getStudentBy(@PathVariable String searchType, @RequestParam Object param)
  {
    // can be replaced with switch if more cases are added.
    if ("userName".equals(searchType))
    {
      return studentRepository.findByUserName((String) param);
    }
    return null;
  }

  /**
   * Will rarely be used. If a Student clicks on an existing Student that 100% exists, this will be used to grab the
   * information about that Student.
   * <p>
   * Has to be separate because to search by ID you need to return an Optional list of Students.
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

  /*
   * Below are old mappings that have been combined into 2 methods instead of many. The combination will make
   * it easier to add onto if needed at a later date.
   * */
//
//  /**
//   * Return list of all Students in Students table
//   *
//   * @return list of all Students in Students table in JSON format
//   */
//  @GetMapping(path = "/search/all")
//  public @ResponseBody
//  Iterable<Student> getAllStudents()
//  {
//    return studentRepository.findAll();
//  }
//
//  /**
//   * Gives Client information about the Student with the given userName. userName's are unique, so only one user will be
//   * returned.
//   *
//   * @param userName
//   *         userName to look for
//   *
//   * @return Information about Student with given userName in JSON format
//   */
//  @GetMapping(path = "/search/userName")
//  public @ResponseBody
//  Student getStudentByUsername(@RequestParam String userName)
//  {
//    return studentRepository.findByUserName(userName);
//  }
//
//  /**
//   * Gives Client information about Students with entered firstName.
//   *
//   * @param firstName
//   *         firstName to search for
//   *
//   * @return List of Students in JSON format with given firstName
//   */
//  @GetMapping(path = "/search/firstName")
//  public @ResponseBody
//  Iterable<Student> getStudentsByFirstName(@RequestParam String firstName)
//  {
//    return studentRepository.findAllByFirstName(firstName);
//  }
//
//  /**
//   * Gives Client information about Students with entered lastName.
//   *
//   * @param lastName
//   *         lastName to search for
//   *
//   * @return List of Students in JSON format with given lastName
//   */
//  @GetMapping(path = "/search/lastName")
//  public @ResponseBody
//  Iterable<Student> getStudentsByLastName(@RequestParam String lastName)
//  {
//    return studentRepository.findAllByLastName(lastName);
//  }
//
//  /**
//   * Gives Client information about Students with entered firstName and lastName.
//   *
//   * @param firstName
//   *         firstName to look for
//   * @param lastName
//   *         lastName to look for
//   *
//   * @return List of Students with the same first and last names in JSON format
//   */
//  @GetMapping(path = "/search/firstNameAndLastName")
//  public @ResponseBody
//  Iterable<Student> getStudentsByFirstAndLastName(@RequestParam String firstName, @RequestParam String lastName)
//  {
//    return studentRepository.findAllByFirstNameAndLastName(firstName, lastName);
//  }
//

}
