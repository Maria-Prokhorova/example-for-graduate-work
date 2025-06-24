package ru.skypro.homework.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
@Table(name = "ads")
public class Ad {

    @Schema(description = "id объявления")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pk;

    @Schema(description = "заголовок объявления")
    private String title;

    @Schema(description = "цена объявления")
    private Integer price;

    @Schema(description = "описание объявления")
    private String description;

    @Schema(description = "ссылка на картинку объявления")
    private String image;

    @OneToOne(mappedBy = "comment")
    private Comment comment;

    @Schema(description = "id автора объявления")
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    public Ad() {
    }

    public Ad(String title, Integer price, String description, String image, Comment comment, User user) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.comment = comment;
        this.user = user;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
