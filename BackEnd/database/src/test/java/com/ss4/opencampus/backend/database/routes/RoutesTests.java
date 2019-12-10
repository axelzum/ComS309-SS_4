package com.ss4.opencampus.backend.database.routes;

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

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Willis Knox
 * <p>
 * Test class to make sure RouteService works properly. Mocks a RouteRepository to do this
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RoutesTests
{
  private static Route r1;
  private static Route r2;

  private static Student s1;

  @Mock
  private RouteRepository routeRepository;

  @InjectMocks
  private RouteService routeService;

  /**
   * Initialize fake Routes and Students before every test
   */
  @Before
  public void init()
  {
    r1 = new Route("Route 1", 100.252, -93.5252, 131.88, 154.0);
    r2 = new Route("My Other Route", 250.0, -87.25, 200.88, 14.0);
    s1 = new Student("willis", "knox", "w_K_Pop", "email@email.com", "pdd");
    s1.setId(1);
    r1.setStudent(s1);
    r2.setStudent(s1);
    r1.setId(1);
    r2.setId(2);
  }

  /**
   * Test to see if the routeService correctly returns all routes organized by name
   */
  @Test
  public void findAll()
  {
    Mockito.when(routeRepository.findAllByStudentId(s1.getId(), new Sort(Sort.Direction.ASC, "rtName")))
            .thenReturn(Arrays.asList(r2, r1));
    Iterable<Route> routes = routeService.getRoutes(s1.getId(), "all", null);
    assertEquals(routeRepository.findAllByStudentId(s1.getId(), new Sort(Sort.Direction.ASC, "rtName")), routes);
    Mockito.verify(routeRepository, Mockito.times(2)).
            findAllByStudentId(s1.getId(), new Sort(Sort.Direction.ASC, "rtName"));
  }

  /**
   * Test to see if the routeService returns the correct Route if given an ID
   */
  @Test
  public void findById()
  {
    Mockito.when(routeRepository.findByIdAndStudentId(r1.getId(), s1.getId()))
            .thenReturn(Optional.of(r1));
    Optional<Route> routes = routeService.getById(r1.getId(), s1.getId());
    assertTrue(routes.isPresent());
    assertEquals(routeRepository.findByIdAndStudentId(r1.getId(), s1.getId()), routes);
    Mockito.verify(routeRepository, Mockito.times(2)).
            findByIdAndStudentId(r1.getId(), s1.getId());
  }

  /**
   * Test to see if the routeService returns the correct Route if given a name
   */
  @Test
  public void findByName()
  {
    Mockito.when(routeRepository.findByRtNameAndStudentId(r2.getRtName(), s1.getId()))
            .thenReturn(Collections.singletonList(r2));
    Iterable<Route> routes = routeService.getRoutes(s1.getId(), "rtName", "My Other Route");
    assertEquals(routeRepository.findByRtNameAndStudentId(r2.getRtName(), s1.getId()), routes);
    Mockito.verify(routeRepository, Mockito.times(2)).
            findByRtNameAndStudentId(r2.getRtName(), s1.getId());
  }

  /**
   * Test to see if the patch() method works. Creates a map with two updates. First checks to see if The getById() works
   * and saves the found Route into an object.
   * <p>
   * Then patches the routeService with the updated information. Then checks to see if this update went through to the
   * service AND the Route object that was found earlier with the getById() test.
   */
  @Test
  public void patchTest()
  {
    Map<String, Object> map = new HashMap<>();
    map.put("rtName", "Corrected Route 1 Information");
    map.put("originLat", 100.250125);
    Mockito.when(routeRepository.findByIdAndStudentId(r1.getId(), s1.getId())).thenReturn(Optional.of(r1));
    Optional<Route> route = routeService.getById(r1.getId(), s1.getId());
    assertEquals(routeRepository.findByIdAndStudentId(r1.getId(), s1.getId()).get().getRtName(),
                 route.get().getRtName());
    routeService.patch(r1.getId(), s1.getId(), map);
    assertEquals(routeRepository.findByIdAndStudentId(r1.getId(), s1.getId()).get().getRtName(), "Corrected Route 1 " +
            "Information");
    assertEquals(route.get().getRtName(), "Corrected Route 1 " +
            "Information");
    Mockito.verify(routeRepository, Mockito.times(4)).findByIdAndStudentId(r1.getId(), s1.getId());
  }


}
