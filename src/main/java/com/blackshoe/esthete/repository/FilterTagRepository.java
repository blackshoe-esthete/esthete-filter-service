package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.Filter;
import com.blackshoe.esthete.entity.FilterTag;
import com.blackshoe.esthete.entity.TemporaryFilter;
import com.blackshoe.esthete.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface FilterTagRepository extends JpaRepository<FilterTag, Long> {
    Optional<List<FilterTag>> findAllByTemporaryFilter(TemporaryFilter temporaryFilter);

    Boolean existsAllByTemporaryFilter(TemporaryFilter findTemporaryFilter);

    @Modifying
    @Query("DELETE FROM FilterTag ft WHERE ft.filter = :filter")
    void deleteByFilter(Filter filter);

    @Modifying
    @Query("DELETE FROM FilterTag ft WHERE ft.filter.user = :user")
    void deleteByUserOfFilter(User user);

    @Modifying
    @Query("DELETE FROM FilterTag ft WHERE ft.temporaryFilter.user = :user")
    void deleteByUserOfTemporaryFilter(User user);
}
