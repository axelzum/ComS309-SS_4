package com.ss4.opencampus.backend.database.custommarkers;

import com.ss4.opencampus.backend.database.students.Student;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
  private CustomMarkerRepository customMarkerRepository;

  @InjectMocks
  private CustomMarkerService customMarkerService;

  /**
   * Initialize fake CustomMarkers and Students that created the CustomMarkers before every test
   */
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
    cm1.setId(1);
    cm1.setStudent(s1);
    cm1.setName("West St Lofts");
    cm1.setDesc("My apartment building. Close to West St Deli and 1+1");
    cm1.setCmLatit(42.0255686);
    cm1.setCmLongit(-93.6585319);

    cm4 = new CustomMarker();
    cm4.setId(4);
    cm4.setStudent(s1);
    cm4.setName("Morrill Hall Bathroom");
    cm4.setDesc("Best bathroom on campus for guys. Always clean and never full");
    cm4.setCmLatit(42.02729);
    cm4.setCmLongit(-93.6486402);

    cm3 = new CustomMarker();
    cm3.setId(3);
    cm3.setStudent(s2);
    cm3.setName("West St Lofts");
    cm3.setDesc("Potential apt building for next year. Like the location.");
    cm3.setCmLatit(42.0255686);
    cm3.setCmLongit(-93.6585319);

    cm2 = new CustomMarker();
    cm2.setId(2);
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
    Iterable<CustomMarker> cm = customMarkerService.getCustomMarkers(s1.getId(), "all", null);
    assertEquals(customMarkerRepository.findAllByStudentId(s1.getId(), new Sort(Sort.Direction.ASC, "name")), cm);
    Mockito.verify(customMarkerRepository, Mockito.times(2)).findAllByStudentId(s1.getId(),
                                                                                new Sort(Sort.Direction.ASC, "name"));

    Mockito.when(customMarkerRepository.findAllByStudentId(s2.getId(), new Sort(Sort.Direction.ASC, "name")))
            .thenReturn(Arrays.asList(cm2, cm3));
    cm = customMarkerService.getCustomMarkers(s2.getId(), "all", null);
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
    Iterable<CustomMarker> cm = customMarkerService.getCustomMarkers(s3.getId(), "all", null);
    assertEquals(customMarkerRepository.findAllByStudentId(s3.getId(), new Sort(Sort.Direction.ASC, "name")), cm);
    Mockito.verify(customMarkerRepository, Mockito.times(2)).findAllByStudentId(s3.getId(),
                                                                                new Sort(Sort.Direction.ASC, "name"));
  }

  /**
   * Test to see if the Controller returns an empty list if given a StudentID that isn't in the database
   */
  @Test
  public void findAllInvalidStudent()
  {
    Mockito.when(customMarkerRepository.findAllByStudentId(3, new Sort(Sort.Direction.ASC, "name")))
            .thenReturn(Collections.emptyList());
    Iterable<CustomMarker> cm = customMarkerService.getCustomMarkers(3, "all", null);
    assertEquals(customMarkerRepository.findAllByStudentId(3, new Sort(Sort.Direction.ASC, "name")), cm);
    Mockito.verify(customMarkerRepository, Mockito.times(2)).findAllByStudentId(3,
                                                                                new Sort(Sort.Direction.ASC, "name"));
  }

  /**
   * Test to see if the Controller returns a single CustomMarker if given a CustomMarkerID and StudentID
   */
  @Test
  public void findByIdAndStudent()
  {
    Mockito.when(customMarkerRepository.findByCmIdAndStudentId(cm1.getId(), s1.getId())).thenReturn(Optional.of(cm1));
    Optional<CustomMarker> cm = customMarkerService.getById(s1.getId(), cm1.getId());
    assertTrue(cm.isPresent());
    assertEquals(cm1, cm.get());
  }

  /**
   * Test to see if the Controller returns a single CustomMarker if given the CustomMarker name and the Student
   * the CustomMarker is for
   */
  @Test
  public void findByNameAndStudent()
  {
    Mockito.when(customMarkerRepository.findByNameAndStudentId("Convos", s2.getId())).thenReturn(
            Collections.singletonList(cm2));
    Iterable<CustomMarker> cm = customMarkerService.getCustomMarkers(s2.getId(), "name", "Convos");
    assertEquals(customMarkerRepository.findByNameAndStudentId("Convos", s2.getId()), cm);
    Mockito.verify(customMarkerRepository, Mockito.times(2)).findByNameAndStudentId("Convos", s2.getId());
  }

  /**
   * Test to see if the Controller returns all CustomMarkers for a particular Student who's names start with a given
   * String
   */
  @Test
  public void findByStartNameAndStudent()
  {
    Mockito.when(customMarkerRepository.findAllByNameStartingWithAndStudentId("W", s1.getId())).thenReturn(
            Collections.singletonList(cm1));
    Iterable<CustomMarker> cm = customMarkerService.getCustomMarkers(s1.getId(), "nameStartsWith", "W");
    assertEquals(customMarkerRepository.findAllByNameStartingWithAndStudentId("W", s1.getId()), cm);
    Mockito.verify(customMarkerRepository, Mockito.times(2)).findAllByNameStartingWithAndStudentId("W", s1.getId());
  }
}
