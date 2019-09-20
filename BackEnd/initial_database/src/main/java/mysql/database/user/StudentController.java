package mysql.database.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/students")
public class StudentController
{
  @Autowired
  private StudentRepository studentRepository;

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

  @GetMapping(path = "/search/all")
  public @ResponseBody
  Iterable<Student> getAllStudents()
  {
    return studentRepository.findAll();
  }

  @GetMapping(path = "/search/firstName")
  public @ResponseBody
  Iterable<Student> getStudentsByFirstName(@RequestParam String firstName)
  {
    return studentRepository.findAllByFirstName(firstName);
  }

  @GetMapping(path = "/search/lastName")
  public @ResponseBody
  Iterable<Student> getStudentsByLastName(@RequestParam String lastName)
  {
    return studentRepository.findAllByLastName(lastName);
  }

  @GetMapping(path = "/search/firstLast")
  public @ResponseBody
  Iterable<Student> getStudentsByFirstAndLast(@RequestParam String firstName, @RequestParam String lastName)
  {
    return studentRepository.findAllByFirstNameAndLastName(firstName, lastName);
  }

  @GetMapping(path = "/search/userName")
  public @ResponseBody
  Student getStudentByUserName(@RequestParam String userName)
  {
    return studentRepository.findByUserName(userName);
  }
}
