package ru.skypro.homework.model;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Schema(description = "id пользователя")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(name ="role")
    private Role role;

    @Schema(description = "ссылка на аватар пользователя")
    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Ad> ads = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String phone, Role role, String image, Set<Comment> comments, Set<Ad> ads) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.image = image;
        this.comments = comments;
        this.ads = ads;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Ad> getAds() {
        return ads;
    }

    public void setAds(Set<Ad> ads) {
        this.ads = ads;
    }
}
