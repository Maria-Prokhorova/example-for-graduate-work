package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    /**
     * Эндпойнт для получения всех объявлений.
     * Список всех объявлений может получить любой пользователь (user, admin и даже неавторизованной пользователь).
     *
     * @return список всех объявлений, хранимых в БД.
     */
    @Operation(summary = "Получение всех объявлений")
    @GetMapping
    public Ads getAllAds() {
        return adService.getAllAds();
    }

    /**
     * Эндпойнт для получения объявлений авторизованного пользователя.
     *
     * @return список объявлений авторизованного пользователя.
     */
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @GetMapping("/me")
    public Ads getAdsByUser() {
        return adService.getAdsByUser();
    }

    /**
     * Эндпойнт для добавления нового объявления.
     * Создать новое объявление может только авторизованный пользователь с ролью USER.
     *
     * @param newAd - информация об объявлении.
     * @param image - картинка объявления.
     * @return - ДТО "объявления".
     */
    @Operation(summary = "Добавление объявления")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Ad addAd(@RequestPart("newAd") CreateOrUpdateAd newAd, @RequestPart("image") MultipartFile image) {
        return adService.addAd(newAd, image);
    }

    /**
     * Эндпойнт для получения информации об объявлении по его id.
     *
     * @param id объявления.
     * @return ДТО.
     */
    @Operation(summary = "Получение информации об объявлении")
    @GetMapping("/{id}")
    public ExtendedAd getInfoAboutAd(@PathVariable Integer id) {
        return adService.getInfoAboutAd(id);
    }

    /**
     * Эндпойнт для удаления объявления.
     * Авторизованный пользователь с ролью USER может удалять свое объявление. Кроме того, авторизованный пользователь
     * с ролью ADMIN может удалять объявления любых других пользователей.
     *
     * @param id объявления.
     */
    @Operation(summary = "Удаление объявления")
    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable Integer id) {
        adService.deleteAd(id);
    }

    /**
     * Эндпойнт для обновления информации об объявлении.
     * Авторизованный пользователь с ролью USER может обновить свое объявление. Кроме того, авторизованный пользователь
     * с ролью ADMIN может обновить объявления любых других пользователей.
     *
     * @param id       объявления.
     * @param updateAd новая информация об объявлении.
     * @return ДТО "объявление".
     */
    @Operation(summary = "Обновление информации об объявлении")
    @PatchMapping("/{id}")
    public Ad updateInfoAboutAd(@PathVariable Integer id, @RequestBody CreateOrUpdateAd updateAd) {
        return adService.updateInfoAboutAd(id, updateAd);
    }

    /**
     * Эндпойнт для обновления картинки объявления.
     * Авторизованный пользователь с ролью USER может обновить свое объявление. Кроме того, авторизованный пользователь
     * с ролью ADMIN может обновить объявления любых других пользователей.
     *
     * @param id    объявления.
     * @param image новая картинка объявления.
     * @return обновленный путь к новой картинке.
     */
    @Operation(summary = "Обновление картинки объявлений")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateAvatarAd(@PathVariable Integer id, @RequestParam MultipartFile image) {
        return adService.updateAvatarAd(id, image);
    }
}
