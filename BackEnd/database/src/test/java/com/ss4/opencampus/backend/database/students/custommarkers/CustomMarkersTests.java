package com.ss4.opencampus.backend.database.students.custommarkers;

import com.ss4.opencampus.backend.database.students.Student;
import com.ss4.opencampus.backend.database.students.StudentRepository;
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
 * @author Willis Knox
 * <p>
 * Test class for CustomMarkers. Makes sure that the CustomMarkerController is setup properly and handles requests
 * appropiately.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CustomMarkersTests
{
  private static CustomMarker cm1;
  private static CustomMarker cm2;
  private static CustomMarker cm3;
  private static CustomMarker cm4;

  private static Student s1;
  private static Student s2;

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private CustomMarkerRepository customMarkerRepository;

  @InjectMocks
  private CustomMarkerController controller;

  @Before
  public void init()
  {
    s1 = new Student();
    s1.setId(1);
    s1.setFirstName("Willis");
    s1.setLastName("Knox");
    s1.setUserName("wpknox");
    s1.setEmail("wpknox@iastate.edu");
    s1.setPassword("Myp@ssword");

    s2 = new Student();
    s2.setId(2);
    s2.setFirstName("Paul");
    s2.setLastName("Atreides");
    s2.setUserName("Muad'Dib");
    s2.setEmail("ruler@arrakis.com");
    s2.setPassword("h3lpM3Chani");

    cm1 = new CustomMarker();
    cm1.setCmID(1);
    cm1.setStudent(s1);
    cm1.setName("West St Lofts");
    cm1.setDesc("My apartment building. Close to West St Deli and 1+1");
    cm1.setCmLatit(42.0255686);
    cm1.setCmLongit(-93.6585319);

    cm4 = new CustomMarker();
    cm4.setCmID(4);
    cm4.setStudent(s1);
    cm4.setName("Morrill Hall Bathroom");
    cm4.setDesc("Best bathroom on campus for guys. Always clean and never full");
    cm4.setCmLatit(42.02729);
    cm4.setCmLongit(-93.6486402);

    cm3 = new CustomMarker();
    cm3.setCmID(3);
    cm3.setStudent(s2);
    cm3.setName("West St Lofts");
    cm3.setDesc("Potential apt building for next year. Like the location.");
    cm3.setCmLatit(42.0255686);
    cm3.setCmLongit(-93.6585319);

    cm2 = new CustomMarker();
    cm2.setCmID(2);
    cm2.setStudent(s2);
    cm2.setName("Convos");
    cm2.setDesc("EASILY the best dining hall on campus. Best burgers by FAR.");
    cm2.setCmLatit(42.0250716);
    cm2.setCmLongit(-93.6403595);
  }

  /**
   * Test to see if the Controller returns all of the correct CustomMarkers given a StudentID
   */
  @Test
  public void findAll()
  {
    Mockito.when(customMarkerRepository.findAllByStudentId(s1.getId(), new Sort(Sort.Direction.ASC, "name")))
            .thenReturn(Arrays.asList(cm4, cm1));
    Iterable<CustomMarker> cm = controller.getCustomMarkers(s1.getId(), "all", null);
    assertEquals(customMarkerRepository.findAllByStudentId(s1.getId(), new Sort(Sort.Direction.ASC, "name")), cm);
    Mockito.verify(customMarkerRepository, Mockito.times(2)).findAllByStudentId(s1.getId(),
                                                                                new Sort(Sort.Direction.ASC, "name"));

    Mockito.when(customMarkerRepository.findAllByStudentId(s2.getId(), new Sort(Sort.Direction.ASC, "name")))
            .thenReturn(Arrays.asList(cm2, cm3));
    cm = controller.getCustomMarkers(s2.getId(), "all", null);
    assertEquals(customMarkerRepository.findAllByStudentId(s2.getId(), new Sort(Sort.Direction.ASC, "name")), cm);
    Mockito.verify(customMarkerRepository, Mockito.times(2)).findAllByStudentId(s2.getId(),
                                                                                new Sort(Sort.Direction.ASC, "name"));

  }

  /**
   * Test to see if the Controller returns an empty list if given a Student with no CustomMarkers
   */
  @Test
  public void findStudentWithNoCMs()
  {
    Student s3 = new Student();
    s3.setId(3);
    Mockito.when(customMarkerRepository.findAllByStudentId(s3.getId(), new Sort(Sort.Direction.ASC, "name")))
            .thenReturn(Collections.emptyList());
    Iterable<CustomMarker> cm = controller.getCustomMarkers(s3.getId(), "all", null);
    assertEquals(customMarkerRepository.findAllByStudentId(s3.getId(), new Sort(Sort.Direction.ASC, "name")), cm);
    Mockito.verify(customMarkerRepository, Mockito.times(2)).findAllByStudentId(s3.getId(),
                                                                                new Sort(Sort.Direction.ASC, "name"));
  }

  @Test
  public void findAllInvalidStudent()
  {

  }

  @Test
  public void findByIdAndStudent()
  {

  }

  @Test
  public void findByNameAndStudent()
  {

  }

  @Test
  public void findByStartNameAndStudent()
  {

  }
}
