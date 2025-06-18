package ru.skypro.homework.dto.comment;

import lombok.Data;

import java.util.List;

/**
 * DTO для списка комментариев
 */
@Data
public class Comments {
    private Integer count;
    private List<Comment> results;
}
