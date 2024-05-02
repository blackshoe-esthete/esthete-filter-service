package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterCreateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CreateService {
//    FilterCreateDto.filterAttributeResponse saveFilterAttribute(UUID userID, FilterCreateDto.filterAttributeRequest requestDto);
//
//    FilterCreateDto.ThumbnailImgUrl uploadFilterThumbnail(MultipartFile thumbnailImg, UUID temporaryFilterId);
//
//    FilterCreateDto.createTmpFilterResponse saveThumbnailImage(FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl, UUID temporaryFilterId);
//
//    List<FilterCreateDto.RepresentationImgUrl> uploadFilterRepresentativeImages(List<MultipartFile> representativeImages, UUID temporaryFilterId);
//
//    FilterCreateDto.createTmpFilterResponse saveRepresentationImage(List<FilterCreateDto.RepresentationImgUrl> representationImgUrls, UUID temporaryFilterId);
//
//    FilterCreateDto.createTmpFilterResponse saveTempFilterInformation(UUID temporaryFilterId, FilterCreateDto.TmpFilterInformationRequest requestDto);
//
//    FilterCreateDto.createTmpFilterResponse saveTempFilterToFilter(UUID temporaryFilterId, FilterCreateDto.TmpFilterInformationRequest requestDto);

    FilterCreateDto.TmpFilterResponse saveTemporaryFilter(UUID userId, MultipartFile thumbnailImg, List<MultipartFile> representationImgs, FilterCreateDto.CreateTmpFilterRequest requestDto);

    FilterCreateDto.TmpFilterResponse saveFilter(UUID userId, MultipartFile thumbnail, List<MultipartFile> representationImg, FilterCreateDto.CreateTmpFilterRequest requestDto);
}
