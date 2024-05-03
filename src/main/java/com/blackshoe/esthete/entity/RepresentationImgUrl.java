package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Getter
@Table(name = "representation_img_urls")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RepresentationImgUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "representation_img_url_id")
    private Long id;

    @Column(name = "representation_img_url_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID representationImgUrlId;

    private String cloudfrontUrl;

    private String s3Url;
//
//    @CreatedDate
//    @Column(name = "created_at", nullable = false, length = 20)
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    @Column(name = "updated_at", length = 20)
//    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "representation_fk_filter_id"))
    private Filter filter; // Filter와 다대일 양방향, 주인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temporary_filter_id", foreignKey = @ForeignKey(name = "representation_fk_temporary_filter_id"))
    private TemporaryFilter temporaryFilter;

    public void updateFilter(Filter filter){
        this.filter = filter;
        filter.addRepresentationImgUrl(this);
    }

    public void updateTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilter = temporaryFilter;
        temporaryFilter.addRepresentationImgUrl(this);
    }

    public void deleteTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilter = null;
        temporaryFilter.deleteRepresentationImgUrl(this);
    }

    public void updateRepresentationImgUrl(String cloudfrontUrl, String s3Url){
        this.cloudfrontUrl = cloudfrontUrl;
        this.s3Url = s3Url;
    }

    @Builder
    public RepresentationImgUrl(String cloudfrontUrl, String s3Url) {
        this.cloudfrontUrl = cloudfrontUrl;
        this.s3Url = s3Url;
    }

    @PrePersist
    public void updateRepresentationUrlId() {
        if (representationImgUrlId == null) {
            representationImgUrlId = UUID.randomUUID();
        }
    }

    public String getStringId() {
        return this.representationImgUrlId.toString();
    }
}
