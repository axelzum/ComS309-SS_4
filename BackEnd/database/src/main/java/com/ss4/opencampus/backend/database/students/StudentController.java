package com.ss4.opencampus.backend.database.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Base URL for all Student related Requests: http://coms-309-ss-4.misc.iastate.edu:8080/students
 * <p>
 * Everything in either @PostMapping or @GetMapping will be added on to the URL for the specific request. If the request
 * has added parameters, it will look something like this: http://coms-309-ss-4.misc.iastate.edu:8080/students/search/firstName?firstName="value_here_no_quotes"
 * <p>
 * Obviously, lastName would have lastName instead of firstName etc.
 */
@RestController
@RequestMapping(path = "/students")
public class StudentController
{
  @Autowired
  private StudentService studentService;

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
    return Collections.singletonMap("response", studentService.add(student));
  }

  /**
   * Mapping for the different searches that will return lists and singluar Students in the database. If the Client is
   * trying to see Students, this will be called. Singular Students will be in lists of size 1.
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
  @GetMapping(path = "/search/{searchType}")
  public @ResponseBody
  Iterable<Student> getStudentLists(@PathVariable String searchType, @RequestParam(required = false) String param1, @RequestParam(required = false) String param2)
  {
    return studentService.getStudents(searchType, param1, param2);
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
    return studentService.getById(id);
  }

  /**
   * Method that handles PUT Requests. These are requests where the whole Student is being updated. Everything should be
   * provided from the Frontend to the Backend. Anything NOT provided will be set to NULL. If you only want to update
   * part of the Student, use the PATCH Request.
   *
   * @param student
   *         Info of the Student that will be placed into the table
   * @param id
   *         Student that exists in Table that will be updated
   *
   * @return JSON formatted response telling Frontend of success or failure
   */
  @PutMapping(path = "/update/{id}")
  public @ResponseBody
  Map<String, Boolean> updateStudent(@RequestBody Student student, @PathVariable Integer id)
  {
    return Collections.singletonMap("response", studentService.put(student, id));
  }

  /**
   * Method that handles PATCH Requests. These requests only update the part of the Student that was given by the
   * Frontend. So, if you only want to change part of the USpot, use this request. These are much more common than PUT
   * Requests.
   *
   * @param patch
   *         Map of the different fields that will be updated
   * @param id
   *         ID of Student that will be updated
   *
   * @return JSON formatted response telling Frontend of success or failure
   */
  @PatchMapping(path = "/patch/{id}")
  public @ResponseBody
  Map<String, Boolean> patchStudent(@RequestBody Map<String, Object> patch,
                                    @PathVariable Integer id)
  {
    return Collections.singletonMap("response", studentService.patch(patch, id));
  }

  /**
   * Method to handle DELETE Requests. Will delete the Student with the given ID if it is in the table
   *
   * @param id
   *         ID of Student to be deleted
   *
   * @return JSON formatted response telling Frontend of success or failure
   */
  @DeleteMapping(path = "/delete/{id}")
  public @ResponseBody
  Map<String, Boolean> deleteStudent(@PathVariable Integer id)
  {
    return Collections.singletonMap("response", studentService.delete(id));
  }
}
