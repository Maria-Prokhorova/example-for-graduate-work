package ru.skypro.homework.service;

import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;

public interface AdService {

    AdsDto getAllAds();

    AdDto addAd(CreateOrUpdateAdDto newAd);

    ExtendedAdDto getInfoAboutAd(Integer adId);

    boolean deleteAd(Integer adId);

    AdDto updateInfoAboutAd(Integer adId, CreateOrUpdateAdDto updateAd);

    AdsDto getAdsByUser();

    String updateAvatarAd(Integer adId, String image);
}
