package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.Attribute;
import com.blackshoe.esthete.entity.TemporaryFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    Optional<Attribute> findByTemporaryFilter(TemporaryFilter temporaryFilter);
}
