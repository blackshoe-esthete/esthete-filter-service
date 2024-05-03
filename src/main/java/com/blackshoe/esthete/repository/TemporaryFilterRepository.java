package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.TemporaryFilter;
import com.blackshoe.esthete.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TemporaryFilterRepository extends JpaRepository<TemporaryFilter, Long> {
    Optional<TemporaryFilter> findByTemporaryFilterId(UUID temporaryFilterId);
    Boolean existsByTemporaryFilterId(UUID temporaryFilterId);

}
