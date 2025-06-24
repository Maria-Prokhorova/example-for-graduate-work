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
    @Column(name ="text")
    private String text;

    @Schema(description = "дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    @Column(name ="data_time")
    private Long createdAt;

    @Schema(description = "объявление")
    @ManyToOne
    @JoinColumn(name = "ads_pk")
    private Ad ad;

    @Schema(description = "id автора комментария")
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    public Comment() {
    }


}
