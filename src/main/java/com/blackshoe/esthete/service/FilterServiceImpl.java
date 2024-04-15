package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.entity.Attribute;
import com.blackshoe.esthete.entity.Filter;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.exception.FilterErrorResult;
import com.blackshoe.esthete.exception.FilterException;
import com.blackshoe.esthete.exception.UserErrorResult;
import com.blackshoe.esthete.exception.UserException;
import com.blackshoe.esthete.repository.FilterRepository;
import com.blackshoe.esthete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService{
    private final UserRepository userRepository;
    private final FilterRepository filterRepository;
    @Override
    public FilterDto.CreatedFilterListResponse getCreatedFilterList(UUID userId) {
        User user = userRepository.findByUserId(userId).orElseThrow
                (() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        List<FilterDto.ViewFilterListResponse> createdFilterList = user.getFilters().stream()
                .map(filter -> FilterDto.ViewFilterListResponse.builder()
                        .filterId(filter.getFilterId().toString())
                        .filterName(filter.getName())
                        .filterThumbnailUrl(filter.getThumbnailUrl().getCloudfrontUrl())
                        .build())
                .collect(Collectors.toList());

        FilterDto.CreatedFilterListResponse createdFilterListResponse = FilterDto.CreatedFilterListResponse.builder()
                .createdFilterList(createdFilterList)
                .build();

        return createdFilterListResponse;
    }

    @Override
    public FilterDto.PurchasedFilterListResponse getPurchasedFilterList(UUID userId) {

        User user = userRepository.findByUserId(userId).orElseThrow
                (() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        List<FilterDto.ViewFilterListResponse> purchasedFilterList = user.getPurchasings().stream()
                .map(purchasing -> FilterDto.ViewFilterListResponse.builder()
                        .filterId(purchasing.getFilter().getFilterId().toString())
                        .filterName(purchasing.getFilter().getName())
                        .filterThumbnailUrl(purchasing.getFilter().getThumbnailUrl().getCloudfrontUrl())
                        .build())
                .collect(Collectors.toList());

        FilterDto.PurchasedFilterListResponse purchasedFilterListResponse = FilterDto.PurchasedFilterListResponse.builder()
                .purchasedFilterList(purchasedFilterList)
                .build();

        return purchasedFilterListResponse;
    }

    @Override
    public FilterDto.FilterAttributesResponse getFilterAttributes(UUID filterId) {

        Filter filter = filterRepository.findByFilterId(filterId).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));

        Attribute attribute = filter.getAttribute();

        FilterDto.FilterAttributesResponse filterAttributesResponse = FilterDto.FilterAttributesResponse
                .builder()
                .filterId(filter.getStringId())
                .brightness(attribute.getBrightness())
                .contrast(attribute.getContrast())
                .saturation(attribute.getSaturation())
                .exposure(attribute.getExposure())
                .sharpness(attribute.getSharpness())
                .highlights(attribute.getHighlights())
                .shadows(attribute.getShadows())
                .build();

        return filterAttributesResponse;
    }
}
