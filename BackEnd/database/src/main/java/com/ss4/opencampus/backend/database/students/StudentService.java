package com.ss4.opencampus.backend.database.students;

import com.ss4.opencampus.backend.database.OpenCampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class StudentService implements OpenCampusService
{
  @Autowired
  private StudentRepository studentRepository;

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

  public Optional<Student> getById(Integer id)
  {
    return studentRepository.findById(id);
  }

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
