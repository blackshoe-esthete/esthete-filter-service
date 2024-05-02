package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.entity.Filter;
import com.blackshoe.esthete.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long>{

    @Query("SELECT f FROM Filter f WHERE f.filterId = :filterId")
    Optional<Filter> findByFilterId(UUID filterId);

    //join filter, user, like
    @Query("SELECT new com.blackshoe.esthete.dto.FilterDto$SearchFilterResponse(f, u, :viewerId, l)" +
            " FROM Filter f JOIN f.user u LEFT JOIN Like l ON f.filterId = l.filter.filterId AND l.userId = :viewerId" +
            " WHERE f.name LIKE %:keyword% OR u.nickname LIKE %:keyword% ORDER BY f.viewCount DESC, f.createdAt DESC")
    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContaining(@Param("viewerId") UUID viewerId, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT new com.blackshoe.esthete.dto.FilterDto$SearchFilterResponse(f, u, :viewerId, l) " +
            "FROM Filter f " +
            "JOIN f.user u " +
            "LEFT JOIN Like l ON f.filterId = l.filter.filterId AND l.userId = :viewerId " +
            "JOIN FilterTag ft ON f = ft.filter " +
            "JOIN Tag t ON ft.tag = t AND t = :tag " +
            "WHERE (f.name LIKE %:keyword% OR u.nickname LIKE %:keyword%) " +
            "ORDER BY f.viewCount DESC, f.createdAt DESC")
    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContainingAndHasTag(@Param("viewerId") UUID viewerId, @Param("tag") Tag tag, @Param("keyword") String keyword, Pageable pageable);

    //keyword is null
    @Query("SELECT new com.blackshoe.esthete.dto.FilterDto$SearchFilterResponse(" +
            "f, u, :viewerId, l) " +
            "FROM Filter f " +
            "JOIN f.user u " +
            "LEFT JOIN Like l ON f.filterId = l.filter.filterId AND l.userId = :viewerId " +
            "ORDER BY f.viewCount DESC, f.createdAt DESC")
    Page<FilterDto.SearchFilterResponse> searchAll(@Param("viewerId") UUID viewerId, Pageable pageable);

}
