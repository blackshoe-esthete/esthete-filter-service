package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.dto.LikeDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface LikeService {
    Page<LikeDto.ReadResponse> getLikeFilterList(UUID userId, int page, int size);

    void likeFilter(UUID userId, UUID filterId);

    void unlikeFilter(UUID userId, UUID filterId);
}
