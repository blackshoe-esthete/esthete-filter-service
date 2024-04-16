package com.blackshoe.esthete.repository;


import com.blackshoe.esthete.entity.Attribute;
import com.blackshoe.esthete.entity.TemporaryFilter;
import com.blackshoe.esthete.entity.ThumbnailUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ThumbnailUrlRepository extends JpaRepository<ThumbnailUrl, Long> {
    Optional<ThumbnailUrl> findByTemporaryFilter(TemporaryFilter temporaryFilter);
}
