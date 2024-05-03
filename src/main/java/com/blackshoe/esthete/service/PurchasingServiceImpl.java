package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.entity.Filter;
import com.blackshoe.esthete.entity.Purchasing;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.exception.*;
import com.blackshoe.esthete.repository.FilterRepository;
import com.blackshoe.esthete.repository.PurchasingRepository;
import com.blackshoe.esthete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchasingServiceImpl implements PurchasingService {
    private final PurchasingRepository purchasingRepository;
    private final UserRepository userRepository;
    private final FilterRepository filterRepository;
    @Override
    public FilterDto.PurchaseResponse purchaseFilter(FilterDto.PurchaseRequest purchaseRequest) {

        User user = userRepository.findByUserId(purchaseRequest.getUserId()).orElseThrow
                (() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        Filter filter = filterRepository.findByFilterId(purchaseRequest.getFilterId()).orElseThrow
                (() -> new FilterException(FilterErrorResult.NOT_FOUND_FILTER));

        if (purchasingRepository.existsByUserAndFilter(user, filter)) {
            log.error("User already purchased this filter");
            throw new PurchasingException(PurchasingErrorResult.ALREADY_PURCHASED);
        } else {

            Purchasing purchasing = Purchasing.builder()
                    .purchasingId(UUID.randomUUID())
                    .filter(filter)
                    .build();

            purchasing.updateUser(user);
            purchasingRepository.save(purchasing);

            FilterDto.PurchaseResponse purchaseResponse = FilterDto.PurchaseResponse.builder()
                    .purchasedAt(purchasing.getCreatedAt().toString())
                    .build();

            return purchaseResponse;
        }
    }

}
