package com.ss4.opencampus.backend.database.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/students")
public class StudentController
{
  @Autowired
  private StudentRepository studentRepository;

  @PostMapping(path = "/add")
  public @ResponseBody
  String addNewStudent(@RequestBody Student student)
  {
    try
    {
      studentRepository.save(student);
    }
    catch (Exception e)
    {
      return "Failed to add student to Students table";
    }
    return "Added student to Students table";
  }

  @GetMapping(path = "/search/all")
  public @ResponseBody
  Iterable<Student> getAllStudents()
  {
    return studentRepository.findAll();
  }

  @GetMapping(path = "/search/userName")
  public @ResponseBody
  Student getStudentByUsername(@RequestParam String userName)
  {
    return studentRepository.findByUserName(userName);
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

  @GetMapping(path = "/search/firstNameAndLastName")
  public @ResponseBody
  Iterable<Student> getStudentsByFirstAndLastName(@RequestParam String firstName, @RequestParam String lastName)
  {
    return studentRepository.findAllByFirstNameAndLastName(firstName, lastName);
  }

  @GetMapping(path = "/search/id/{id}")
  public @ResponseBody
  Optional<Student> getStudentById(@PathVariable Integer id)
  {
    return studentRepository.findById(id);
  }

}
