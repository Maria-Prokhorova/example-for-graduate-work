package ru.skypro.homework.dto.ad;

import lombok.Data;

/**
 * DTO для представления объявления
 */
@Data
public class Ad {
    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}
