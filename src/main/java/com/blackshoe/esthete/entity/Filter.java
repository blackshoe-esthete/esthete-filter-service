package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "filter")
@EntityListeners(AuditingEntityListener.class)
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filter_id")
    private Long id;

    @Column(name = "filter_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID filterId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "filter_fk_user_id"))
    private User user; // User와 다대일 양방향, 주인

    @OneToOne(mappedBy = "filter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Attribute attribute;

    @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepresentationImgUrl> representationImgUrls = new ArrayList();

//    @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Photo> photos = new ArrayList();

    @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList();

    @OneToOne(mappedBy = "filter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ThumbnailUrl thumbnailUrl;

//    @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Purchasing> purchasing = new ArrayList();
//
//    @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FilterTag> filterTags = new ArrayList();

    @ColumnDefault("0")
    @Column(name = "like_count")
    private Long likeCount;

    @ColumnDefault("0")
    @Column(name = "view_count")
    private Long viewCount;

    @PrePersist
    public void updateFilterId() {
        if (this.filterId == null) {
            this.filterId = UUID.randomUUID();
        }
    }

    @Builder
    public Filter(){}

    public void updateUser(User user){
        this.user = user;
        user.addFilter(this);
    }

    public void setAttribute(Attribute attribute){
        this.attribute = attribute;
    }

    public void addRepresentationImgUrl(RepresentationImgUrl representationImgUrl){
        this.representationImgUrls.add(representationImgUrl);
    }


    public void addLike(Like like){
        this.likes.add(like);
    }
    public void removeLike(Like like){
        this.likes.remove(like);
    }
    public void increaseLikeCount() {
        this.likeCount++;
    }
    public void decreaseLikeCount() {
        this.likeCount--;
    }
    public void increaseViewCount() {
        this.viewCount++;
    }

    public void setThumbnailUrl(ThumbnailUrl thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getStringId() {
        return this.filterId.toString();
    }

    public void updateFilterInfo(String name, String description){
        this.name = name;
        this.description = description;
    }
}
