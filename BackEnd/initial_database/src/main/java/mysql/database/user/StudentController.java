package mysql.database.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controls the different types of requests sent to the server. This is explicitly for Student entries. Initial path
 * will be: coms-309-ss-4.misc.iastate.edu:8080/students
 */
@Controller
@RequestMapping(path = "/students")
public class StudentController
{
  /**
   * Creates a StudentRepository object and implements it's method's behind the scenes.
   */
  @Autowired
  private StudentRepository studentRepository;

  /**
   * When a Client tries to POST, this method takes the information and handles it.
   *
   * @param firstName
   *         firstName of new Student to be added to database
   * @param lastName
   *         lastName of new Student to be added to database
   * @param userName
   *         userName of new Student to be added to database
   * @param email
   *         email of new Student to be added to database
   * @param password
   *         password of new Student to be added to database
   *
   * @return Returns a String letting me know that the action succeeded. Not really needed but is nice for testing
   * purposes.
   */
  @PostMapping(path = "/add")
  public @ResponseBody
  String addNewStudent(@RequestParam String firstName, @RequestParam String lastName,
                       @RequestParam String userName, @RequestParam String email,
                       @RequestParam String password)
  {
    Student student = new Student();
    student.setFirstName(firstName);
    student.setLastName(lastName);
    student.setPassword(password);
    student.setEmail(email);
    student.setUserName(userName);
    studentRepository.save(student);
    return "Student added!";
  }

  /**
   * Returns all Students in the database
   *
   * @return A list of all Students in JSON format. Client will take this info and be able to display it to the
   * end-users
   */
  @GetMapping(path = "/search/all")
  public @ResponseBody
  Iterable<Student> getAllStudents()
  {
    return studentRepository.findAll();
  }
  
  // NOTE: WHEN MAKING MORE OFFICIAL CONTROLLER WE WILL PROBABLY USE @PathVariable
  // FOR SPECIFIC NAMES INSTEAD OF @RequestParam
  // Example:   
  
  // @GetMapping(path = "/search/firstName/{firstName}")
  // public @ResponseBody Iterable<Student> getStudentByFirstName(@PathVariable("firstName") String firstName
  // {
  // return studentRepository.findAllByFirstName(firstName);      
  // }
  
  // URL will look like this: coms-309-ss-4.misc.iastate.edu:8080/students/search/firstName/Willis
  // This will return all Students with firstName: Willis

  /**
   * Returns all Students in the database with the given firstName
   *
   * @param firstName
   *         Name to search the database/Student table for.
   *
   * @return A list of Students that meet the requirements in JSON format. Client will take this info and be able to
   * display it to the end-users
   */
  @GetMapping(path = "/search/firstName")
  public @ResponseBody
  Iterable<Student> getStudentsByFirstName(@RequestParam String firstName)
  {
    return studentRepository.findAllByFirstName(firstName);
  }

  /**
   * Returns all Students in the database with the given lastName
   *
   * @param lastName
   *         Name to search the database/Student table for.
   *
   * @return A list of Students that meet the requirements in JSON format. Client will take this info and be able to
   * display it to the end-users
   */
  @GetMapping(path = "/search/lastName")
  public @ResponseBody
  Iterable<Student> getStudentsByLastName(@RequestParam String lastName)
  {
    return studentRepository.findAllByLastName(lastName);
  }

  /**
   * Returns all Students in the database with the given firstName and lastName combination
   *
   * @param firstName
   *         firstName to search the database/Student table for.
   * @param lastName
   *         lastName to search the database/Student table for.
   *
   * @return A list of Students that meet the requirements in JSON format. Client will take this info and be able to
   * display it to the end-users
   */
  @GetMapping(path = "/search/firstLast")
  public @ResponseBody
  Iterable<Student> getStudentsByFirstAndLast(@RequestParam String firstName, @RequestParam String lastName)
  {
    return studentRepository.findAllByFirstNameAndLastName(firstName, lastName);
  }

  /**
   * Returns the singular Student with the given userName. This only returns one because userNames will be unique.
   *
   * @param userName
   *         constraint to search for
   *
   * @return Information about the Student with the given userName
   */
  @GetMapping(path = "/search/userName")
  public @ResponseBody
  Student getStudentByUserName(@RequestParam String userName)
  {
    return studentRepository.findByUserName(userName);
  }
}
