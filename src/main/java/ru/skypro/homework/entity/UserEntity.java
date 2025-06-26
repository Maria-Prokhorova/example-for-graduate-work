package ru.skypro.homework.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Schema(description = "id пользователя")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "имя пользователя")
    @Column(name = "first_name")
    private String firstName;

    @Schema(description = "фамилия пользователя")
    @Column(name = "last_name")
    private String lastName;

    @Schema(description = "логин пользователя")
    @Column(name = "email")
    private String email;

    @Schema(description = "пароль")
    @Column(name = "password")
    private String password;

    @Schema(description = "телефон пользователя")
    @Column(name = "phone")
    private String phoneNumber;

    @Schema(description = "роль пользователя")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Schema(description = "ссылка на аватар пользователя")
    @Column(name = "image")
    private String imagePath;

    @OneToMany(mappedBy = "author")
    private Set<CommentEntity> commentsByUser = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<AdEntity> adsByUser = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String email, String password, String phoneNumber, Role role, String imagePath, Set<CommentEntity> commentsByUser, Set<AdEntity> adsByUser) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.imagePath = imagePath;
        this.commentsByUser = commentsByUser;
        this.adsByUser = adsByUser;
    }
}
