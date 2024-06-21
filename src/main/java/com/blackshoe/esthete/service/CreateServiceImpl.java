package com.blackshoe.esthete.service;

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
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.*;
import java.util.List;

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

    @Override
    @Transactional // 임시저장
    public FilterCreateDto.TmpFilterResponse saveTemporaryFilter(UUID userId, MultipartFile thumbnailImg, List<MultipartFile> representationImgs, FilterCreateDto.CreateFilterRequest requestDto){

        if(requestDto.getTmpFilterId() != null){ // 여러번 임시저장 -> 업데이트
            log.info("여러번 임시저장");
            TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(requestDto.getTmpFilterId()).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));
            //속성 설정
            saveFilterAttribute(findTemporaryFilter.getTemporaryFilterId(), requestDto, null);


            //썸네일 설정
            FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl = uploadFilterThumbnail(thumbnailImg, findTemporaryFilter.getTemporaryFilterId(), null);
            saveThumbnailImage(thumbnailImgUrl, findTemporaryFilter.getTemporaryFilterId(), null);

            //대표사진 설정
            List<FilterCreateDto.RepresentationImgUrl> representationImgUrls = uploadFilterRepresentativeImages(representationImgs, findTemporaryFilter.getTemporaryFilterId(), null);
            saveRepresentationImage(representationImgUrls, findTemporaryFilter.getTemporaryFilterId(), null);

            //필터 정보 저장
            saveTempFilterInformation(findTemporaryFilter.getTemporaryFilterId(), requestDto, null);


            return FilterCreateDto.TmpFilterResponse.builder()
                    .tmpFilterId(findTemporaryFilter.getTemporaryFilterId())
                    .createdAt(findTemporaryFilter.getCreatedAt())
                    .build();
        }
        else{ // 첫 임시저장
            log.info("첫 임시저장");
            User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));
            TemporaryFilter temporaryFilter = TemporaryFilter.builder().build();
            temporaryFilter.updateUser(user);
            temporaryFilterRepository.save(temporaryFilter); // @PrePersist로 인한 널값 방지


            //속성 설정
            saveFilterAttribute(temporaryFilter.getTemporaryFilterId(), requestDto, null);
            log.info("첫 임시저장 속성 설정 완료 : " + String.valueOf(temporaryFilter.getTemporaryFilterId()));

            //썸네일 설정
            FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl = uploadFilterThumbnail(thumbnailImg, temporaryFilter.getTemporaryFilterId(), null);
            saveThumbnailImage(thumbnailImgUrl, temporaryFilter.getTemporaryFilterId(), null);
            log.info("첫 임시저장 썸네일 설정 완료" + String.valueOf(thumbnailImgUrl.getCloudfrontUrl()));

            //대표사진 설정
            List<FilterCreateDto.RepresentationImgUrl> representationImgUrls = uploadFilterRepresentativeImages(representationImgs, temporaryFilter.getTemporaryFilterId(), null);
            saveRepresentationImage(representationImgUrls, temporaryFilter.getTemporaryFilterId(), null);
            log.info("첫 임시저장 대표사진 설정 완료");

            //필터 정보 저장
            saveTempFilterInformation(temporaryFilter.getTemporaryFilterId(), requestDto, null);
            log.info("첫 임시저장 필터정보 등록 완료");


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
            log.info("임시저장 이력이 있는 저장");
            //내용 업데이트 기능
            //속성 설정
            saveFilterAttribute(findTemporaryFilter.get().getTemporaryFilterId(), requestDto, null);

            //썸네일 설정
            FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl = uploadFilterThumbnail(thumbnailImg, findTemporaryFilter.get().getTemporaryFilterId(), null);
            saveThumbnailImage(thumbnailImgUrl, findTemporaryFilter.get().getTemporaryFilterId(), null);

            //대표사진 설정
            List<FilterCreateDto.RepresentationImgUrl> representationImgUrls = uploadFilterRepresentativeImages(representationImgs, findTemporaryFilter.get().getTemporaryFilterId(), null);
            saveRepresentationImage(representationImgUrls, findTemporaryFilter.get().getTemporaryFilterId(), null);

            //필터 정보 저장
            saveTempFilterInformation(findTemporaryFilter.get().getTemporaryFilterId(), requestDto, null);

            //삭제 후 필터 테이블로 이전 -> 필터 객체 생성
            Filter filter = transferTempFilterToFilter(findTemporaryFilter.get());

            return FilterCreateDto.CreateFilterResponse.builder()
                    .filterId(filter.getFilterId())
                    .createdAt(filter.getCreatedAt())
                    .build();

        }else{ // 임시저장을 거치치 않고 바로 필터 저장하는 경우 -> 바로 필터 테이블로 저장
            log.info("바로 필터 저장");
            User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

            Filter filter = Filter.builder().build();
            filter.updateUser(user);
            filterRepository.save(filter);

            saveFilterAttribute(null, requestDto, filter.getFilterId());

            FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl = uploadFilterThumbnail(thumbnailImg, null, filter.getFilterId());
            saveThumbnailImage(thumbnailImgUrl, null, filter.getFilterId());

            List<FilterCreateDto.RepresentationImgUrl> representationImgUrls = uploadFilterRepresentativeImages(representationImgs, null, filter.getFilterId());
            saveRepresentationImage(representationImgUrls, null, filter.getFilterId());

            saveTempFilterInformation(null, requestDto, filter.getFilterId());

            return FilterCreateDto.CreateFilterResponse.builder()
                    .filterId(filter.getFilterId())
                    .createdAt(filter.getCreatedAt())
                    .build();

        }
    }



    public void saveFilterAttribute(UUID temporaryFilterId, FilterCreateDto.CreateFilterRequest requestDto, UUID filterId){
        if(temporaryFilterId != null && filterId == null){ // 임시저장테이블에 이미 연결되어 있는 경우, 업데이트 진행
            TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

            if(attributeRepository.existsByTemporaryFilter(findTemporaryFilter)){ // 두번째 임시저장 경우, 업데이트 진행
                log.info("두번째 임시저장 경우, 속성 업데이트");
                Attribute findAttribute = attributeRepository.findByTemporaryFilter(findTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_ATTRIBUTE));
                //속성 업데이트
                findAttribute.changeAttribute(requestDto.getFilterAttribute().getBrightness(),
                        requestDto.getFilterAttribute().getSharpness(),
                        requestDto.getFilterAttribute().getExposure(),
                        requestDto.getFilterAttribute().getContrast(),
                        requestDto.getFilterAttribute().getSaturation(),
                        requestDto.getFilterAttribute().getHue(),
                        requestDto.getFilterAttribute().getTemperature(),
                        requestDto.getGrayScale());

                attributeRepository.save(findAttribute);
            }
            else{//첫 임시저장
                log.info("첫 임시저장 경우, 속성 생성");
                Attribute attribute = Attribute.builder()
                        .brightness(requestDto.getFilterAttribute().getBrightness())
                        .sharpness(requestDto.getFilterAttribute().getSharpness())
                        .exposure(requestDto.getFilterAttribute().getExposure())
                        .contrast(requestDto.getFilterAttribute().getContrast())
                        .saturation(requestDto.getFilterAttribute().getSaturation())
                        .hue(requestDto.getFilterAttribute().getHue())
                        .temperature(requestDto.getFilterAttribute().getTemperature())
                        .grayScale(requestDto.getGrayScale())
                        .build();
                attribute.updateTemporaryFilter(findTemporaryFilter);
                attributeRepository.save(attribute);
            }
        }
        else if(filterId != null && temporaryFilterId == null){
            Filter findFilter = filterRepository.findByFilterId(filterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));
            log.info("바로 저장 경우, 속성 생성");
            Attribute attribute = Attribute.builder()
                    .brightness(requestDto.getFilterAttribute().getBrightness())
                    .sharpness(requestDto.getFilterAttribute().getSharpness())
                    .exposure(requestDto.getFilterAttribute().getExposure())
                    .contrast(requestDto.getFilterAttribute().getContrast())
                    .saturation(requestDto.getFilterAttribute().getSaturation())
                    .hue(requestDto.getFilterAttribute().getHue())
                    .temperature(requestDto.getFilterAttribute().getTemperature())
                    .grayScale(requestDto.getGrayScale())
                    .build();

            attribute.updateFilter(findFilter);
            attributeRepository.save(attribute);
        }

    }

    // rootdirect -> 임시저장UUIDdirect -> thumbnail -> 파일
    public FilterCreateDto.ThumbnailImgUrl uploadFilterThumbnail(MultipartFile thumbnailImg, UUID temporaryFilterId, UUID filterId) {

        if(temporaryFilterId != null && filterId == null){
            TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));
            if(thumbnailUrlRepository.existsByTemporaryFilter(findTemporaryFilter)){ // 기존 임시저장테이블에 썸네일이 존재하면
                log.info("두번째 임시저장 경우, 썸네일 s3 데이터 지우기");
                //s3에 데이터 지우기
                ThumbnailUrl findThumbnailUrl = thumbnailUrlRepository.findByTemporaryFilter(findTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_THUMBNAIL_IMG_URL));
                String thumbnails3Url = findThumbnailUrl.getS3Url();

                String key = thumbnails3Url.substring(thumbnails3Url.indexOf(ROOT_DIRECTORY));

                try {
                    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(key)
                            .build();
                    amazonS3Client.deleteObject(deleteObjectRequest);
                    log.info("썸네일 객체 삭제중");

                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new FilterException(FilterErrorResult.THUMBNAIL_IMG_DELETE_FAILED);
                }
            }
        }

        String s3FilePath; // 기존에 파일 올라간게 있으면 지우는 과정까지..
        if(temporaryFilterId != null && filterId == null){
            s3FilePath = temporaryFilterId + "/" + THUMBNAIL_DIRECTORY;
        }else if(filterId != null && temporaryFilterId == null){
            s3FilePath = filterId + "/" + THUMBNAIL_DIRECTORY;
        }else{
            s3FilePath = null; //예외 던지기
        }

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


    public void saveThumbnailImage(FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl, UUID temporaryFilterId, UUID filterId){
        if(temporaryFilterId != null && filterId == null){

            TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

            if(thumbnailUrlRepository.existsByTemporaryFilter(findTemporaryFilter)){ // 기존 임시저장테이블에 존재하면 update
                ThumbnailUrl thumbnailUrl = thumbnailUrlRepository.findByTemporaryFilter(findTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));
                thumbnailUrl.updateThumbnailUrl(thumbnailImgUrl.getCloudfrontUrl(), thumbnailImgUrl.getS3Url());
                thumbnailUrlRepository.save(thumbnailUrl);
                log.info("두번째 임시저장 경우, 썸네일 url 업데이트");
            }
            else{ // 첫번째 임시저장이면
                ThumbnailUrl thumbnailUrl = ThumbnailUrl.builder()
                        .s3Url(thumbnailImgUrl.getS3Url())
                        .cloudfrontUrl(thumbnailImgUrl.getCloudfrontUrl())
                        .build();

                thumbnailUrl.updateTemporaryFilter(findTemporaryFilter);
//                temporaryFilterRepository.save(findTemporaryFilter);
                thumbnailUrlRepository.save(thumbnailUrl);
                log.info("첫 임시저장 경우, 썸네일 url 생성");
            }

        } else if (filterId != null && temporaryFilterId == null) { // 바로 필터 등록인 경우
            Filter findFilter = filterRepository.findByFilterId(filterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));
            log.info("바로 저장 경우, 썸네일 url 생성");
            ThumbnailUrl thumbnailUrl = ThumbnailUrl.builder()
                    .s3Url(thumbnailImgUrl.getS3Url())
                    .cloudfrontUrl(thumbnailImgUrl.getCloudfrontUrl())
                    .build();

            thumbnailUrl.updateFilter(findFilter);
            filterRepository.save(findFilter);
        }

    }

    // rootdirect -> 임시저장UUIDdirect -> thumbnail -> 파일
    public List<FilterCreateDto.RepresentationImgUrl> uploadFilterRepresentativeImages(List<MultipartFile> representationImgs, UUID temporaryFilterId, UUID filterId) {
        if(temporaryFilterId != null && filterId == null){
            TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

            if(representationImgUrlRepository.existsAllByTemporaryFilter(findTemporaryFilter)){
                List<RepresentationImgUrl> findAllRepresentationImgUrl = representationImgUrlRepository.findAllByTemporaryFilter(findTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_REPRESENTATION_IMG_URL));
                log.info("두번째 임시저장 경우, 대표사진 s3 삭제");

                for(RepresentationImgUrl representationImgUrl : findAllRepresentationImgUrl){
                    String representationImgS3Url = representationImgUrl.getS3Url();

                    String key = representationImgS3Url.substring(representationImgS3Url.indexOf(ROOT_DIRECTORY));

                    try {
                        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(key)
                                .build();
                        amazonS3Client.deleteObject(deleteObjectRequest);
                        log.info("대표사진 객체 삭제중");

                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new FilterException(FilterErrorResult.THUMBNAIL_IMG_DELETE_FAILED);
                    }
                }
            }
        }

        List<FilterCreateDto.RepresentationImgUrl> representationImgUrlDtos = new ArrayList<>();
        String s3FilePath;
        if(temporaryFilterId != null && filterId == null){
            s3FilePath = temporaryFilterId + "/" + REPRESENTATION_IMG_DIRECTORY;
        }else if(filterId != null && temporaryFilterId == null){
            s3FilePath = filterId + "/" + REPRESENTATION_IMG_DIRECTORY;
        }else{
            s3FilePath = null;
        }

        for(MultipartFile representationImg : representationImgs){
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

    public void saveRepresentationImage(List<FilterCreateDto.RepresentationImgUrl> representationImgUrls, UUID temporaryFilterId, UUID filterId){
        if(temporaryFilterId != null && filterId == null){
            TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));

            if(representationImgUrlRepository.existsAllByTemporaryFilter(findTemporaryFilter)) { // 두번째 임시저장 -> update
                log.info("두번째 임시저장 경우, 대표사진 url db 삭제");
                List<RepresentationImgUrl> findAllRepresentationImgUrl = representationImgUrlRepository.findAllByTemporaryFilter(findTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_REPRESENTATION_IMG_URL));
                representationImgUrlRepository.deleteAll(findAllRepresentationImgUrl);

            }

            log.info("대표사진 url db 생성 중");
            for(FilterCreateDto.RepresentationImgUrl representationImgUrl : representationImgUrls) {
                RepresentationImgUrl representationImgUrlEntity = RepresentationImgUrl.builder()
                        .cloudfrontUrl(representationImgUrl.getCloudfrontUrl())
                        .s3Url(representationImgUrl.getS3Url())
                        .build();

                representationImgUrlEntity.updateTemporaryFilter(findTemporaryFilter);
                representationImgUrlRepository.save(representationImgUrlEntity);
            }

        }
        else if(filterId != null && temporaryFilterId == null){
            log.info("바로 저장 경우, 대표사진 url db에 생성");
            Filter findFilter = filterRepository.findByFilterId(filterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));

            for(FilterCreateDto.RepresentationImgUrl representationImgUrl : representationImgUrls){
                RepresentationImgUrl representationImgUrlEntity = RepresentationImgUrl.builder()
                        .cloudfrontUrl(representationImgUrl.getCloudfrontUrl())
                        .s3Url(representationImgUrl.getS3Url())
                        .build();

                representationImgUrlEntity.updateFilter(findFilter);
                representationImgUrlRepository.save(representationImgUrlEntity);
            }
        }

    }

    // 똑같은 버튼 눌렀을때 중복처리되는거 예외처리하기
    public void saveTempFilterInformation(UUID temporaryFilterId, FilterCreateDto.CreateFilterRequest requestDto, UUID filterId){
        if(temporaryFilterId != null && filterId == null){

            TemporaryFilter findTemporaryFilter = temporaryFilterRepository.findByTemporaryFilterId(temporaryFilterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TEMPORARY_FILTER));
            if(filterTagRepository.existsAllByTemporaryFilter(findTemporaryFilter)) {
                log.info("두번째 임시저장 경우, 필터태그 삭제");
                List<FilterTag> filterTags = filterTagRepository.findAllByTemporaryFilter(findTemporaryFilter).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER_TAG));
                filterTagRepository.deleteAll(filterTags);
            }


            for(UUID tag : requestDto.getFilterInformation().getTagList().getTags()){
                log.info("tag : " + tag);
                FilterTag filterTag = FilterTag.builder().build();
                filterTag.updateTemporaryFilter(findTemporaryFilter);

                Tag savedtag = tagRepository.findByTagId(tag).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));

                filterTag.updateTag(savedtag);
            }
            findTemporaryFilter.updateTemporaryFilterInfo(requestDto.getFilterInformation().getName(), requestDto.getFilterInformation().getDescription());

            temporaryFilterRepository.save(findTemporaryFilter);

        } else if (filterId != null && temporaryFilterId == null) {
            Filter findFilter = filterRepository.findByFilterId(filterId).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));
            log.info("바로 저장 경우, 필터태그 생성");
            for(UUID tag : requestDto.getFilterInformation().getTagList().getTags()){
                FilterTag filterTag = FilterTag.builder().build();
                filterTag.updateFilter(findFilter);

                Tag savedtag = tagRepository.findByTagId(tag).orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));

                filterTag.updateTag(savedtag);
            }
            findFilter.updateFilterInfo(requestDto.getFilterInformation().getName(), requestDto.getFilterInformation().getDescription());
            filterRepository.save(findFilter);
        }


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


        log.info("임시저장 필터 옮기기, 자식들과 연관관계 끊기 -> 데이터 삭제");
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
