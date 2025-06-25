package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.service.AdService;

@Tag(name = "Объявления", description = "Раздел содержит методы по работе с объявлениями")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @Operation(summary = "Получение всех объявлений")
    @GetMapping
    public Ads getAllAds() {
        return adService.getAllAds();
    }

    @Operation(summary = "Добавление объявления")
    @PostMapping()
    public Ad addAd(@RequestBody CreateOrUpdateAd newAd) {
        return adService.addAd(newAd);
    }

    @Operation(summary = "Получение информации об объявлении")
    @GetMapping("/{id}")
    public ExtendedAd getInfoAboutAd(@PathVariable Integer id) {
        return adService.getInfoAboutAd(id);
    }

    @Operation(summary = "Удаление объявления")
    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable Integer id) {
        adService.deleteAd(id);
    }

    @Operation(summary = "Обновление информации об объявлении")
    @PatchMapping("/{id}")
    public Ad updateInfoAboutAd(@PathVariable Integer id, @RequestBody CreateOrUpdateAd updateAd) {
        return adService.updateInfoAboutAd(id, updateAd);
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @GetMapping("/me")
    public Ads getAdsByUser() {
        return adService.getAdsByUser();
    }

    @Operation(summary = "Обновление картинки объявлений")
    @PatchMapping("/{id}/image")
    public String updateAvatarAd(@PathVariable Integer id, @RequestBody String image) {
        return adService.updateAvatarAd(id, image);
    }
}
