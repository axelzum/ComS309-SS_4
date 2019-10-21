package com.ss4.opencampus.backend.database.students.custommarkers;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomMarkerRepository extends JpaRepository<CustomMarker, Integer>
{
  Iterable<CustomMarker> findAllByStudentId(Integer studentId, Sort sort);

  Optional<CustomMarker> findByCmIdAndStudentId(Integer cmId, Integer studentId);

  Iterable<CustomMarker> findByNameAndStudentId(String name, Integer studentId);

  Iterable<CustomMarker> findAllByNameStartingWithAndStudentId(String nameStart, Integer studentId);
}
