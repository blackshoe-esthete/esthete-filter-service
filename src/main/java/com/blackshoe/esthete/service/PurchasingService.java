package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;

public interface PurchasingService {
    FilterDto.PurchaseResponse purchaseFilter(FilterDto.PurchaseRequest purchaseRequest);
}
