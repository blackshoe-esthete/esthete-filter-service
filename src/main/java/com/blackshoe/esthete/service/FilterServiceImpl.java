package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.entity.*;
import com.blackshoe.esthete.exception.FilterErrorResult;
import com.blackshoe.esthete.exception.FilterException;
import com.blackshoe.esthete.exception.UserErrorResult;
import com.blackshoe.esthete.exception.UserException;
import com.blackshoe.esthete.repository.*;
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
    private final FilterTagRepository filterTagRepository;
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
    public FilterDto.FilterDetailsResponse getDetails(UUID filterId, UUID viewerId) {

        Filter filter = filterRepository.findByFilterId(filterId).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));

        User filterOwner = filter.getUser(); // 필터를 게시한 사용자

        boolean isLike = false;
        if (viewerId != null) {
            User viewer = userRepository.findByUserId(viewerId).orElseThrow
                    (() -> new UserException(UserErrorResult.NOT_FOUND_USER));
            isLike = filter.getLikes().stream().anyMatch(like -> like.getUser().equals(viewer));
        }

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

        return FilterDto.FilterDetailsResponse.builder()
                .filterAttributes(filterAttributesResponse)
                .filterThumbnail(filter.getThumbnailUrl().getCloudfrontUrl())
                .representationImgList(representationImgListResponse)
                .filterTagList(filterTagListResponse)
                .filterName(filter.getName())
                .filterDescription(filter.getDescription())
                .likeCount(filter.getLikeCount())
                .userId(filterOwner.getStringId())
                .profileImgUrl(filterOwner.getProfileImgUrl())
                .nickname(filterOwner.getNickname())
                .isLike(isLike)
                .createdAt(filter.getCreatedAt())
                .build();
    }
//    @Override
//    @Transactional
//    public FilterDto.FilterDetailsResponse getDetails(UUID filterId, UUID userId) {
//
//        Filter filter = filterRepository.findByFilterId(filterId).orElseThrow
//                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));
//
//        User user = userRepository.findByUserId(userId).orElseThrow
//                (() -> new UserException(UserErrorResult.NOT_FOUND_USER));
//
//        List<String> filterTagList = filter.getFilterTags().stream()
//                .map(filterTag -> filterTag.getTag().getStringId())
//                .collect(Collectors.toList());
//
//        FilterDto.FilterTagListResponse filterTagListResponse = FilterDto.FilterTagListResponse.builder()
//                .filterTagList(filterTagList)
//                .build();
//
//        List<String> representationImgList = filter.getRepresentationImgUrls().stream()
//                .map(representation -> representation.getCloudfrontUrl())
//                .collect(Collectors.toList());
//
//        FilterDto.RepresentationImgListResponse representationImgListResponse = FilterDto.RepresentationImgListResponse.builder()
//                .representationImgList(representationImgList)
//                .build();
//
//        Attribute attribute = filter.getAttribute();
//
//        FilterDto.AttributeResponse filterAttributesResponse = FilterDto.AttributeResponse
//                .builder()
//                .brightness(attribute.getBrightness())
//                .contrast(attribute.getContrast())
//                .saturation(attribute.getSaturation())
//                .exposure(attribute.getExposure())
//                .sharpness(attribute.getSharpness())
//                .hue(attribute.getHue())
//                .temperature(attribute.getTemperature())
//                .grayScale(attribute.getGrayScale())
//                .build();
//
//        FilterDto.FilterDetailsResponse filterDetailResponse = FilterDto.FilterDetailsResponse.builder()
//                .filterAttributes(filterAttributesResponse)
//                .filterThumbnail(filter.getThumbnailUrl().getCloudfrontUrl())
//                .representationImgList(representationImgListResponse)
//                .filterTagList(filterTagListResponse)
//                .filterName(filter.getName())
//                .filterDescription(filter.getDescription())
//                .likeCount(filter.getLikeCount())
//                .userId(user.getStringId())
//                .profileImgUrl(user.getProfileImgUrl())
//                .nickname(user.getNickname())
//                .isLike(filter.getLikes().stream().anyMatch(like -> like.getUser().equals(user)))
//                .createdAt(filter.getCreatedAt())
//                .build();
//
//        return filterDetailResponse;
//    }

    @Override
    @Transactional
    public void deleteFilter(UUID userId, UUID filterId) {
        final User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new UserException(UserErrorResult.NOT_FOUND_USER)
        );

        final Filter filter = filterRepository.findByFilterId(filterId).orElseThrow(
                () -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER)
        );

        if(!filter.getUser().equals(user)){
            throw new FilterException(FilterErrorResult.NOT_FOUND_FILTER);

        }else{
            user.removeFilter(filter);
            filterRepository.delete(filter);
        }
    }

    @Override
    @Transactional
    public void deleteTemporaryFilter(UUID userId, UUID temporaryFilterId) {
        final User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new UserException(UserErrorResult.NOT_FOUND_USER)
        );
//5a0db2eb-f4bc-4fa3-ae47-8381ed0da1ab
        final TemporaryFilter temporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(
                () -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER)
        );

        if(!temporaryFilter.getUser().equals(user)){
            throw new FilterException(FilterErrorResult.NOT_FOUND_FILTER);
        }else{
            user.removeTemporaryFilter(temporaryFilter);
            temporaryFilterRepository.delete(temporaryFilter);

        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FilterDto.ReadTemporaryDetailsInfoResponse> readTemporaryFilter(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<TemporaryFilter> temporaryFilters = temporaryFilterRepository.findByUserId(userId, pageable);
        List<FilterDto.ReadTemporaryDetailsInfoResponse> readTemporaryDetailsInfoResponse = new ArrayList<>();

        for(TemporaryFilter temporaryFilter : temporaryFilters){
            List<String> representationImgCloudfrontUrl = representationImgUrlRepository
                    .findAllByTemporaryFilter(temporaryFilter)
                    .orElseGet(ArrayList::new)
                    .stream()
                    .map(RepresentationImgUrl::getCloudfrontUrl)
                    .collect(Collectors.toList());

            List<String> filterTagList = filterTagRepository
                    .findAllByTemporaryFilter(temporaryFilter)
                    .orElseGet(ArrayList::new)
                    .stream()
                    .map(filterTag -> filterTag.getTag().getStringId())
                    .collect(Collectors.toList());

            Attribute attribute = temporaryFilter.getAttribute();
            if (attribute == null) {
                attribute = Attribute.builder().build();
            }

            FilterDto.ReadTemporaryDetailsInfoResponse readTemporaryDetailsInfo = FilterDto.ReadTemporaryDetailsInfoResponse.builder()
                    .filterName(temporaryFilter.getName())
                    .description(temporaryFilter.getDescription())
                    .representationImgList(new FilterDto.RepresentationImgListResponse(representationImgCloudfrontUrl))
                    .filterTagList(new FilterDto.FilterTagListResponse(filterTagList))
                    .temporaryFilterId(temporaryFilter.getTemporaryFilterId())
                    .filterThumbnail(temporaryFilter.getThumbnailUrl() != null ? temporaryFilter.getThumbnailUrl().getCloudfrontUrl() : "")
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
                    .updatedAt(temporaryFilter.getUpdatedAt())
                    .build();

            readTemporaryDetailsInfoResponse.add(readTemporaryDetailsInfo);
        }

        return new PageImpl<>(readTemporaryDetailsInfoResponse, pageable, temporaryFilters.size());
    }
}
