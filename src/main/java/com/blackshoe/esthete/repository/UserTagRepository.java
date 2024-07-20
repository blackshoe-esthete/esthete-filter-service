package com.blackshoe.esthete.repository;

import com.blackshoe.esthete.entity.Tag;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    boolean existsByUserAndTag(User user, Tag tag);

    void deleteByUserAndTag(User user, Tag tag);


    @Query("SELECT ut FROM UserTag ut JOIN FETCH ut.tag WHERE ut.user = :user")
    List<UserTag> findByUserWithTags(@Param("user") User user);
}
