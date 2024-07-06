package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.dto.LikeDto;
import com.blackshoe.esthete.entity.Filter;
import com.blackshoe.esthete.entity.Like;
import com.blackshoe.esthete.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    /*
    private String filterId;
        private String filterName;
        private String filterThumbnailUrl;
     */
    @Query("SELECT new com.blackshoe.esthete.dto.LikeDto$ReadResponse(f.filterId, f.name, f.thumbnailUrl.cloudfrontUrl) " +
            "FROM Like l " +
            "JOIN l.filter f " +
            "WHERE l.userId = :userId")
    Page<LikeDto.ReadResponse> readByUserId(UUID userId, Pageable pageable);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.user = :user AND l.filter = :filter")
    Optional<Boolean> existsByUserAndFilter(User user, Filter filter);

    @Query("SELECT l FROM Like l WHERE l.user = :user AND l.filter = :filter")
    Optional<Like> findByUserAndFilter(User user, Filter filter);
}
