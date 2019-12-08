package com.ss4.opencampus.backend.database.uspots;

import com.ss4.opencampus.backend.database.buildings.Building;
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

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Willis Knox
 * <p>
 * Test Class for USpots. Tests the multiple functionalities of the USpots and the edge cases
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class USpotsTests
{

  private static USpot u1;
  private static USpot u2;
  private static USpot u3;

  private static Student s1;
  private static Student s2;

  private static Building b;

  @Mock
  private USpotRepository uSpotRepository;

  @InjectMocks
  private USpotService uSpotService;

  /**
   * Initializes 3 USpots with fake data before each test. Also creates a fake Student and Building
   */
  @Before
  public void init()
  {
    s1 = new Student();
    s1.setId(1);
    s1.setPassword("helloworld");
    s1.setEmail("test@email.com");
    s1.setUserName("student1");
    s1.setFirstName("Student");
    s1.setLastName("1");

    s2 = new Student();
    s2.setId(2);
    s2.setPassword("helloworld");
    s2.setEmail("mymail@email.com");
    s2.setUserName("2Student2");
    s2.setFirstName("Student");
    s2.setLastName("2");

    b = new Building();
    b.setId(1);
    b.setBuildingName("Pearson Hall");
    b.setFloorCnt(4);
    b.setAddress("123 I don't know");
    b.setAbbreviation("PEAR");
    b.setLatit(42.0101);
    b.setLongit(-93.1010);

    u1 = new USpot();
    u1.setId(1);
    u1.setStudentId(1);
    u1.setUsName("LeBaron Bike Rack");
    u1.setUsRating(4.5);
    u1.setRatingTotal(9.0);
    u1.setRatingCount(2);
    u1.setUsImagePath(null);
    u1.setPicBytes(null);
    u1.setUsCategory("Bike Rack");
    u1.setUsLatit(42.00001);
    u1.setUsLongit(-93.10000);

    u2 = new USpot();
    u2.setId(2);
    u2.setStudentId(1);
    u2.setBuildingId(1);
    u2.setFloor("B");
    u2.setUsName("Secret Bathroom");
    u2.setUsRating(3.0);
    u2.setRatingTotal(3.0);
    u2.setRatingCount(1);
    u2.setUsImagePath(null);
    u2.setPicBytes(null);
    u2.setUsCategory("Bathroom");
    u2.setUsLatit(42.87654);
    u2.setUsLongit(-93.12345);

    u3 = new USpot();
    u3.setId(3);
    u3.setStudentId(2);
    u3.setUsName("Study Tree");
    u3.setUsRating(4.88);
    u3.setRatingTotal(34.16);
    u3.setRatingCount(7);
    u3.setUsImagePath(null);
    u3.setPicBytes(null);
    u3.setUsCategory("Study Spot");
    u3.setUsLatit(42.99999);
    u3.setUsLongit(-93.02525);
  }

  /**
   * Tests to see if the Controller returns all USpots in the Database. Sorted by name alphabetically
   *
   * @throws IOException
   *         Could throw an error if getting picture doesn't work
   */
  @Test
  public void findAll() throws IOException
  {
    Mockito.when(uSpotRepository.findAll(new Sort(Sort.Direction.ASC, "usName"))).thenReturn(
            Arrays.asList(u1,
                          u2,
                          u3));
    Iterable<USpot> u = uSpotService.getUSpots("all", null, null);
    assertEquals(uSpotRepository.findAll(new Sort(Sort.Direction.ASC, "usName")), u);
    Mockito.verify(uSpotRepository, Mockito.times(2)).findAll(new Sort(Sort.Direction.ASC, "usName"));
  }

  /**
   * Test to see if we can find the correct USpot when searching with a BuildingID
   *
   * @throws IOException
   *         Could throw an error if getting picture doesn't work
   */
  @Test
  public void findByBuilding() throws IOException
  {
    Mockito.when(uSpotRepository.findAllByBuildingIdAndFloor(b.getId(), "B")).thenReturn(Collections.singletonList(u2));
    Iterable<USpot> u = uSpotService.getUSpots("building", "1", "B");
    assertEquals(uSpotRepository.findAllByBuildingIdAndFloor(b.getId(), "B"), u);
    Mockito.verify(uSpotRepository, Mockito.times(2)).findAllByBuildingIdAndFloor(b.getId(), "B");
  }

  /**
   * Test to see if the USpots are updated properly on PATCH Requests
   */
  @Test
  public void patchTest()
  {
    Map<String, Object> map = new HashMap<>();
    map.put("usName", "testName");
    Mockito.when(uSpotRepository.findById(u3.getId())).thenReturn(Optional.of(u3));
    assertEquals(uSpotRepository.findById(u3.getId()).get().getUsName(), u3.getUsName());
    uSpotService.patch(map, u3.getId());
    assertEquals(u3.getUsName(), "testName");
    assertEquals(uSpotRepository.findById(u3.getId()).get().getUsName(), "testName");
    Mockito.verify(uSpotRepository, Mockito.times(3)).findById(u3.getId());
  }

}
