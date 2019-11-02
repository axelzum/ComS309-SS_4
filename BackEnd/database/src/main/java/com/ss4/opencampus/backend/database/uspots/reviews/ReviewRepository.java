package com.ss4.opencampus.backend.database.uspots.reviews;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer>
{
  Iterable<Review> findAllByUSpotUsID(Integer u, Sort sort);

  Optional<Review> findByRIdAndUSpotUsID(Integer rId, Integer uspotId);
}
