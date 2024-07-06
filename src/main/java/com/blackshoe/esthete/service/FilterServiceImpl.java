package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.entity.*;
import com.blackshoe.esthete.exception.FilterErrorResult;
import com.blackshoe.esthete.exception.FilterException;
import com.blackshoe.esthete.exception.UserErrorResult;
import com.blackshoe.esthete.exception.UserException;
import com.blackshoe.esthete.repository.FilterRepository;
import com.blackshoe.esthete.repository.RepresentationImgUrlRepository;
import com.blackshoe.esthete.repository.TemporaryFilterRepository;
import com.blackshoe.esthete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService{
    private final UserRepository userRepository;
    private final FilterRepository filterRepository;
    private final TemporaryFilterRepository temporaryFilterRepository;
    private final RepresentationImgUrlRepository representationImgUrlRepository;
    @Override
    @Transactional
    public FilterDto.CreatedListResponse getCreatedFilterList(UUID userId) {
        User user = userRepository.findByUserId(userId).orElseThrow
                (() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        List<FilterDto.FilterBasicInfoResponse> createdFilterList = user.getFilters().stream()
                .map(filter -> FilterDto.FilterBasicInfoResponse.builder()
                        .filterId(filter.getFilterId().toString())
                        .filterName(filter.getName())
                        .filterThumbnailUrl(filter.getThumbnailUrl().getCloudfrontUrl())
                        .build())
                .collect(Collectors.toList());

        FilterDto.CreatedListResponse createdFilterListResponse = FilterDto.CreatedListResponse.builder()
                .createdFilterList(createdFilterList)
                .build();

        return createdFilterListResponse;
    }

    @Override
    @Transactional
    public FilterDto.PurchasedListResponse getPurchasedFilterList(UUID userId) {

        User user = userRepository.findByUserId(userId).orElseThrow
                (() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        List<FilterDto.FilterBasicInfoResponse> purchasedFilterList = user.getPurchasings().stream()
                .map(purchasing -> FilterDto.FilterBasicInfoResponse.builder()
                        .filterId(purchasing.getFilter().getFilterId().toString())
                        .filterName(purchasing.getFilter().getName())
                        .filterThumbnailUrl(purchasing.getFilter().getThumbnailUrl().getCloudfrontUrl())
                        .build())
                .collect(Collectors.toList());

        FilterDto.PurchasedListResponse purchasedFilterListResponse = FilterDto.PurchasedListResponse.builder()
                .purchasedFilterList(purchasedFilterList)
                .build();

        return purchasedFilterListResponse;
    }

    @Override
    @Transactional
    public FilterDto.AttributeResponse getFilterAttributes(UUID filterId) {

        Filter filter = filterRepository.findByFilterId(filterId).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));

        Attribute attribute = filter.getAttribute();

        FilterDto.AttributeResponse filterAttributesResponse = FilterDto.AttributeResponse
                .builder()
                .filterId(filter.getStringId())
                .brightness(attribute.getBrightness())
                .contrast(attribute.getContrast())
                .saturation(attribute.getSaturation())
                .exposure(attribute.getExposure())
                .sharpness(attribute.getSharpness())
                .hue(attribute.getHue())
                .temperature(attribute.getTemperature())
                .grayScale(attribute.getGrayScale())
                .build();

        return filterAttributesResponse;
    }

    @Override
    @Transactional
    public FilterDto.ThumbnailResponse getFilterThumbnail(UUID filterId) {

        Filter filter = filterRepository.findByFilterId(filterId).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));

        FilterDto.ThumbnailResponse filterThumbnailResponse = FilterDto.ThumbnailResponse.builder()
                .filterThumbnailUrl(filter.getThumbnailUrl().getCloudfrontUrl())
                .build();

        return filterThumbnailResponse;
    }

    @Override
    @Transactional
    public FilterDto.RepresentationImgListResponse getFilterRepresentations(UUID filterId) {

        Filter filter = filterRepository.findByFilterId(filterId).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));

        List<String> representationImgList = filter.getRepresentationImgUrls().stream()
                .map(representation -> representation.getCloudfrontUrl())
                .collect(Collectors.toList());

        FilterDto.RepresentationImgListResponse representationImgListResponse = FilterDto.RepresentationImgListResponse.builder()
                .representationImgList(representationImgList)
                .build();

        return representationImgListResponse;
    }

    @Override
    @Transactional
    public FilterDto.FilterDetailsResponse getDetails(UUID filterId, UUID userId) {

        Filter filter = filterRepository.findByFilterId(filterId).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));

        User user = userRepository.findByUserId(userId).orElseThrow
                (() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        List<String> filterTagList = filter.getFilterTags().stream()
                .map(filterTag -> filterTag.getTag().getStringId())
                .collect(Collectors.toList());

        FilterDto.FilterTagListResponse filterTagListResponse = FilterDto.FilterTagListResponse.builder()
                .filterTagList(filterTagList)
                .build();

        List<String> representationImgList = filter.getRepresentationImgUrls().stream()
                .map(representation -> representation.getCloudfrontUrl())
                .collect(Collectors.toList());

        FilterDto.RepresentationImgListResponse representationImgListResponse = FilterDto.RepresentationImgListResponse.builder()
                .representationImgList(representationImgList)
                .build();

        Attribute attribute = filter.getAttribute();

        FilterDto.AttributeResponse filterAttributesResponse = FilterDto.AttributeResponse
                .builder()
                .brightness(attribute.getBrightness())
                .contrast(attribute.getContrast())
                .saturation(attribute.getSaturation())
                .exposure(attribute.getExposure())
                .sharpness(attribute.getSharpness())
                .hue(attribute.getHue())
                .temperature(attribute.getTemperature())
                .grayScale(attribute.getGrayScale())
                .build();

        FilterDto.FilterDetailsResponse filterDetailResponse = FilterDto.FilterDetailsResponse.builder()
                .filterAttributes(filterAttributesResponse)
                .filterThumbnail(filter.getThumbnailUrl().getCloudfrontUrl())
                .representationImgList(representationImgListResponse)
                .filterTagList(filterTagListResponse)
                .likeCount(filter.getLikeCount())
                .userId(user.getStringId())
                .profileImgUrl(user.getProfileImgUrl())
                .nickname(user.getNickname())
                .isLike(filter.getLikes().stream().anyMatch(like -> like.getUser().equals(user)))
                .createdAt(filter.getCreatedAt())
                .build();

        return filterDetailResponse;
    }

    @Override
    public void deleteFilter(UUID userId, UUID filterId) {

    }

    @Override
    public void deleteTemporaryFilter(UUID userId, UUID temporaryFilterId) {

    }

    @Override
    public Page<FilterDto.ReadTemporaryDetailsInfoResponse> readTemporaryFilter(UUID userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        List<TemporaryFilter> temporaryFilters = temporaryFilterRepository.findByUserId(userId, pageable);
        List<FilterDto.ReadTemporaryDetailsInfoResponse> readTemporaryDetailsInfoResponse = new ArrayList<>();
        List<String> representationImgCloudfrontUrl = new ArrayList<>();

        for(TemporaryFilter temporaryFilter : temporaryFilters){
            List<RepresentationImgUrl> representationImgUrls = representationImgUrlRepository.findAllByTemporaryFilter(temporaryFilter).orElseGet(
                    ArrayList::new
            );

            for(RepresentationImgUrl representationImgUrl : representationImgUrls){
                representationImgCloudfrontUrl.add(representationImgUrl.getCloudfrontUrl());
            }

            Attribute attribute = temporaryFilter.getAttribute() != null ? temporaryFilter.getAttribute() : Attribute.builder().build();
            String thumbnailUrl = temporaryFilter.getThumbnailUrl() != null ? temporaryFilter.getThumbnailUrl().getCloudfrontUrl() : "";

            FilterDto.ReadTemporaryDetailsInfoResponse readTemporaryDetailsInfo = FilterDto.ReadTemporaryDetailsInfoResponse.builder()
                    .representationImgList(FilterDto.RepresentationImgListResponse.builder()
                            .representationImgList(representationImgCloudfrontUrl)
                            .build())
                    .temporaryFilterId(temporaryFilter.getTemporaryFilterId())
                    .filterThumbnail(thumbnailUrl)
                    .filterAttributes(FilterDto.AttributeResponse.builder()
                            .brightness(attribute.getBrightness())
                            .contrast(attribute.getContrast())
                            .saturation(attribute.getSaturation())
                            .exposure(attribute.getExposure())
                            .sharpness(attribute.getSharpness())
                            .hue(attribute.getHue())
                            .temperature(attribute.getTemperature())
                            .grayScale(attribute.getGrayScale())
                            .build())
                    .representationImgList(FilterDto.RepresentationImgListResponse.builder()
                            .build())
                    .build();

            readTemporaryDetailsInfoResponse.add(readTemporaryDetailsInfo);
        }

        Page<FilterDto.ReadTemporaryDetailsInfoResponse> readBasicInfoOfTemporaryFilter = new PageImpl<>(readTemporaryDetailsInfoResponse, pageable, temporaryFilters.size());

        return readBasicInfoOfTemporaryFilter;
    }
}
