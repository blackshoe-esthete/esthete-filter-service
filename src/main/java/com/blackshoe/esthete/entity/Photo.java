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
import java.util.UUID;

@Entity
@Getter
@Table(name = "photo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @Column(name = "photo_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID photoId;

    @Column(name = "img_url")
    private String imgUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "photo_fk_filter_id"))
    private Filter filter; // Filter와 다대일 양방향, 주인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temporary_filter_id", foreignKey = @ForeignKey(name = "photo_fk_temporary_filter_id"))
    private TemporaryFilter temporaryFilter; // TemporaryFilter와 다대일 양방향, 주인

    public void updateFilter(Filter filter){
        this.filter = filter;
        //filter.getPhotos().add(this);
    }

    public void updateTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilter = temporaryFilter;
        temporaryFilter.getPhotos().add(this);
    }
}
