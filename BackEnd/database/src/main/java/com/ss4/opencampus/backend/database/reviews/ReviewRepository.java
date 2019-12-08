package com.ss4.opencampus.backend.database.reviews;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Willis Knox
 * <p>
 * Repository that handles ways to query the Database for Reviews. Implements two special ways to find Reviews that are
 * needed for our project.
 */
public interface ReviewRepository extends JpaRepository<Review, Integer>
{
  Iterable<Review> findAllByUSpotUsID(Integer u, Sort sort);

  Optional<Review> findByRIdAndUSpotUsID(Integer rId, Integer uspotId);
}
