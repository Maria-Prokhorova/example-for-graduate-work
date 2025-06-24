package ru.skypro.homework.dto.ad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * DTO для списка объявлений
 */
@Data
public class AdsDto {

    @Schema(description = "общее количество объявлений")
    private Integer count;
    private List<AdDto> results;
}
