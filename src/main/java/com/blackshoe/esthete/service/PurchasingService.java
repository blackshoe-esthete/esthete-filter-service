package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;

import java.util.UUID;

public interface PurchasingService {
    FilterDto.PurchaseResponse purchaseFilter(FilterDto.PurchaseRequest purchaseRequest, UUID userId);
}
