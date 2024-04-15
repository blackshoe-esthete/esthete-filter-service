package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "filter_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filter_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "filter_tag_fk_filter_id"))
    private Filter filter; // Filter와 다대일 양방향, 주인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "filter_tag_fk_tag_id"))
    private Tag tag; // FilterTag와 다대일 양방향, 주인

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    public void updateFilter(Filter filter){
        this.filter = filter;
        //filter.getFilterTags().add(this);
    }

    public void updateTag(Tag tag){
        this.tag = tag;
        tag.getFilterTags().add(this);
    }
}
