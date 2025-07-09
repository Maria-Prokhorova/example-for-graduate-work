package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Получает аватар пользователя.
     * @return изображение аватара
     */
    @GetMapping(value = "/users/me/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getUserAvatar() {
        return imageService.getUserAvatar();
    }

    /**
     * Получает изображение объявления по ID.
     * @param adId - ID объявления
     * @return изображение объявления
     */
    @GetMapping(value = "/ads/{adId}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getAdImage(@PathVariable Integer adId) {
        return imageService.getAdImage(adId);
    }

    /**
     * Получает изображение по имени файла.
     * Универсальный эндпоинт для получения изображений из папки uploads/images/
     * @param filename - имя файла изображения
     * @return изображение
     */
    @GetMapping(value = "/images/{filename:.+}")
    public byte[] getImageByFilename(@PathVariable String filename) {
        String filePath = "/images/" + filename;
        return imageService.getImage(filePath);
    }
} 