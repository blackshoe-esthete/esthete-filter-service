package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;
@Entity
@Table(name = "thumbnail_url")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ThumbnailUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumbnail_url_id")
    private Long id;

    @Column(columnDefinition = "BINARY(16)", name = "thumbnail_url_uuid")
    private UUID thumbnailUrlId;

    private String cloudfrontUrl;

    private String s3Url;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "thumbnail_url_fk_filter_id"))
    private Filter filter;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "temporary_filter_id", foreignKey = @ForeignKey(name = "thumbnail_url_fk_temporary_filter_id"))
    private TemporaryFilter temporaryFilter;


    public void updateFilter(Filter filter){
        this.filter = filter;
        filter.setThumbnailUrl(this);
    }

    public void updateTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilter = temporaryFilter;
        temporaryFilter.setThumbnailUrl(this);
    }

    public void deleteTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilter = null;
        temporaryFilter.setThumbnailUrl(null);
    }

    public void updateThumbnailUrl(String cloudfrontUrl, String s3Url){
        this.cloudfrontUrl = cloudfrontUrl;
        this.s3Url = s3Url;
    }

    @Builder
    public ThumbnailUrl(String cloudfrontUrl, String s3Url) {
        this.cloudfrontUrl = cloudfrontUrl;
        this.s3Url = s3Url;
    }

    @PrePersist
    public void updateThumbnailUrlId() {
        if (thumbnailUrlId == null) {
            thumbnailUrlId = UUID.randomUUID();
        }
    }

    public String getStringId() {
        return this.thumbnailUrlId.toString();
    }
}
