package com.ss4.opencampus.backend.database.floors;

import com.ss4.opencampus.backend.database.buildings.Building;
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
import static org.junit.Assert.assertTrue;

/**
 * @author Axel Zumwalt
 * <p>
 * Test class to make sure FloorPlans works properly. Mocks a FloorPlanRepository to do this
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FloorPlanTests
{
  private static FloorPlan f1;
  private static FloorPlan f2;

  private static Building b1;

  @Mock
  private FloorPlanRepository floorPlanRepository;

  @InjectMocks
  private FloorPlanService floorPlanService;

  /**
   * Initialize fake FloorPlans and Buildings before every test
   */
  @Before
  public void init()
  {
    f1 = new FloorPlan("Floor 1", "1", null);
    f2 = new FloorPlan("Floor 2B", "2B", null);
    b1 = new Building("West Street Lofts", "TOUCH", "6969 420 West Street", 100.456, -93.45);
    b1.setFloorCnt(2);
    b1.setId(1);
    f1.setBuilding(b1);
    f2.setBuilding(b1);
    f1.setId(1);
    f2.setId(2);
  }

  /**
   * Test to see if the FloorPlanService correctly returns all floorPlans organized by name
   */
  @Test
  public void findAll() throws IOException {
    Mockito.when(floorPlanRepository.findAllByBuildingId(b1.getId(), new Sort(Sort.Direction.ASC, "level")))
            .thenReturn(Arrays.asList(f1, f2));
    Iterable<FloorPlan> floorPlans = floorPlanService.getFloorPlans(b1.getId());
    assertEquals(floorPlanRepository.findAllByBuildingId(b1.getId(), new Sort(Sort.Direction.ASC, "level")), floorPlans);
    Mockito.verify(floorPlanRepository, Mockito.times(2)).
            findAllByBuildingId(b1.getId(), new Sort(Sort.Direction.ASC, "level"));
  }

  /**
   * Test to see if the floorPlanService returns the correct floorPlan if given an ID
   */
  @Test
  public void findById() throws IOException {
    Mockito.when(floorPlanRepository.findByFpIdAndBuildingId(f1.getId(), b1.getId()))
            .thenReturn(Optional.of(f1));
    Optional<FloorPlan> floorPlan = floorPlanService.getById(b1.getId(), f1.getId());
    assertTrue(floorPlan.isPresent());
    assertEquals(floorPlanRepository.findByFpIdAndBuildingId(f1.getId(), b1.getId()), floorPlan);
    Mockito.verify(floorPlanRepository, Mockito.times(2)).
            findByFpIdAndBuildingId(f1.getId(), b1.getId());
  }

  /**
   * Test to see if the patch() method works. Creates a map with two updates. First checks to see if The getById() works
   * and saves the found floorPlan into an object.
   * <p>
   * Then patches the floorPlanService with the updated information. Then checks to see if this update went through to the
   * service AND the floorPlan object that was found earlier with the getById() test.
   */
  @Test
  public void patchTest() throws IOException {
    Map<String, Object> map = new HashMap<>();
    map.put("level", "1B");
    map.put("name", "Floor 1B");
    Mockito.when(floorPlanRepository.findByFpIdAndBuildingId(f2.getId(), b1.getId())).thenReturn(Optional.of(f2));
    Optional<FloorPlan> floorPlan = floorPlanService.getById(b1.getId(), f2.getId());
    assertEquals(floorPlanRepository.findByFpIdAndBuildingId(f2.getId(), b1.getId()).get().getName(),
                 floorPlan.get().getName());
    floorPlanService.patch(b1.getId(), f2.getId(), map);
    assertEquals(floorPlanRepository.findByFpIdAndBuildingId(f2.getId(), b1.getId()).get().getName(), "Floor 1B");
    assertEquals(floorPlan.get().getName(), "Floor 1B");
    Mockito.verify(floorPlanRepository, Mockito.times(4)).findByFpIdAndBuildingId(f2.getId(), b1.getId());
  }


}
