package com.blackshoe.esthete.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "attribute")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long id;

    @Column(name = "brightness")
    private Float brightness;

    @Column(name = "sharpness")
    private Float sharpness;

    @Column(name = "exposure")
    private Float exposure;

    @Column(name = "contrast")
    private Float contrast;

    @Column(name = "saturation")
    private Float saturation;

    @Column(name = "highlights")
    private Float highlights;

    @Column(name = "shadows")
    private Float shadows;

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "attribute_fk_filter_id"))
    private Filter filter; // Attribute와 일대일 양방향, 주인

    public void updateFilter(Filter filter){
        this.filter = filter;
        filter.updateAttribute(this);
    }
}
