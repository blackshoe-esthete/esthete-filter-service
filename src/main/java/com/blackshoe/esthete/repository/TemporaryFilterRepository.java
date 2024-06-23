package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.entity.TemporaryFilter;
import com.blackshoe.esthete.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /*
        @Data
    @NoArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ReadTemporary {
        private String temporaryFilterId;
        private String filterThumbnail;
        private LocalDateTime createdAt;
     */
    @Query("SELECT new com.blackshoe.esthete.dto.FilterDto$ReadTemporary(tf.temporaryFilterId, tf.thumbnailUrl.cloudfrontUrl, tf.createdAt) " +
            "FROM TemporaryFilter tf WHERE tf.user.userId = :userId")
    Page<FilterDto.ReadTemporary> readBasicInfo(UUID userId, Pageable pageable);
}
