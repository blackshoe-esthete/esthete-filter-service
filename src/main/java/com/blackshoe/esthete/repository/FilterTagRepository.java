package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.FilterTag;
import com.blackshoe.esthete.entity.TemporaryFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface FilterTagRepository extends JpaRepository<FilterTag, Long> {
    Optional<List<FilterTag>> findAllByTemporaryFilter(TemporaryFilter temporaryFilter);

    Boolean existsAllByTemporaryFilter(TemporaryFilter findTemporaryFilter);
}
