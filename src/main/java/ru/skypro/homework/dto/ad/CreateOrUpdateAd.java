package ru.skypro.homework.dto.ad;

import lombok.Data;

/**
 * DTO для создания и обновления объявления
 */
@Data
public class CreateOrUpdateAd {
    private String title;
    private Integer price;
    private String description;
}
