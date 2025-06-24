package ru.skypro.homework.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ads")
public class Ad {

    @Schema(description = "id объявления")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @Schema(description = "заголовок объявления")
    @Column(name = "title")
    private String title;

    @Schema(description = "цена объявления")
    @Column(name = "price")
    private Integer price;

    @Schema(description = "описание объявления")
    @Column(name = "description")
    private String description;

    @Schema(description = "ссылка на картинку объявления")
    @Column(name = "image")
    private String image;

    @Schema(description = "id автора объявления")
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "ad")
    private Set<Comment> commentsInAd = new HashSet<>();

    public Ad() {
    }
}
