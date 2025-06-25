package ru.skypro.homework.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ads")
public class AdEntity {

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
    private UserEntity userEntity;

    @OneToMany(mappedBy = "ad")
    private Set<CommentEntity> commentsInAd = new HashSet<>();

    public AdEntity() {
    }

    public AdEntity(String title, Integer price, String description, String image, UserEntity userEntity, Set<CommentEntity> commentsInAd) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.userEntity = userEntity;
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

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Set<CommentEntity> getCommentsInAd() {
        return commentsInAd;
    }

    public void setCommentsInAd(Set<CommentEntity> commentsInAd) {
        this.commentsInAd = commentsInAd;
    }
}
