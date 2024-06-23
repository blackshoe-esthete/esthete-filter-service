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
@Table(name = "purchasings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Purchasing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchasing_id")
    private Long id;

    @Column(name = "purchasing_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID purchasingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "purchasing_fk_user_id"))
    private User user;

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "purchasing_fk_filter_id"))
    private Filter filter;

    @Builder
    public Purchasing(UUID purchasingId, User user, Filter filter, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.purchasingId = purchasingId;
        this.user = user;
        this.filter = filter;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateFilter(Filter filter){
        this.filter = filter;
        //filter.getPurchasing().add(this);
    }

    public void updateUser(User user){
        this.user = user;
        user.addPurchasing(this);
    }

    @PrePersist
    public void updateFilterId() {
        if (this.purchasingId == null) {
            this.purchasingId = UUID.randomUUID();
        }
    }

    public String getStringId() {
        return this.purchasingId.toString();
    }
}
