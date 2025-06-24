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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "ad")
    private Set<Comment> commentsInAd = new HashSet<>();

    public Ad() {
    }

    public Ad(String title, Integer price, String description, String image, User user, Set<Comment> commentsInAd) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.user = user;
        this.commentsInAd = commentsInAd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getCommentsInAd() {
        return commentsInAd;
    }

    public void setCommentsInAd(Set<Comment> commentsInAd) {
        this.commentsInAd = commentsInAd;
    }
}
