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

@Entity
@Getter
@Table(name = "filter_tag")
@EntityListeners(AuditingEntityListener.class)
public class FilterTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filter_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "filter_tag_fk_filter_id"))
    private Filter filter; // Filter와 다대일 단방향, 주인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temporary_filter_id", foreignKey = @ForeignKey(name = "filter_tag_fk_temporary_filter_id"))
    private TemporaryFilter temporaryFilter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "filter_tag_fk_tag_id"))
    private Tag tag; // FilterTag와 다대일 양방향, 주인

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @Builder
    public FilterTag(){}

    public void updateFilter(Filter filter){
        this.filter = filter;
        //filter.getFilterTags().add(this);
    }


    public void updateTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilter = temporaryFilter;
    }

    public void deleteTemporaryFilter(TemporaryFilter temporaryFilter){
        this.temporaryFilter = null;
    }


    public void updateTag(Tag tag){
        this.tag = tag;
        tag.getFilterTags().add(this);
    }
}
