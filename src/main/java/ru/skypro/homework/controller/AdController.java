package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.service.AdService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAds() {
        return new ResponseEntity<>(adService.getAllAds(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addAd(@RequestBody CreateOrUpdateAd newAd) {
        Ad ad = adService.addAd(newAd);
        if (ad != null) {
            return new ResponseEntity<>(ad, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInfoAboutAd(@PathVariable Integer id) {
        ExtendedAd infoAboutAd = adService.getInfoAboutAd(id    );
        if (infoAboutAd != null) {
            return new ResponseEntity<>(infoAboutAd, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        if (adService.deleteAd(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateInfoAboutAd(@PathVariable Integer id, @RequestBody CreateOrUpdateAd updateAd) {
        Ad newInfoAboutAd = adService.updateInfoAboutAd(id, updateAd);
        if (newInfoAboutAd != null) {
            return new ResponseEntity<>(newInfoAboutAd, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAdsByUser() {
        Ads allAds = adService.getAdsByUser();
        if (allAds != null) {
            return new ResponseEntity<>(allAds, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<?> updateAvatarAd(@PathVariable Integer id, @RequestBody String image) {
        String newImage = adService.updateAvatarAd(id, image);
        if (newImage != null) {
            return new ResponseEntity<>(newImage, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
