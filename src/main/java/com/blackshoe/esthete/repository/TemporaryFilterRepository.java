package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.TemporaryFilter;
import com.blackshoe.esthete.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TemporaryFilterRepository extends JpaRepository<TemporaryFilter, Long> {
    Optional<TemporaryFilter> findByTemporaryFilterId(UUID temporaryFilterId);
    Boolean existsByTemporaryFilterId(UUID temporaryFilterId);
    @Modifying
    @Query("DELETE FROM TemporaryFilter tf WHERE tf.user = :user")
    void deleteByUser(User user);
}
