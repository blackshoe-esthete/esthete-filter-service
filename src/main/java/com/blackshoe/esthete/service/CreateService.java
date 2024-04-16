package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterCreateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CreateService {
    FilterCreateDto.filterAttributeResponse saveFilterAttribute(UUID userID, FilterCreateDto.filterAttributeRequest requestDto);

    FilterCreateDto.ThumbnailImgUrl uploadFilterThumbnail(MultipartFile thumbnailImg, UUID temporaryFilterId);

    FilterCreateDto.createTmpFilterResponse saveThumbnailImage(FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl, UUID temporaryFilterId);

}
