package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Table(name = "temporary_filter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class TemporaryFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temporary_filter_id")
    private Long id;

    @Column(name = "temporary_filter_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID temporaryFilterId;

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
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "temporary_filter_fk_user_id"))
    private User user; // User와 다대일 양방향, 주인, 부모

    @OneToMany(mappedBy = "temporaryFilter", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList();

    @OneToOne(mappedBy = "temporaryFilter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ThumbnailUrl thumbnailUrl;

    @OneToOne(mappedBy = "temporaryFilter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Attribute attribute; //추가함

    @OneToMany(mappedBy = "temporaryFilter", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepresentationImgUrl> representationImgUrls = new ArrayList(); //추가함


    public void setThumbnailUrl(ThumbnailUrl thumbnailUrl){
        this.thumbnailUrl = thumbnailUrl;
    }
    public void updateUser(User user){
        this.user = user;
        user.addTemporaryFilter(this);
    }

    public void deleteUser(User user){
        this.user = null;
        user.deleteTemporaryFilter(null);
    }

    public String getStringId() {
        return this.temporaryFilterId.toString();
    }
    public void setAttribute(Attribute attribute){
        this.attribute = attribute;
    } //추가함

    public void addRepresentationImgUrl(RepresentationImgUrl representationImgUrl){
        this.representationImgUrls.add(representationImgUrl); //추가함
    }

    public void deleteRepresentationImgUrl(RepresentationImgUrl representationImgUrl){
        this.representationImgUrls.add(null); //추가함
    }

    @PrePersist
    public void setTemporaryFilterId() {
        if (temporaryFilterId == null) {
            temporaryFilterId = UUID.randomUUID();
        }
    }

    @Builder
    public TemporaryFilter(String name, String description){
        this.name = name;
        this.description = description;
    }


    public void updateTemporaryFilterInfo(String name, String description){
        this.name = name;
        this.description = description;
    }

}
