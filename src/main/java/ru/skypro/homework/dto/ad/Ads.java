package ru.skypro.homework.dto.ad;

import lombok.Data;

import java.util.List;

/**
 * DTO для списка объявлений
 */
@Data
public class Ads {
    private Integer count;
    private List<Ad> results;
}
