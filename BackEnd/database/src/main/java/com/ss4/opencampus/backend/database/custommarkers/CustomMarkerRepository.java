package com.ss4.opencampus.backend.database.custommarkers;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Willis Knox Repository for different ways to Query the MySQL CustomMarkers table
 */
public interface CustomMarkerRepository extends JpaRepository<CustomMarker, Integer>
{
  /**
   * Find all CustomMarkers tied to a Student
   *
   * @param studentId
   *         id of Student
   * @param sort
   *         Sort by CustomMarker name
   *
   * @return Iterable List of CustomMarkers the Student has made
   */
  Iterable<CustomMarker> findAllByStudentId(Integer studentId, Sort sort);

  /**
   * Find a specific CustomMarker a Student has made. Used when the CustomMarker is clicked on
   *
   * @param cmId
   *         id of CustomMarker
   * @param studentId
   *         id of the Student
   *
   * @return Specific CustomMarker
   */
  Optional<CustomMarker> findByCmIdAndStudentId(Integer cmId, Integer studentId);

  /**
   * Finds a specific CustomMarker by Name
   *
   * @param name
   *         Name of CustomMarker
   * @param studentId
   *         id of Student who made the marker
   *
   * @return Iterable List of 0 or 1 CustomMarkers
   */
  Iterable<CustomMarker> findByNameAndStudentId(String name, Integer studentId);

  /**
   * Finds a list of CustomMarkers who's names start with given parameter
   *
   * @param nameStart
   *         start of name of CustomMarkers
   * @param studentId
   *         id of Student who made the markers
   *
   * @return List of CustomMarkers who's names start with the parameter
   */
  Iterable<CustomMarker> findAllByNameStartingWithAndStudentId(String nameStart, Integer studentId);
}
