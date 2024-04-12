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
@Table(name = "purchasing")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchasing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchasing_id")
    private Long id;

    @Column(name = "purchasing_uuid", columnDefinition = "BINARY(16)", unique = true)
    private UUID purchasingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "purchasing_fk_user_id"))
    private User user; //ERD에서 수정, B/L 내부 로직이기 때문에 uuid 대신 user id 사용

    @CreatedDate
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", foreignKey = @ForeignKey(name = "purchasing_fk_filter_id"))
    private Filter filter; // Filter와 다대일 양방향, 주인

    public void updateFilter(Filter filter){
        this.filter = filter;
        //filter.getPurchasing().add(this);
    }

    @PrePersist
    public void updateFilterId() {
        if (this.purchasingId == null) {
            this.purchasingId = UUID.randomUUID();
        }
    }
}
