package ru.skypro.homework.service;

import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;

public interface AdService {

    Ads getAllAds();

    Ad addAd(CreateOrUpdateAd newAd);

    ExtendedAd getInfoAboutAd(Integer adId);

    boolean deleteAd(Integer adId);

    Ad updateInfoAboutAd(Integer adId, CreateOrUpdateAd updateAd);

    Ads getAdsByUser();

    String updateAvatarAd(Integer adId, String image);
}
