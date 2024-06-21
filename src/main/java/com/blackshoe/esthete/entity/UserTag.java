package com.blackshoe.esthete.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user_tags")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter @Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserTag {
    //user filter 선호 태그
    @Id
    @GeneratedValue
    @Column(name = "user_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_tag_fk_user_id"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "user_tag_fk_tag_id"))
    private Tag tag;

    @Builder
    public UserTag(Tag tag) {
        this.tag = tag;
    }

    public void updateUser(User user) {
        this.user = user;
        //user.addUserTag(this);
    }
}
