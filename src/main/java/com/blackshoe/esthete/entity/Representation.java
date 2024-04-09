package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "representation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Representation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "representation_id")
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "representation_fk_filter_id"))
    private Filter filter; // Filter와 다대일 양방향, 주인

    public void setFilter(Filter filter){
        this.filter = filter;
        filter.getRepresentations().add(this);
    }
}
