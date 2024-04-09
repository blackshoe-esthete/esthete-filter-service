package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
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

    @Column(name = "photo_uuid")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;

    @Column(name = "img_url")
    private String imgUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "photo_fk_filter_id"))
    private Filter filter; // Filter와 다대일 양방향, 주인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tmp_filter_id", foreignKey = @ForeignKey(name = "photo_fk_tmp_filter_id"))
    private TemporaryFilter tmpFilter; // TmpFilter와 다대일 양방향, 주인

    public void setFilter(Filter filter){
        this.filter = filter;
        filter.getPhotos().add(this);
    }

    public void setTmpFilter(TemporaryFilter tmpFilter){
        this.tmpFilter = tmpFilter;
        tmpFilter.getPhotos().add(this);
    }
}
