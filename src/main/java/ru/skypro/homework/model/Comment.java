package ru.skypro.homework.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Schema(description = "id комментария")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @Schema(description = "текст комментария")
    @Column(name = "text")
    private String text;

    @Schema(description = "дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    @Column(name = "data_time")
    private Long createdAt;

    @Schema(description = "объявление")
    @ManyToOne
    @JoinColumn(name = "ads_pk")
    private Ad ad;

    @Schema(description = "id автора комментария")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public Comment() {
    }

    public Comment(String text, Long createdAt, Ad ad, User user) {
        this.text = text;
        this.createdAt = createdAt;
        this.ad = ad;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
