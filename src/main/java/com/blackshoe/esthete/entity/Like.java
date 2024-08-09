package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.LastModifiedDate;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "likes_fk_user_id"))
    private User user;

    @Column(name = "user_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID userId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "likes_fk_filter_id"))
    private Filter filter; // Filter와 다대일 양방향, 주인

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Like(User user, UUID userId, LocalDateTime createdAt, Filter filter){
        this.user = user;
        this.userId = userId;
        this.filter = filter;
        this.createdAt = createdAt;
    }

    public void updateFilter(Filter filter){
        this.filter = filter;
        filter.addLike(this);
    }

    public Boolean isUserLike(UUID viewerId){
        if(viewerId == null) return false;
        return this.userId.equals(viewerId);
    }

}
