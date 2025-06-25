package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.service.AdService;

@Service
public class AdServiceImpl implements AdService {

    @Override
    public Ads getAllAds() {
        return null;
    }

    @Override
    public Ad addAd(CreateOrUpdateAd newAd){
        return null;
    }

    @Override
    public ExtendedAd getInfoAboutAd(Integer adId) {
        return null;
    }

    @Override
    public boolean deleteAd(Integer adId){
        return true;
    }

    @Override
    public Ad updateInfoAboutAd(Integer adId, CreateOrUpdateAd updateAd) {
        return null;
    }

    @Override
    public Ads getAdsByUser(){
        return null;
    }

    @Override
    public String updateAvatarAd(Integer adId, String image){
        return null;
    }
}
