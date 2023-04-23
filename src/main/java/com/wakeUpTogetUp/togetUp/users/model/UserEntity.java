package com.wakeUpTogetUp.togetUp.users.model;

import com.wakeUpTogetUp.togetUp.users.model.UserRole;
import com.wakeUpTogetUp.togetUp.login.oauth.entity.ProviderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;



@Setter
@Getter
@Entity
@Table(name = "\"user\"")
@SQLDelete(sql = "UPDATE \"user\" SET removed_at = NOW() WHERE id=?")
//@Where(clause = "removed_at is NULL")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    @Column(name = "name")
    private String userName;

    @Column(name = "email")
    private String email;

    private String password;

    @Column(name = "statusMessage")
    private String statusMessage;

//    @Column(name = "avatarImgLink")
//    private String avatarImgLink;

//    @Enumerated(EnumType.STRING)
//    private UserRole role = UserRole.USER;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

//    @PrePersist
//    void removedAt() {
//        this.removedAt = Timestamp.from(Instant.now());
//    }

    public static UserEntity of(String encodedPwd) {
        UserEntity entity = new UserEntity();
        entity.setPassword(encodedPwd);
        return entity;
    }
}
