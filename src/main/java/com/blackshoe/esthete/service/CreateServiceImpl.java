package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterCreateDto;
import com.blackshoe.esthete.entity.*;
import com.blackshoe.esthete.exception.FilterErrorResult;
import com.blackshoe.esthete.exception.FilterException;
import com.blackshoe.esthete.exception.UserErrorResult;
import com.blackshoe.esthete.exception.UserException;
import com.blackshoe.esthete.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class CreateServiceImpl implements CreateService{

    private final UserRepository userRepository;
    private final TemporaryFilterRepository temporaryFilterRepository;
    private final S3Client amazonS3Client;
    private final RepresentationImgUrlRepository representationImgUrlRepository;
    private final TagRepository tagRepository;
    private final AttributeRepository attributeRepository;
    private final ThumbnailUrlRepository thumbnailUrlRepository;
    private final FilterTagRepository filterTagRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String BUCKET;
    @Value("${spring.cloud.aws.cloudfront.distribution-domain}")
    private String DISTRIBUTION_DOMAIN;
    @Value("${spring.cloud.aws.s3.root-directory}")
    private String ROOT_DIRECTORY;
    @Value("${spring.cloud.aws.s3.thumbnail-directory}")
    private String THUMBNAIL_DIRECTORY;
    @Value("${spring.cloud.aws.s3.thumbnail-directory}")
    private String REPRESENTATION_IMG_DIRECTORY;


    @Override
    public FilterCreateDto.filterAttributeResponse saveFilterAttribute(UUID userId, FilterCreateDto.filterAttributeRequest requestDto){
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        TemporaryFilter tmpFilter = TemporaryFilter.builder().build();

        Attribute attribute = Attribute.builder()
                .brightness(requestDto.getBrightness())
                .sharpness(requestDto.getSharpness())
                .exposure(requestDto.getContrast())
                .contrast(requestDto.getContrast())
                .saturation(requestDto.getSaturation())
                .highlights(requestDto.getHighlights())
                .shadows(requestDto.getShadows())
                .build();

        tmpFilter.setAttribute(attribute);
        tmpFilter.updateUser(user);

        TemporaryFilter savedTmpFilter = temporaryFilterRepository.save(tmpFilter);

        return FilterCreateDto.filterAttributeResponse.builder()
                .tmpFilterId(savedTmpFilter.getTemporaryFilterId())
                .createdAt(savedTmpFilter.getCreatedAt())
                .build();

    }

    @Override
    @Transactional
    public FilterCreateDto.ThumbnailImgUrl uploadFilterThumbnail(MultipartFile thumbnailImg, UUID temporaryFilterId) {
        String s3FilePath = temporaryFilterId + "/" + THUMBNAIL_DIRECTORY;

        FilterCreateDto.ThumbnailImgUrl thumbnailImgUrlDto;

        if(thumbnailImg == null || thumbnailImg.isEmpty()) {
            thumbnailImgUrlDto = FilterCreateDto.ThumbnailImgUrl.builder()
                    .cloudfrontUrl("")
                    .s3Url("")
                    .build();

            return thumbnailImgUrlDto;
        }

        String fileExtension = thumbnailImg.getOriginalFilename().substring(thumbnailImg.getOriginalFilename().lastIndexOf("."));
        String key = ROOT_DIRECTORY + "/" + s3FilePath + "/" + UUID.randomUUID() + fileExtension;

        if (thumbnailImg.getSize() > 52428800) {
            throw new FilterException(FilterErrorResult.INVALID_THUMBNAIL_IMG_SIZE);
        }

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(key)
                    .build();

            amazonS3Client.putObject(putObjectRequest, RequestBody.fromInputStream(thumbnailImg.getInputStream(), thumbnailImg.getSize()));
        } catch (Exception e) {
            //log.error(e.getMessage());
            throw new FilterException(FilterErrorResult.THUMBNAIL_IMG_UPLOAD_FAILED);
        }

        String s3Url = "https://" + BUCKET + ".s3.amazonaws.com/" + key;
        String cloudFrontUrl = "https://" + DISTRIBUTION_DOMAIN + "/" + key;

        thumbnailImgUrlDto = FilterCreateDto.ThumbnailImgUrl.builder()
                .s3Url(s3Url)
                .cloudfrontUrl(cloudFrontUrl)
                .build();

        return thumbnailImgUrlDto;
    }

    @Override
    public FilterCreateDto.createTmpFilterResponse saveThumbnailImage(FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl, UUID temporaryFilterId){
        TemporaryFilter temporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

        ThumbnailUrl thumbnailUrl = ThumbnailUrl.builder()
                .s3Url(thumbnailImgUrl.getS3Url())
                .cloudfrontUrl(thumbnailImgUrl.getCloudfrontUrl())
                .build();

        temporaryFilter.setThumbnailUrl(thumbnailUrl);

        TemporaryFilter savedTmpFilter = temporaryFilterRepository.save(temporaryFilter);

        return FilterCreateDto.createTmpFilterResponse.builder()
                .createdAt(savedTmpFilter.getCreatedAt())
                .build();
    }

    @Override
    public List<FilterCreateDto.RepresentationImgUrl> uploadFilterRepresentativeImages(List<MultipartFile> representationImgs, UUID temporaryFilterId) {
        List<FilterCreateDto.RepresentationImgUrl> representationImgUrlDtos = new ArrayList<>();

        for(MultipartFile representationImg : representationImgs){
            String s3FilePath = temporaryFilterId + "/" + REPRESENTATION_IMG_DIRECTORY;

            FilterCreateDto.RepresentationImgUrl representationImgUrlDto;

            if(representationImg == null || representationImg.isEmpty()){
                representationImgUrlDto = FilterCreateDto.RepresentationImgUrl.builder()
                        .cloudfrontUrl("")
                        .s3Url("")
                        .build();

                representationImgUrlDtos.add(representationImgUrlDto);
                continue;
            }

            String fileExtension = representationImg.getOriginalFilename().substring(representationImg.getOriginalFilename().lastIndexOf("."));
            String key = ROOT_DIRECTORY + "/" + s3FilePath + "/" + UUID.randomUUID() + fileExtension;

            if(representationImg.getSize() > 52428800){
                throw new FilterException(FilterErrorResult.INVALID_THUMBNAIL_IMG_SIZE);
            }

            try {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(BUCKET)
                        .key(key)
                        .build();

                amazonS3Client.putObject(putObjectRequest, RequestBody.fromInputStream(representationImg.getInputStream(), representationImg.getSize()));
            } catch (Exception e) {
                //log.error(e.getMessage());
                throw new FilterException(FilterErrorResult.THUMBNAIL_IMG_UPLOAD_FAILED);
            }

            String s3Url = "https://" + BUCKET + ".s3.amazonaws.com/" + key;
            String cloudFrontUrl = "https://" + DISTRIBUTION_DOMAIN + "/" + key;

            representationImgUrlDto = FilterCreateDto.RepresentationImgUrl.builder()
                    .s3Url(s3Url)
                    .cloudfrontUrl(cloudFrontUrl)
                    .build();

            representationImgUrlDtos.add(representationImgUrlDto);
        }
        return representationImgUrlDtos;
    }

    @Override
    public FilterCreateDto.createTmpFilterResponse saveRepresentationImage(List<FilterCreateDto.RepresentationImgUrl> representationImgUrls, UUID temporaryFilterId){
        TemporaryFilter temporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

        for(FilterCreateDto.RepresentationImgUrl representationImgUrl : representationImgUrls){
            RepresentationImgUrl representationImgUrlEntity = RepresentationImgUrl.builder()
                    .cloudfrontUrl(representationImgUrl.getCloudfrontUrl())
                    .s3Url(representationImgUrl.getS3Url())
                    .build();

            representationImgUrlEntity.updateTemporaryFilter(temporaryFilter);
            representationImgUrlRepository.save(representationImgUrlEntity);
        }

        return FilterCreateDto.createTmpFilterResponse.builder()
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public FilterCreateDto.createTmpFilterResponse saveTempFilterInformation(UUID temporaryFilterId, FilterCreateDto.TmpFilterInformationRequest requestDto){
        TemporaryFilter temporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

        for(UUID tag : requestDto.getTagList().getTags()){
            FilterTag filterTag = FilterTag.builder().build();
            filterTag.updateTemporaryFilter(temporaryFilter);
            //tag레포에서 객체 찾는다.
            Tag savedtag = tagRepository.findByTagId(tag).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));
            //임시필터태그 테이블에 해당 태그 객체 연결
            filterTag.updateTag(savedtag);
        }
        temporaryFilter.updateTemporaryFilterInfo(requestDto.getName(), requestDto.getDescription());
        TemporaryFilter savedTemporaryFilter = temporaryFilterRepository.save(temporaryFilter);

        return FilterCreateDto.createTmpFilterResponse.builder()
                .createdAt(savedTemporaryFilter.getCreatedAt())
                .build();

    }

    @Override
    public FilterCreateDto.createTmpFilterResponse saveTempFilterToFilter(UUID temporaryFilterId, FilterCreateDto.TmpFilterInformationRequest requestDto){
        TemporaryFilter temporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

        for(UUID tag : requestDto.getTagList().getTags()){
            FilterTag filterTag = FilterTag.builder().build();
            filterTag.updateTemporaryFilter(temporaryFilter);
            //tag레포에서 객체 찾는다.
            Tag savedtag = tagRepository.findByTagId(tag).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));
            //임시필터태그 테이블에 해당 태그 객체 연결
            filterTag.updateTag(savedtag);
        }
        temporaryFilter.updateTemporaryFilterInfo(requestDto.getName(), requestDto.getDescription());
        TemporaryFilter savedTemporaryFilter = temporaryFilterRepository.save(temporaryFilter);

        User user = userRepository.findByUserId(savedTemporaryFilter.getUser().getUserId()).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        Filter filter = Filter.builder().build();

        filter.updateUser(user);

        Attribute attribute = attributeRepository.findByTemporaryFilter(savedTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_ATTRIBUTE));
        attribute.updateFilter(filter);

        ThumbnailUrl thumbnailUrl = thumbnailUrlRepository.findByTemporaryFilter(savedTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_THUMBNAIL_IMG_URL));
        thumbnailUrl.updateFilter(filter);

        List<RepresentationImgUrl> representationImgUrls = representationImgUrlRepository.findAllByTemporaryFilter(savedTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_REPRESENTATION_IMG_URL));

        for(RepresentationImgUrl representationImgUrl : representationImgUrls) {
            representationImgUrl.updateFilter(filter);
        }

        List<FilterTag> temporaryFilterTags = filterTagRepository.findAllByTemporaryFilter(savedTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER_TAG));

        for(FilterTag temporaryFilterTag : temporaryFilterTags) {
            temporaryFilterTag.updateFilter(filter);
        }

        temporaryFilterRepository.delete(savedTemporaryFilter);

        return FilterCreateDto.createTmpFilterResponse.builder()
                .createdAt(LocalDateTime.now())
                .build();

    }

}
