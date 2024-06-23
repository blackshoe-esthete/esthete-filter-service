package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;

import java.util.List;
import java.util.UUID;

public interface FilterService {
    FilterDto.CreatedListResponse getCreatedFilterList(UUID userId);
    FilterDto.PurchasedListResponse getPurchasedFilterList(UUID userId);

    FilterDto.AttributeResponse getFilterAttributes(UUID filterId);

    FilterDto.ThumbnailResponse getFilterThumbnail(UUID filterId);

    FilterDto.RepresentationImgListResponse getFilterRepresentations(UUID filterId);

    FilterDto.FilterDetailsResponse getDetails(UUID filterId, UUID userId);

    void deleteFilter(UUID userId, UUID filterId);
}
