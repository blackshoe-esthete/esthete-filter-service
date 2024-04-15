package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;

import java.util.UUID;

public interface FilterService {
    FilterDto.CreatedFilterListResponse getCreatedFilterList(UUID userId);
    FilterDto.PurchasedFilterListResponse getPurchasedFilterList(UUID userId);
}
