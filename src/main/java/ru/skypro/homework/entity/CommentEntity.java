package ru.skypro.homework.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class CommentEntity {

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
    private AdEntity adEntity;

    @Schema(description = "id автора комментария")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity userEntity;

    public CommentEntity() {
    }

    public CommentEntity(String text, Long createdAt, AdEntity adEntity, UserEntity userEntity) {
        this.text = text;
        this.createdAt = createdAt;
        this.adEntity = adEntity;
        this.userEntity = userEntity;
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

    public AdEntity getAd() {
        return adEntity;
    }

    public void setAd(AdEntity adEntity) {
        this.adEntity = adEntity;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
