package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.dto.LikeDto;
import com.blackshoe.esthete.entity.Filter;
import com.blackshoe.esthete.entity.Like;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.exception.FilterErrorResult;
import com.blackshoe.esthete.exception.FilterException;
import com.blackshoe.esthete.exception.UserErrorResult;
import com.blackshoe.esthete.exception.UserException;
import com.blackshoe.esthete.repository.FilterRepository;
import com.blackshoe.esthete.repository.LikeRepository;
import com.blackshoe.esthete.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final FilterRepository filterRepository;
    @Override
    public Page<LikeDto.ReadResponse> getLikeFilterList(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<LikeDto.ReadResponse> likeFilterList = likeRepository.readByUserId(userId, pageable);

        return likeFilterList;
    }

    @Transactional
    @Override
    public void likeFilter(UUID userId, UUID filterId) {
        final User user = userRepository.findByUserId(userId).orElseThrow
                (() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        final Filter filter = filterRepository.findByFilterId(filterId).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));
        filter.increaseLikeCount();

        Like like = Like.builder()
                .user(user)
                .userId(userId)
                .filter(filter)
                .createdAt(LocalDateTime.now())
                .build();

        likeRepository.save(like);
    }

    @Transactional
    @Override
    public void unlikeFilter(UUID userId, UUID filterId) {
        final User user = userRepository.findByUserId(userId).orElseThrow
                (() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        final Filter filter = filterRepository.findByFilterId(filterId).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));

        filter.decreaseLikeCount();

        Like like = likeRepository.findByUserAndFilter(user, filter).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_LIKE));

        likeRepository.delete(like);
    }
}
