package ru.skypro.homework.dto.comment;

import lombok.Data;

/**
 * DTO для создания и обновления комментария
 */
@Data
public class CreateOrUpdateComment {
    private String text;
}
