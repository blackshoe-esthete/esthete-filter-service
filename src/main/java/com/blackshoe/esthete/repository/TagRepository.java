package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t WHERE t.tagId = :tagId")
    Optional<Tag> findByTagId(UUID tagId);
}
