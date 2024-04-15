package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;

import java.util.UUID;

public interface FilterService {
    FilterDto.CreatedListResponse getCreatedFilterList(UUID userId);
    FilterDto.PurchasedListResponse getPurchasedFilterList(UUID userId);

    FilterDto.AttributeResponse getFilterAttributes(UUID filterId);

    FilterDto.ThumbnailResponse getFilterThumbnail(UUID filterId);
}
