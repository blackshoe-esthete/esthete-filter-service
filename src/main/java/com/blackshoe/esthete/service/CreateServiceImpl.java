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


}
