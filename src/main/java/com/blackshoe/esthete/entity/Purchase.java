package com.blackshoe.esthete.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "purchases")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @Column(name = "purchase_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "purchase_fk_user_id"))
    private User user; //ERD에서 수정, B/L 내부 로직이기 때문에 uuid 대신 user id 사용

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "purchase_fk_filter_id"))
    private Filter filter; // Filter와 다대일 양방향, 주인

    public void updateFilter(Filter filter){
        this.filter = filter;
        //filter.getPurchasing().add(this);
    }

    @PrePersist
    public void updateFilterId() {
        if (this.purchaseId == null) {
            this.purchaseId = UUID.randomUUID();
        }
    }
}
