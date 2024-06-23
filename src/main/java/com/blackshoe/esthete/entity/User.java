package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.LastModifiedDate;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(columnDefinition = "BINARY(16)", name = "user_uuid", unique = true)
    private UUID userId;

    @Column(name = "profile_img_url", nullable = false, columnDefinition = "VARCHAR(250) default 'default'")
    private String profileImgUrl;

    @Column(name = "nickname")
    private String nickname;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "role")
//    private Role role;

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Filter> filters = new ArrayList(); // 이거 단방향으로 열어놔도 될듯, 여기는 없애고 Filter 테이블에만 user있도록

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemporaryFilter> temporaryFilters = new ArrayList();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchasing> purchasings = new ArrayList();

    @Builder
    public User(UUID userId, String nickname){
        this.userId = userId;
        this.nickname = nickname;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void addFilter(Filter filter){
        this.filters.add(filter);
    }

    public void addTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilters.add(temporaryFilter);
    }

    public void deleteTemporaryFilter(TemporaryFilter temporaryFilter) {
        this.temporaryFilters.add(temporaryFilter);
    }

    public void addPurchasing(Purchasing purchasing){
        this.purchasings.add(purchasing);
    }

    public void addUserTag(UserTag userTag){
        //this.userTag.add(userTag);
    }

    public String getStringId() {
        return this.userId.toString();
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }
}
