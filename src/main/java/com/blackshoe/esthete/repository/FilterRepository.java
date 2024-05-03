package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.entity.Filter;
import com.blackshoe.esthete.entity.Tag;
import com.blackshoe.esthete.entity.TemporaryFilter;
import com.blackshoe.esthete.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FilterRepository extends JpaRepository<Filter, Long>{

    //join filter, user, like
    @Query("SELECT new com.blackshoe.esthete.dto.FilterDto$SearchFilterResponse(f, u, :viewer, l)" +
            " FROM Filter f JOIN f.user u LEFT JOIN Like l ON f.filterId = l.filter.filterId AND l.user = :viewer" +
            " WHERE f.name LIKE %:keyword% OR u.nickname LIKE %:keyword% ORDER BY f.viewCount DESC, f.createdAt DESC")
    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContaining(User viewer, String keyword, Pageable pageable);

    @Query("SELECT new com.blackshoe.esthete.dto.FilterDto$SearchFilterResponse(f, u, :viewer, l) " +
            "FROM Filter f " +
            "JOIN f.user u " +
            "LEFT JOIN Like l ON f.filterId = l.filter.filterId AND l.user = :viewer " +
            "JOIN FilterTag ft ON f = ft.filter " +
            "JOIN Tag t ON ft.tag = t AND t = :tag " +
            "WHERE (f.name LIKE %:keyword% OR u.nickname LIKE %:keyword%) " +
            "ORDER BY f.viewCount DESC, f.createdAt DESC")
    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContainingAndHasTag(User viewer, Tag tag, String keyword, Pageable pageable);

    //keyword is null
    @Query("SELECT new com.blackshoe.esthete.dto.FilterDto$SearchFilterResponse(f, u, :viewer, l)" +
            " FROM Filter f JOIN f.user u LEFT JOIN Like l ON f.filterId = l.filter.filterId AND l.user = :viewer" +
            " ORDER BY f.viewCount DESC, f.createdAt DESC")
    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContaining(User viewer, Pageable pageable);


    Optional<Filter> findByFilterId(UUID filterId);
}
