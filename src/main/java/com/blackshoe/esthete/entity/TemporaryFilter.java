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
import java.util.*;

@Entity
@Getter
@Table(name = "tmp_filter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TemporaryFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tmp_filter_id")
    private Long id;

    @Column(name = "tmp_filter_uuid")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, length = 20)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", length = 20)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "tmp_filter_fk_user_id"))
    private User user; // User와 다대일 양방향, 주인

    @OneToMany(mappedBy = "tmpFilter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList();

    public void setUser(User user){
        this.user = user;
        user.getTmpFilters().add(this);
    }
}
