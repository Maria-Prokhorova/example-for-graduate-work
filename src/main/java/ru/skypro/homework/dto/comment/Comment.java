package ru.skypro.homework.dto.comment;

import lombok.Data;

/**
 * DTO для представления комментария
 */
@Data
public class Comment {
    private Integer author;        // id автора комментария
    private String authorImage;     // ссылка на аватар автора
    private String authorFirstName; // имя автора
    private Long createdAt;        // дата создания в миллисекундах
    private Integer pk;            // id комментария
    private String text;           // текст комментария
}
