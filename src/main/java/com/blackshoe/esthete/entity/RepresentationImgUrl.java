package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "representation_img_url")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void updateFilter(Filter filter){
        this.filter = filter;
        filter.addRepresentationImgUrl(this); //명시적으로
    }

    public String getStringId() {
        return this.representationImgUrlId.toString();
    }
}
