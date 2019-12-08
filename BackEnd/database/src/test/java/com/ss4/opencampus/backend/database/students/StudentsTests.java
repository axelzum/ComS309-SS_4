package com.ss4.opencampus.backend.database.students;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * @author Axel Zumwalt
 * <p>
 * Test Class for Students. Tests that the StudentController is correctly working by using a combination of JUnit4 and
 * Mockito
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class StudentsTests
{
  private static Student student1;
  private static Student student2;
  private static Student student3;
  private static Student student4;

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  private StudentService studentService;

  /**
   * Initializes 4 fake Students before every test
   */
  @Before
  public void init()
  {
    student1 = new Student();
    student1.setId(1);
    student1.setFirstName("Axel");
    student1.setLastName("Zumwalt");
    student1.setUserName("axelzum");
    student1.setEmail("axelzum@iastate.edu");
    student1.setPassword("123");

    student2 = new Student();
    student2.setId(2);
    student2.setFirstName("Willis");
    student2.setLastName("Knox");
    student2.setUserName("wpknox");
    student2.setEmail("wpknox@iastate.edu");
    student2.setPassword("123");

    student3 = new Student();
    student3.setId(3);
    student3.setFirstName("Axel");
    student3.setLastName("Two");
    student3.setUserName("axelzum");
    student3.setEmail("axelzum2@iastate.edu");
    student3.setPassword("12345");

    student4 = new Student();
    student4.setId(4);
    student4.setFirstName("Willis");
    student4.setLastName("Two");
    student4.setUserName("billis");
    student4.setEmail("wpknox@iastate.edu");
    student4.setPassword("123456");
  }

  /**
   * Tests to make sure if no Students have been saved, then the StudentController returns an empty list.
   */
  @Test
  public void findAllNoStudents()
  {
    Mockito.when(studentRepository.findAll(new Sort(Sort.Direction.ASC, "lastName"))).thenReturn(
            Collections.emptyList());
    Iterable<Student> b = studentService.getStudents("all", null, null);
    assertEquals(studentRepository.findAll(new Sort(Sort.Direction.ASC, "lastName")), b);
    Mockito.verify(studentRepository, Mockito.times(2)).findAll(new Sort(Sort.Direction.ASC, "lastName"));
  }

  /**
   * Tests to make if the specs for findAll() are met, the StudentController returns a list of Students sorted by their
   * lastNames.
   */
  @Test
  public void findAllWithBuildings()
  {
    Mockito.when(studentRepository.findAll(new Sort(Sort.Direction.ASC, "lastName"))).thenReturn(
            Arrays.asList(student2,
                          student1));
    Iterable<Student> b = studentService.getStudents("all", null, null);
    assertEquals(studentRepository.findAll(new Sort(Sort.Direction.ASC, "lastName")), b);
    Mockito.verify(studentRepository, Mockito.times(2)).findAll(new Sort(Sort.Direction.ASC, "lastName"));
  }

  /**
   * Tests to make if the specs for findByUserName() are met, the StudentController returns a list with the singular
   * Student with that userName
   */
  @Test
  public void findByName()
  {
    Mockito.when(studentRepository.findByUserName("wpknox")).thenReturn(
            Collections.singletonList(student2));
    Iterable<Student> b = studentService.getStudents("userName", "wpknox", null);
    assertEquals(studentRepository.findByUserName("wpknox"), b);
    Mockito.verify(studentRepository, Mockito.times(2)).findByUserName("wpknox");
  }

  /**
   * As of now, does nothing
   */
  @Test
  public void addDuplicateUsername()
  {

  }

  /**
   * As of now, does nothing
   */
  @Test
  public void addDuplicateEmail()
  {

  }
}
