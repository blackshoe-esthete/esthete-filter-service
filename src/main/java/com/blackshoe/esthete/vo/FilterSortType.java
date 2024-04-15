package com.blackshoe.esthete.vo;

import com.blackshoe.esthete.exception.FilterException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import com.blackshoe.esthete.exception.FilterErrorResult;
@Getter
@RequiredArgsConstructor
public enum FilterSortType {
 //   TRENDING("viewCount"),
    RECENT("createdAt"),
    ;

    private final String sortType;

    public static Sort convertParamToColumn(String sort) {
        switch (sort) {
            case "recent":
                return Sort.by(Sort.Direction.DESC, RECENT.sortType);
            default:
                throw new FilterException(FilterErrorResult.INVALID_SORT_TYPE);
        }
    }
}

