package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.LastModifiedDate;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(columnDefinition = "BINARY(16)", name = "user_uuid", unique = true)
    private UUID userId;

    @Column(name = "profile_img_url")
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
    private List<Filter> filters = new ArrayList();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemporaryFilter> temporaryFilters = new ArrayList();

    public void addFilter(Filter filter){
        this.filters.add(filter);
    }

    public void addTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilters.add(temporaryFilter);
    }

    public void addUserTag(UserTag userTag){
        //this.userTag.add(userTag);
    }

    public String getStringId() {
        return this.userId.toString();
    }
}
