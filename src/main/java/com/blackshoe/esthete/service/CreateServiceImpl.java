package com.blackshoe.esthete.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.blackshoe.esthete.dto.FilterCreateDto;
import com.blackshoe.esthete.entity.*;
import com.blackshoe.esthete.exception.FilterErrorResult;
import com.blackshoe.esthete.exception.FilterException;
import com.blackshoe.esthete.exception.UserErrorResult;
import com.blackshoe.esthete.exception.UserException;
import com.blackshoe.esthete.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@Slf4j
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
    private final FilterRepository filterRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String BUCKET;
    @Value("${spring.cloud.aws.cloudfront.distribution-domain}")
    private String DISTRIBUTION_DOMAIN;
    @Value("${spring.cloud.aws.s3.root-directory}")
    private String ROOT_DIRECTORY;
    @Value("${spring.cloud.aws.s3.thumbnail-directory}")
    private String THUMBNAIL_DIRECTORY;
    @Value("${spring.cloud.aws.s3.representation-directory}")
    private String REPRESENTATION_IMG_DIRECTORY;


//    @Override
//    public FilterCreateDto.filterAttributeResponse saveFilterAttribute(UUID userId, FilterCreateDto.filterAttributeRequest requestDto){
//        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));
//
//        TemporaryFilter tmpFilter = TemporaryFilter.builder().build();
//
//        Attribute attribute = Attribute.builder()
//                .brightness(requestDto.getBrightness())
//                .sharpness(requestDto.getSharpness())
//                .exposure(requestDto.getExposure())
//                .contrast(requestDto.getContrast())
//                .saturation(requestDto.getSaturation())
//                .highlights(requestDto.getHighlights())
//                .shadows(requestDto.getShadows())
//                .build();
//
//        attribute.updateTemporaryFilter(tmpFilter);
//        tmpFilter.updateUser(user);
//
//        TemporaryFilter savedTmpFilter = temporaryFilterRepository.save(tmpFilter);
//
//        return FilterCreateDto.filterAttributeResponse.builder()
//                .tmpFilterId(savedTmpFilter.getTemporaryFilterId())
//                .createdAt(savedTmpFilter.getCreatedAt())
//                .build();
//
//    }
//
//    @Override
//    @Transactional // rootdirect -> 임시저장UUIDdirect -> thumbnail -> 파일
//    public FilterCreateDto.ThumbnailImgUrl uploadFilterThumbnail(MultipartFile thumbnailImg, UUID temporaryFilterId) {
//        String s3FilePath = temporaryFilterId + "/" + THUMBNAIL_DIRECTORY;
//
//        FilterCreateDto.ThumbnailImgUrl thumbnailImgUrlDto;
//
//        if(thumbnailImg == null || thumbnailImg.isEmpty()) {
//            thumbnailImgUrlDto = FilterCreateDto.ThumbnailImgUrl.builder()
//                    .cloudfrontUrl("")
//                    .s3Url("")
//                    .build();
//
//            return thumbnailImgUrlDto;
//        }
//
//        String fileExtension = thumbnailImg.getOriginalFilename().substring(thumbnailImg.getOriginalFilename().lastIndexOf("."));
//        String key = ROOT_DIRECTORY + "/" + s3FilePath + "/" + UUID.randomUUID() + fileExtension;
//
//        if (thumbnailImg.getSize() > 52428800) {
//            throw new FilterException(FilterErrorResult.INVALID_THUMBNAIL_IMG_SIZE);
//        }
//
//        try {
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(BUCKET)
//                    .key(key)
//                    .contentType(thumbnailImg.getContentType())
//                    .build();
//            amazonS3Client.putObject(putObjectRequest, RequestBody.fromInputStream(thumbnailImg.getInputStream(), thumbnailImg.getSize()));
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new FilterException(FilterErrorResult.THUMBNAIL_IMG_UPLOAD_FAILED);
//        }
//
//
//        String s3Url = "https://" + BUCKET + ".s3.amazonaws.com/" + key;
//        String cloudFrontUrl = "https://" + DISTRIBUTION_DOMAIN + "/" + key;
//
//        thumbnailImgUrlDto = FilterCreateDto.ThumbnailImgUrl.builder()
//                .s3Url(s3Url)
//                .cloudfrontUrl(cloudFrontUrl)
//                .build();
//
//        return thumbnailImgUrlDto;
//    }
//
//    @Override
//    public FilterCreateDto.createTmpFilterResponse saveThumbnailImage(FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl, UUID temporaryFilterId){
//        TemporaryFilter temporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));
//
//        ThumbnailUrl thumbnailUrl = ThumbnailUrl.builder()
//                .s3Url(thumbnailImgUrl.getS3Url())
//                .cloudfrontUrl(thumbnailImgUrl.getCloudfrontUrl())
//                .build();
//
//        thumbnailUrl.updateTemporaryFilter(temporaryFilter);
//
//        TemporaryFilter savedTmpFilter = temporaryFilterRepository.save(temporaryFilter);
//
//        return FilterCreateDto.createTmpFilterResponse.builder()
//                .createdAt(savedTmpFilter.getCreatedAt())
//                .build();
//    }
//
//    @Override // rootdirect -> 임시저장UUIDdirect -> thumbnail -> 파일
//    public List<FilterCreateDto.RepresentationImgUrl> uploadFilterRepresentativeImages(List<MultipartFile> representationImgs, UUID temporaryFilterId) {
//        List<FilterCreateDto.RepresentationImgUrl> representationImgUrlDtos = new ArrayList<>();
//
//        for(MultipartFile representationImg : representationImgs){
//            String s3FilePath = temporaryFilterId + "/" + REPRESENTATION_IMG_DIRECTORY;
//
//            FilterCreateDto.RepresentationImgUrl representationImgUrlDto;
//
//            if(representationImg == null || representationImg.isEmpty()){
//                representationImgUrlDto = FilterCreateDto.RepresentationImgUrl.builder()
//                        .cloudfrontUrl("")
//                        .s3Url("")
//                        .build();
//
//                representationImgUrlDtos.add(representationImgUrlDto);
//                continue;
//            }
//
//            String fileExtension = representationImg.getOriginalFilename().substring(representationImg.getOriginalFilename().lastIndexOf("."));
//            String key = ROOT_DIRECTORY + "/" + s3FilePath + "/" + UUID.randomUUID() + fileExtension;
//
//            if(representationImg.getSize() > 52428800){
//                throw new FilterException(FilterErrorResult.INVALID_THUMBNAIL_IMG_SIZE);
//            }
//
//            try {
//                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                        .bucket(BUCKET)
//                        .key(key)
//                        .contentType(representationImg.getContentType())
//                        .build();
//
//                amazonS3Client.putObject(putObjectRequest, RequestBody.fromInputStream(representationImg.getInputStream(), representationImg.getSize()));
//            } catch (Exception e) {
//                //log.error(e.getMessage());
//                throw new FilterException(FilterErrorResult.THUMBNAIL_IMG_UPLOAD_FAILED);
//            }
//
//            String s3Url = "https://" + BUCKET + ".s3.amazonaws.com/" + key;
//            String cloudFrontUrl = "https://" + DISTRIBUTION_DOMAIN + "/" + key;
//
//            representationImgUrlDto = FilterCreateDto.RepresentationImgUrl.builder()
//                    .s3Url(s3Url)
//                    .cloudfrontUrl(cloudFrontUrl)
//                    .build();
//
//            representationImgUrlDtos.add(representationImgUrlDto);
//        }
//        return representationImgUrlDtos;
//    }
//
//    @Override
//    public FilterCreateDto.createTmpFilterResponse saveRepresentationImage(List<FilterCreateDto.RepresentationImgUrl> representationImgUrls, UUID temporaryFilterId){
//        TemporaryFilter temporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));
//
//        for(FilterCreateDto.RepresentationImgUrl representationImgUrl : representationImgUrls){
//            RepresentationImgUrl representationImgUrlEntity = RepresentationImgUrl.builder()
//                    .cloudfrontUrl(representationImgUrl.getCloudfrontUrl())
//                    .s3Url(representationImgUrl.getS3Url())
//                    .build();
//
//            representationImgUrlEntity.updateTemporaryFilter(temporaryFilter);
//            representationImgUrlRepository.save(representationImgUrlEntity);
//        }
//
//        return FilterCreateDto.createTmpFilterResponse.builder()
//                .createdAt(LocalDateTime.now())
//                .build();
//    }
//
//    @Override // 똑같은 버튼 눌렀을때 중복처리되는거 예외처리하기
//    public FilterCreateDto.createTmpFilterResponse saveTempFilterInformation(UUID temporaryFilterId, FilterCreateDto.TmpFilterInformationRequest requestDto){
//        TemporaryFilter temporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));
//
//        for(UUID tag : requestDto.getTagList().getTags()){
//            log.info("tag : " + tag);
//            FilterTag filterTag = FilterTag.builder().build();
//            filterTag.updateTemporaryFilter(temporaryFilter);
//            //tag레포에서 객체 찾는다.
//            Tag savedtag = tagRepository.findByTagId(tag).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));
//            //임시필터태그 테이블에 해당 태그 객체 연결
//            filterTag.updateTag(savedtag);
//        }
//        temporaryFilter.updateTemporaryFilterInfo(requestDto.getName(), requestDto.getDescription());
//        TemporaryFilter savedTemporaryFilter = temporaryFilterRepository.save(temporaryFilter);
//
//        return FilterCreateDto.createTmpFilterResponse.builder()
//                .createdAt(savedTemporaryFilter.getCreatedAt())
//                .build();
//
//    }
//
//    @Override
//    @Transactional
//    public FilterCreateDto.createTmpFilterResponse saveTempFilterToFilter(UUID temporaryFilterId, FilterCreateDto.TmpFilterInformationRequest requestDto){
//        TemporaryFilter temporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));
//
//        for(UUID tag : requestDto.getTagList().getTags()){
//            log.info("tag : " + tag);
//            FilterTag filterTag = FilterTag.builder().build();
//            filterTag.updateTemporaryFilter(temporaryFilter);
//            Tag savedtag = tagRepository.findByTagId(tag).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));
//            filterTag.updateTag(savedtag);
//        }
//        temporaryFilter.updateTemporaryFilterInfo(requestDto.getName(), requestDto.getDescription());
//        TemporaryFilter savedTemporaryFilter = temporaryFilterRepository.save(temporaryFilter);
//
//        User user = userRepository.findByUserId(savedTemporaryFilter.getUser().getUserId()).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));
//
//        Filter filter = Filter.builder().build();
//
//        filter.updateUser(user);
//
//        Attribute attribute = attributeRepository.findByTemporaryFilter(savedTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_ATTRIBUTE));
//        attribute.updateFilter(filter);
//
//        ThumbnailUrl thumbnailUrl = thumbnailUrlRepository.findByTemporaryFilter(savedTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_THUMBNAIL_IMG_URL));
//        thumbnailUrl.updateFilter(filter);
//
//        List<RepresentationImgUrl> representationImgUrls = representationImgUrlRepository.findAllByTemporaryFilter(savedTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_REPRESENTATION_IMG_URL));
//
//        for(RepresentationImgUrl representationImgUrl : representationImgUrls) {
//            representationImgUrl.updateFilter(filter);
//        }
//
//        List<FilterTag> temporaryFilterTags = filterTagRepository.findAllByTemporaryFilter(savedTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER_TAG));
//
//        for(FilterTag temporaryFilterTag : temporaryFilterTags) {
//            temporaryFilterTag.updateFilter(filter);
//        }
//
//        Filter savedFilter = filterRepository.save(filter);
//
//        //임시저장 필터 자식들과 연관관계 끊기 -> 데이터 삭제
//        for(FilterTag temporaryFilterTag : temporaryFilterTags) {
//            temporaryFilterTag.deleteTemporaryFilter(savedTemporaryFilter);
//        }
//
//        for(RepresentationImgUrl representationImgUrl : representationImgUrls) {
//            representationImgUrl.deleteTemporaryFilter(savedTemporaryFilter);
//        }
//
//        thumbnailUrl.deleteTemporaryFilter(savedTemporaryFilter);
//
//        attribute.deleteTemporaryFilter(savedTemporaryFilter);
//
//        savedTemporaryFilter.deleteUser(user);
//
//        temporaryFilterRepository.save(savedTemporaryFilter);
//
//        return FilterCreateDto.createTmpFilterResponse.builder()
//                .createdAt(savedFilter.getCreatedAt())
//                .build();
//
//    }
    @Override
    @Transactional // 임시저장
    public FilterCreateDto.TmpFilterResponse saveTemporaryFilter(UUID userId, MultipartFile thumbnailImg, List<MultipartFile> representationImgs, FilterCreateDto.CreateFilterRequest requestDto){
        Optional<TemporaryFilter> findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(requestDto.getTmpFilterId());

        if(findTemporaryFilter.isPresent()){ // 여러번 임시저장 -> 업데이트
            //속성 설정
            saveFilterAttribute(findTemporaryFilter.get().getTemporaryFilterId(), requestDto);

            //썸네일 설정
            FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl = uploadFilterThumbnail(thumbnailImg, findTemporaryFilter.get().getTemporaryFilterId());
            saveThumbnailImage(thumbnailImgUrl, findTemporaryFilter.get().getTemporaryFilterId());

            //대표사진 설정
            List<FilterCreateDto.RepresentationImgUrl> representationImgUrls = uploadFilterRepresentativeImages(representationImgs, findTemporaryFilter.get().getTemporaryFilterId());
            saveRepresentationImage(representationImgUrls, findTemporaryFilter.get().getTemporaryFilterId());

            //필터 정보 저장
            saveTempFilterInformation(findTemporaryFilter.get().getTemporaryFilterId(), requestDto);


            return FilterCreateDto.TmpFilterResponse.builder()
                    .tmpFilterId(findTemporaryFilter.get().getTemporaryFilterId())
                    .createdAt(findTemporaryFilter.get().getCreatedAt())
                    .build();
        }
        else{ // 첫 임시저장
            User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));
            TemporaryFilter temporaryFilter = TemporaryFilter.builder().build();
            temporaryFilter.updateUser(user);

            //속성 설정
            saveFilterAttribute(temporaryFilter.getTemporaryFilterId(), requestDto);

            //썸네일 설정
            FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl = uploadFilterThumbnail(thumbnailImg, temporaryFilter.getTemporaryFilterId());
            saveThumbnailImage(thumbnailImgUrl, temporaryFilter.getTemporaryFilterId());

            //대표사진 설정
            List<FilterCreateDto.RepresentationImgUrl> representationImgUrls = uploadFilterRepresentativeImages(representationImgs, temporaryFilter.getTemporaryFilterId());
            saveRepresentationImage(representationImgUrls, temporaryFilter.getTemporaryFilterId());

            //필터 정보 저장
            saveTempFilterInformation(temporaryFilter.getTemporaryFilterId(), requestDto);


            return FilterCreateDto.TmpFilterResponse.builder()
                    .tmpFilterId(temporaryFilter.getTemporaryFilterId())
                    .createdAt(temporaryFilter.getCreatedAt())
                    .build();
        }


    }

    @Override
    @Transactional
    public FilterCreateDto.CreateFilterResponse saveFilter(UUID userId, MultipartFile thumbnailImg, List<MultipartFile> representationImgs, FilterCreateDto.CreateFilterRequest requestDto){
        Optional<TemporaryFilter> findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(requestDto.getTmpFilterId());
        if(findTemporaryFilter.isPresent()){ // 임시저장 테이블에 해당 필터가 있는 경우 -> 테이블 업데이트 후 삭제하고 필터 테이블로 옮김
            //내용 업데이트 기능
            //속성 설정
            saveFilterAttribute(findTemporaryFilter.get().getTemporaryFilterId(), requestDto);

            //썸네일 설정
            FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl = uploadFilterThumbnail(thumbnailImg, findTemporaryFilter.get().getTemporaryFilterId());
            saveThumbnailImage(thumbnailImgUrl, findTemporaryFilter.get().getTemporaryFilterId());

            //대표사진 설정
            List<FilterCreateDto.RepresentationImgUrl> representationImgUrls = uploadFilterRepresentativeImages(representationImgs, findTemporaryFilter.get().getTemporaryFilterId());
            saveRepresentationImage(representationImgUrls, findTemporaryFilter.get().getTemporaryFilterId());

            //필터 정보 저장
            saveTempFilterInformation(findTemporaryFilter.get().getTemporaryFilterId(), requestDto);

            //삭제 후 필터 테이블로 이전
            Filter filter = transferTempFilterToFilter(findTemporaryFilter.get());

            return FilterCreateDto.CreateFilterResponse.builder()
                    .filterId(filter.getFilterId())
                    .createdAt(filter.getCreatedAt())
                    .build();

        }else{ // 임시저장을 거치치 않고 바로 필터 저장하는 경우 -> 바로 필터 테이블로 저장
            User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

            Filter filter = Filter.builder().build();
            filter.updateUser(user);


        }
    }



    public void saveFilterAttribute(UUID temporaryFilterId, FilterCreateDto.CreateFilterRequest requestDto){
        TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

        //속성 설정
        Attribute attribute = Attribute.builder()
                .brightness(requestDto.getFilterAttribute().getBrightness())
                .sharpness(requestDto.getFilterAttribute().getSharpness())
                .exposure(requestDto.getFilterAttribute().getExposure())
                .contrast(requestDto.getFilterAttribute().getContrast())
                .saturation(requestDto.getFilterAttribute().getSaturation())
                .highlights(requestDto.getFilterAttribute().getHighlights())
                .shadows(requestDto.getFilterAttribute().getShadows())
                .build();

        attribute.updateTemporaryFilter(findTemporaryFilter);
    }

    // rootdirect -> 임시저장UUIDdirect -> thumbnail -> 파일
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
                    .contentType(thumbnailImg.getContentType())
                    .build();
            amazonS3Client.putObject(putObjectRequest, RequestBody.fromInputStream(thumbnailImg.getInputStream(), thumbnailImg.getSize()));
        } catch (Exception e) {
            log.error(e.getMessage());
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


    public void saveThumbnailImage(FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl, UUID temporaryFilterId){
        TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

        ThumbnailUrl thumbnailUrl = ThumbnailUrl.builder()
                .s3Url(thumbnailImgUrl.getS3Url())
                .cloudfrontUrl(thumbnailImgUrl.getCloudfrontUrl())
                .build();

        thumbnailUrl.updateTemporaryFilter(findTemporaryFilter);
        temporaryFilterRepository.save(findTemporaryFilter);
    }

    // rootdirect -> 임시저장UUIDdirect -> thumbnail -> 파일
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
                        .contentType(representationImg.getContentType())
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

    public void saveRepresentationImage(List<FilterCreateDto.RepresentationImgUrl> representationImgUrls, UUID temporaryFilterId){
        TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

        for(FilterCreateDto.RepresentationImgUrl representationImgUrl : representationImgUrls){
            RepresentationImgUrl representationImgUrlEntity = RepresentationImgUrl.builder()
                    .cloudfrontUrl(representationImgUrl.getCloudfrontUrl())
                    .s3Url(representationImgUrl.getS3Url())
                    .build();

            representationImgUrlEntity.updateTemporaryFilter(findTemporaryFilter);
            representationImgUrlRepository.save(representationImgUrlEntity);
        }
    }

    // 똑같은 버튼 눌렀을때 중복처리되는거 예외처리하기
    public void saveTempFilterInformation(UUID temporaryFilterId, FilterCreateDto.CreateFilterRequest requestDto){
        TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

        for(UUID tag : requestDto.getFilterInformation().getTagList().getTags()){
            log.info("tag : " + tag);
            FilterTag filterTag = FilterTag.builder().build();
            filterTag.updateTemporaryFilter(findTemporaryFilter);

            Tag savedtag = tagRepository.findByTagId(tag).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));

            filterTag.updateTag(savedtag);
        }
        findTemporaryFilter.updateTemporaryFilterInfo(requestDto.getFilterInformation().getName(), requestDto.getFilterInformation().getDescription());
        temporaryFilterRepository.save(findTemporaryFilter);

    }


    public Filter transferTempFilterToFilter(TemporaryFilter temporaryFilter){
        //tmpfilter의 값들을 filter에 연결
        User user = userRepository.findByUserId(temporaryFilter.getUser().getUserId()).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        Filter filter = Filter.builder().build();

        filter.updateUser(user);

        Attribute attribute = attributeRepository.findByTemporaryFilter(temporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_ATTRIBUTE));
        attribute.updateFilter(filter);

        ThumbnailUrl thumbnailUrl = thumbnailUrlRepository.findByTemporaryFilter(temporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_THUMBNAIL_IMG_URL));
        thumbnailUrl.updateFilter(filter);

        List<RepresentationImgUrl> representationImgUrls = representationImgUrlRepository.findAllByTemporaryFilter(temporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_REPRESENTATION_IMG_URL));

        for(RepresentationImgUrl representationImgUrl : representationImgUrls) {
            representationImgUrl.updateFilter(filter);
        }

        List<FilterTag> temporaryFilterTags = filterTagRepository.findAllByTemporaryFilter(temporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER_TAG));

        for(FilterTag temporaryFilterTag : temporaryFilterTags) {
            temporaryFilterTag.updateFilter(filter);
        }

        filterRepository.save(filter);

        //임시저장 필터 자식들과 연관관계 끊기 -> 데이터 삭제
        for(FilterTag temporaryFilterTag : temporaryFilterTags) {
            temporaryFilterTag.deleteTemporaryFilter(temporaryFilter);
        }

        for(RepresentationImgUrl representationImgUrl : representationImgUrls) {
            representationImgUrl.deleteTemporaryFilter(temporaryFilter);
        }

        thumbnailUrl.deleteTemporaryFilter(temporaryFilter);

        attribute.deleteTemporaryFilter(temporaryFilter);

        temporaryFilter.deleteUser(user);

        temporaryFilterRepository.save(temporaryFilter);

        return filter;

    }

}
