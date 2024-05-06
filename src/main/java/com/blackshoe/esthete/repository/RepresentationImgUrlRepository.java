package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.RepresentationImgUrl;
import com.blackshoe.esthete.entity.TemporaryFilter;
import com.blackshoe.esthete.entity.ThumbnailUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepresentationImgUrlRepository extends JpaRepository<RepresentationImgUrl, Long> {
    Optional<List<RepresentationImgUrl>> findAllByTemporaryFilter(TemporaryFilter temporaryFilter);
    Boolean existsAllByTemporaryFilter(TemporaryFilter temporaryFilter);
}
