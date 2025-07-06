package ru.skypro.homework.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdController.class)
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdMapper adMapper;

    @MockBean
    private AdRepository adRepository;

    @SpyBean
    private AdServiceImpl adService;

    @InjectMocks
    private AdController adController;

    @Test
    @DisplayName("GET /ads — Получение всех объявлений")
    public void whenGetAllAdsTest() throws Exception {
        Ad ad1 = new Ad();
        ad1.setAuthor(1);
        ad1.setImage("picturesads1");
        ad1.setPk(1);
        ad1.setPrice(1000);
        ad1.setTitle("lopata");

        Ad ad2 = new Ad();
        ad2.setAuthor(2);
        ad2.setImage("picturesads2");
        ad2.setPk(2);
        ad2.setPrice(1500);
        ad2.setTitle("gvozdi");

        List<Ad> adList = List.of(ad1, ad2);
        Ads mockAds = new Ads();
        mockAds.setCount(adList.size());
        mockAds.setResults(adList);

        when(adService.getAllAds()).thenReturn(mockAds);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.count").value(mockAds.getCount()))
                .andExpect(jsonPath("$.results").value(mockAds.getResults()))
                .andDo(print());

        verify(adService).getAllAds();
    }


}
