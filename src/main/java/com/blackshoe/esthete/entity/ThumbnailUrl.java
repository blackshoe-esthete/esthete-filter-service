package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;
@Entity
@Table(name = "thumbnail_urls")
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
    private Filter filter; // Attribute와 일대일 양방향, 주인

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "temporary_filter_id", foreignKey = @ForeignKey(name = "thumbnail_url_fk_temporary_filter_id"))
    private TemporaryFilter temporaryFilter; // Attribute와 일대일 양방향, 주인


    public void updateFilter(Filter filter){
        this.filter = filter;
        filter.setThumbnailUrl(this);
    }

    public void updateTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilter = temporaryFilter;
        temporaryFilter.setThumbnailUrl(this);
    }

    @Builder
    public ThumbnailUrl(UUID thumbnailUrlId, String cloudfrontUrl, String s3Url) {
        this.thumbnailUrlId = thumbnailUrlId;
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
