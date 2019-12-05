package com.ss4.opencampus.backend.database.routes;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Willis Knox
 */
public interface RouteRepository extends JpaRepository<Route, Integer>
{
  Iterable<Route> findByRtNameAndStudentId(String rtName, Integer studentId);
  Iterable<Route> findAllByStudentId(Integer studentId, Sort sort);
  Optional<Route> findByIdAndStudentId(Integer id, Integer studentId);
}
