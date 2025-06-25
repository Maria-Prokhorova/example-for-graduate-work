package ru.skypro.homework.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
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
    private String phone;

    @Schema(description = "роль пользователя")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Schema(description = "ссылка на аватар пользователя")
    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "user")
    private Set<CommentEntity> commentsByUser = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<AdEntity> adsByUser = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String email, String password, String phone, Role role, String image, Set<CommentEntity> commentsByUser, Set<AdEntity> adsByUser) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.image = image;
        this.commentsByUser = commentsByUser;
        this.adsByUser = adsByUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<CommentEntity> getCommentsByUser() {
        return commentsByUser;
    }

    public void setCommentsByUser(Set<CommentEntity> commentsByUser) {
        this.commentsByUser = commentsByUser;
    }

    public Set<AdEntity> getAdsByUser() {
        return adsByUser;
    }

    public void setAdsByUser(Set<AdEntity> adsByUser) {
        this.adsByUser = adsByUser;
    }
}
