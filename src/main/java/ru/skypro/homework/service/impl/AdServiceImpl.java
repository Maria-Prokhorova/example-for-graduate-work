package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

@Service
public class AdServiceImpl implements AdService {

    @Override
    public AdsDto getAllAds() {
        return null;
    }

    @Override
    public AdDto addAd(CreateOrUpdateAdDto newAd){
        return null;
    }

    @Override
    public ExtendedAdDto getInfoAboutAd(Integer adId) {
        return null;
    }

    @Override
    public boolean deleteAd(Integer adId){
        return true;
    }

    @Override
    public AdDto updateInfoAboutAd(Integer adId, CreateOrUpdateAdDto updateAd) {
        return null;
    }

    @Override
    public AdsDto getAdsByUser(){
        return null;
    }

    @Override
    public String updateAvatarAd(Integer adId, String image){
        return null;
    }
}
