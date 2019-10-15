package com.ss4.opencampus.backend.database.buildings;

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
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BuildingsTests
{
  private static Building building1;
  private static Building building2;
  private static Building building3;

  @Mock
  private BuildingRepository buildingRepository;

  @InjectMocks
  private BuildingController buildingController;

  @Before
  public void init()
  {
    building1 = new Building();
    building1.setId(1);
    building1.setBuildingName("West St Lofts");
    building1.setAbbreviation("WSL");
    building1.setAddress("2811 West St");
    building1.setLatit(42.0255686);
    building1.setLongit(-93.6585319);

    building2 = new Building();
    building2.setId(2);
    building2.setBuildingName("Jack Trice Stadium");
    building2.setAbbreviation("JTS");
    building2.setAddress("1732 S 4th St");
    building2.setLatit(42.0159517);
    building2.setLongit(-93.6371199);

    building3 = new Building();
    building3.setId(3);
    building3.setBuildingName("West Hy-Vee");
    building3.setAbbreviation("WHV");
    building3.setAddress("3800 Lincoln Way");
    building3.setLatit(42.0187128);
    building3.setLongit(-93.6633696);
  }

  @Test
  public void findAllNoBuildings()
  {
    Mockito.when(buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName"))).thenReturn(Collections.emptyList());
    Iterable<Building> b = buildingController.getBuildingLists("all", null, null);
    assertEquals(buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName")), b);
    Mockito.verify(buildingRepository, Mockito.times(2)).findAll(new Sort(Sort.Direction.ASC, "buildingName"));
  }

  @Test
  public void findAllWithBuildings()
  {
    Mockito.when(buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName"))).thenReturn(
            Arrays.asList(building2,
                          building3,
                          building1));
    Iterable<Building> b = buildingController.getBuildingLists("all", null, null);
    assertEquals(buildingRepository.findAll(new Sort(Sort.Direction.ASC, "buildingName")), b);
    Mockito.verify(buildingRepository, Mockito.times(2)).findAll(new Sort(Sort.Direction.ASC, "buildingName"));
  }

  @Test
  public void findByName()
  {
    Mockito.when(buildingRepository.findByBuildingName("West St Lofts")).thenReturn(
            Collections.singletonList(building1));
    Iterable<Building> b = buildingController.getBuildingLists("name", "West St Lofts", null);
    assertEquals(buildingRepository.findByBuildingName("West St Lofts"), b);
    Mockito.verify(buildingRepository, Mockito.times(2)).findByBuildingName("West St Lofts");
  }

  @Test
  public void findByNameStart()
  {
    Mockito.when(buildingRepository.findAllByBuildingNameStartingWith("West")).thenReturn(Arrays.asList(building1,
                                                                                                        building3));
    Iterable<Building> b = buildingController.getBuildingLists("nameStartsWith", "West", null);
    assertEquals(buildingRepository.findAllByBuildingNameStartingWith("West"), b);
    Mockito.verify(buildingRepository, Mockito.times(2)).findAllByBuildingNameStartingWith("West");
  }

  @Test
  public void findByAddress()
  {
    Mockito.when(buildingRepository.findByAddress("1732 S 4th St")).thenReturn(Collections.singletonList(building2));
    Iterable<Building> b = buildingController.getBuildingLists("address", "1732 S 4th St", null);
    assertEquals(buildingRepository.findByAddress("1732 S 4th St"), b);
    Mockito.verify(buildingRepository, Mockito.times(2)).findByAddress("1732 S 4th St");
  }

  public void findByAbbrev()
  {
    Mockito.when(buildingRepository.findByAbbreviation("JTS")).thenReturn(Collections.singletonList(building2));
    Iterable<Building> b = buildingController.getBuildingLists("abbreviation", "JTS", null);
    assertEquals(buildingRepository.findByAbbreviation("JTS"), b);
    Mockito.verify(buildingRepository, Mockito.times(2)).findByAbbreviation("JTS");
  }

  public void findByAbbrevStart()
  {
    Mockito.when(buildingRepository.findAllByAbbreviationStartingWith("W")).thenReturn(
            Arrays.asList(building1, building3));
    Iterable<Building> b = buildingController.getBuildingLists("abbreviationStartsWith", "W", null);
    assertEquals(buildingRepository.findByAbbreviation("W"), b);
    Mockito.verify(buildingRepository, Mockito.times(2)).findAllByAbbreviationStartingWith("W");
  }

  public void findByLatLong()
  {
    Mockito.when(buildingRepository.findByLatitAndLongit(42.0187128, -93.6633696)).thenReturn(
            Collections.singletonList(building3));
    Iterable<Building> b = buildingController.getBuildingLists("location", 42.0187128, -93.6633696);
    assertEquals(buildingRepository.findByLatitAndLongit(42.0187128, -93.6633696), b);
    Mockito.verify(buildingRepository, Mockito.times(2)).findByLatitAndLongit(42.0187128, -93.6633696);
  }

  @Test
  public void findById()
  {
    Mockito.when(buildingRepository.findById(3)).thenReturn(Optional.of(building3));
    Optional<Building> b = buildingController.getBuildingById(3);
    assertTrue(b.isPresent());
    assertEquals(building3, b.get());
  }


}
