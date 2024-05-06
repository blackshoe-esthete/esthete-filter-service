package com.blackshoe.esthete.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FilterErrorResult {
    NOT_FOUND_FILTER(HttpStatus.NOT_FOUND, "존재하지 않는 필터입니다."),
    INVALID_SORT_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 정렬 요청입니다." ),
    NOT_FOUND_TAG(HttpStatus.NOT_FOUND, "존재하지 않는 태그입니다."),
    INVALID_THUMBNAIL_IMG_SIZE(HttpStatus.UNPROCESSABLE_ENTITY, "유효하지 않은 썸네일 사진입니다."),
    THUMBNAIL_IMG_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "썸네일 사진 S3 업로드 실패했습니다."),
    NOT_FOUND_TEMPORARY_FILTER(HttpStatus.NOT_FOUND, "임시저장 되어 있는 필터를 찾지 못했습니다."),
    NOT_FOUND_ATTRIBUTE(HttpStatus.NOT_FOUND, "속성값을 찾지 못했습니다."),
    NOT_FOUND_THUMBNAIL_IMG_URL(HttpStatus.NOT_FOUND, "썸네일 이미지 주소를 찾지 못했습니다."),
    NOT_FOUND_REPRESENTATION_IMG_URL(HttpStatus.NOT_FOUND, "대표 이미지 주소를 찾지 못했습니다."),
    NOT_FOUND_FILTER_TAG(HttpStatus.NOT_FOUND, "필터 태그를 찾지 못했습니다."),
    THUMBNAIL_IMG_DELETE_FAILED(HttpStatus.BAD_REQUEST, "썸네일 사진 S3 삭제에 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

