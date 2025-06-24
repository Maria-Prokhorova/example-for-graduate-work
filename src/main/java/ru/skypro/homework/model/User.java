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
    private Set<Comment> commentsByUser = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Ad> adsByUser = new HashSet<>();

    public User() {
    }




}
