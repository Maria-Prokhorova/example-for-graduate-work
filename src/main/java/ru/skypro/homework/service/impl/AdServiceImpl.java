package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.SecurityService;

import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository repository;
    private final AdMapper adMapper;
    private final ImageService imageService;
    private final SecurityService securityService;

    public AdServiceImpl(AdRepository repository, AdMapper adMapper, ImageService imageService, SecurityService securityService) {
        this.repository = repository;
        this.adMapper = adMapper;
        this.imageService = imageService;
        this.securityService = securityService;
    }

    /**
     * Получение всех объявлений.
     *
     * @return возвращает список всех объявлений в виде ДТО Ads.
     */
    @Override
    public Ads getAllAds() {
        return (Ads) repository.findAll();
    }

    /**
     * Добавление объявлений.
     *
     * @param newAd информация об объявлении.
     * @param image - картинка объявления.
     * @return - ДТО ad.
     */
    @Override
    public Ad addAd(CreateOrUpdateAd newAd, MultipartFile image) {
        UserEntity userEntity = securityService.getCurrentUser();
        AdEntity createAdEntity = adMapper.toAdEntity(newAd, userEntity);
        String imagePath = imageService.saveImage(image);
        createAdEntity.setImagePath(imagePath);
        repository.save(createAdEntity);
        return adMapper.toAdDto(createAdEntity);
    }

    /**
     * Получение информации об объявлении.
     * Сначала находим в БД объявление по его id, затем с помощью маппера сущность "Объявление"
     * переводим в нужную нам ДТО.
     *
     * @param adId - id объявления.
     * @return ДТО ExtendedAd.
     */
    @Override
    public ExtendedAd getInfoAboutAd(Integer adId) {
        Optional<AdEntity> ad = repository.findById(adId);
        return adMapper.toExtendedAdDto(ad.orElse(null));
    }

    /**
     * Удаление объявления.
     *
     * @param adId - id объявления.
     */
    @Override
    public void deleteAd(Integer adId) {
        Optional<AdEntity> ad = repository.findById(adId);
        if (ad.isPresent()) {
            repository.delete(ad.orElse(null));
        } else throw new AdNotFoundException(adId);
    }

    /**
     * Обновление информации об объявлении.
     *
     * @param adId     - id объявления.
     * @param updateAd - ДТО updateAd - новая информация по объявлению.
     * @return - ДТО ad.
     */
    @Override
    public Ad updateInfoAboutAd(Integer adId, CreateOrUpdateAd updateAd) {
        Optional<AdEntity> ad = repository.findById(adId);
        adMapper.updateAdEntityFromDto(ad.orElse(null), updateAd);
        Optional<AdEntity> newAdAfterUpdate = repository.findById(adId);
        return adMapper.toAdDto(newAdAfterUpdate.orElse(null));
    }

    /**
     * Получение объявлений авторизованного пользователя.
     *
     * @return
     */
    @Override
    public Ads getAdsByUser() {
        UserEntity userEntity = securityService.getCurrentUser();
        return (Ads) userEntity.getAdsByUser();
    }

    /**
     * Обновление картинки объявления.
     * Сначала находим в БД объявление по его id. Затем сохраняем изображение.
     * Обновляем путь к картинке у найденного объявления.
     *
     * @param adId  - id объявления.
     * @param image - новая картинка объявления.
     * @return возвращаем обновленный путь к новой картинке.
     */
    @Override
    public String updateAvatarAd(Integer adId, MultipartFile image) {
        Optional<AdEntity> ad = repository.findById(adId);
        // Сохраняем изображение
        String imagePath = imageService.saveImage(image);

        // Обновляем путь к картинке
        ad.get().setImagePath(imagePath);
        repository.save(ad.orElse(null));
        return ad.get().getImagePath();
    }
}
