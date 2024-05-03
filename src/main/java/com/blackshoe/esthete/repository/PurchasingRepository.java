package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.Filter;
import com.blackshoe.esthete.entity.Purchasing;
import com.blackshoe.esthete.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasingRepository extends JpaRepository<Purchasing, Long>{
    boolean existsByUserAndFilter(User user, Filter filter);
}
