package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.TestSecurityConfig;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.AccessDeniedException;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdController.class)
@Import(TestSecurityConfig.class)
@WithMockUser(username = "test@example.com", roles = {"USER"})
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdServiceImpl adService;

    @Autowired
    private LocalValidatorFactoryBean validator;

    private Ad testAdDTO;
    private Ads testAdsDTO;
    private AdEntity testAdEntity;
    private CreateOrUpdateAd testCreateOrUpdateAdDTO;
    private ExtendedAd testExtendedAdDTO;
    private UserEntity testUser;
    private CommentEntity testCommentEntity;

    /**
     * Подготовка тестовых данных перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        testAdDTO = new Ad();
        testAdDTO.setAuthor(1);
        testAdDTO.setImage("/avatar.jpg");
        testAdDTO.setPk(1);
        testAdDTO.setPrice(1000);
        testAdDTO.setTitle("железная лопата");

        testCreateOrUpdateAdDTO = new CreateOrUpdateAd();
        testCreateOrUpdateAdDTO.setTitle("железная лопата");
        testCreateOrUpdateAdDTO.setPrice(1000);
        testCreateOrUpdateAdDTO.setDescription("железная лопата для земляных работ");

        testExtendedAdDTO = new ExtendedAd();
        testExtendedAdDTO.setPk(1);
        testExtendedAdDTO.setAuthorFirstName("Мария");
        testExtendedAdDTO.setAuthorLastName("Прохорова");
        testExtendedAdDTO.setDescription("железная лопата");
        testExtendedAdDTO.setEmail("Maria@mail.ru");
        testExtendedAdDTO.setImage("/avatar.jpg");
        testExtendedAdDTO.setPhone("+71112043601");
        testExtendedAdDTO.setPrice(1000);
        testExtendedAdDTO.setTitle("ad 1");

        testUser = new UserEntity();
        testUser.setId(1);
        testUser.setFirstName("Antony");
        testUser.setLastName("Mackey");
        testUser.setEmail("anmac@example.com");
        testUser.setPassword("password");
        testUser.setPhoneNumber("+98021010011");
        testUser.setRole(Role.USER);
        testUser.setImagePath("/avatar.jpg");

        testAdEntity = new AdEntity();
        testAdEntity.setId(1);
        testAdEntity.setAuthor(testUser);
        testAdEntity.setTitle("Test Ad");
        testAdEntity.setDescription("Test Ad Description");
        testAdEntity.setImagePath("/avatar.jpg");
        testAdEntity.setPrice(10000);

        testCommentEntity = new CommentEntity();
        testCommentEntity.setId(1);
        testCommentEntity.setText("text");
        testCommentEntity.setAuthor(testUser);
        testCommentEntity.setCreatedAt(LocalDateTime.now());
        testCommentEntity.setAd(testAdEntity);

        testAdEntity.setCommentsInAd(Set.of(testCommentEntity));

        testAdsDTO = new Ads();
        testAdsDTO.setCount(1);
        testAdsDTO.setResults(List.of(testAdDTO));

    }

    @Test
    @DisplayName("GET /ads — Получение всех объявлений")
    public void shouldReturnResultOfGetAllAds() throws Exception {

        when(adService.getAllAds()).thenReturn(testAdsDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(adService).getAllAds();
    }

    @Test
    @DisplayName("POST /ads — Добавление нового объявления")
    public void shouldReturnResultOfAddNewAdWhenSuccess() throws Exception {
        MultipartFile image = mock(MultipartFile.class);
        when(image.getBytes()).thenReturn(new byte[]{});

        when(adService.addAd(eq(testCreateOrUpdateAdDTO), any(MultipartFile.class))).thenReturn(new Ad());

        MockMultipartFile propertiesJson = new MockMultipartFile("properties",
                "properties.json",
                "application/json",
                "{\"title\": \"железная лопата\", \"price\": 1000, \"description\": \"железная лопата для земляных работ\"}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/ads")
                        .file("image", image.getBytes())
                        .file(propertiesJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /ads — Добавление нового объявления неавторизованным пользователем")
    @WithAnonymousUser
    void shouldReturnResultOfAddNewAdWhenCustomerIfNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ads"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /ads — Добавление нового объявления")
    public void shouldReturnResultOfGetAdByIdWhenSuccess() throws Exception {
        when(adService.getInfoAboutAd(1)).thenReturn(testExtendedAdDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.pk").value(testExtendedAdDTO.getPk()))
                .andExpect(jsonPath("$.authorFirstName").value(testExtendedAdDTO.getAuthorFirstName()))
                .andDo(print());

        verify(adService).getInfoAboutAd(1);
    }

    @Test
    @DisplayName("GET /ads/{id} — Попытка получить информации об объявлении неавторизованным пользователем")
    @WithAnonymousUser
    public void shouldReturnResultOfGetAdByIdWhenCustomerIfNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/{id}", 1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /ads/{id} — Попытка получить информации об объявлении, id которого не найдено в БД")
    void shouldReturnResultOfGetInfoAboutAdWhenDoNotIdAdExist() throws Exception {
        when(adService.getInfoAboutAd(1)).thenThrow(AdNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /ads/{id} — Удаление объявления")
    void shouldReturnResultOfDeleteAdWhenSuccess() throws Exception {
        doNothing().when(adService).deleteAd(1);

        mockMvc.perform(delete("/ads/{id}", 1))
                .andExpect(status().isNoContent());

        verify(adService).deleteAd(1);
    }

    @Test
    @DisplayName("DELETE /ads/{id} — Удаление объявления, неавторизованным пользователем")
    @WithAnonymousUser
    public void shouldReturnResultOfDeleteAdWhenCustomerIfNotAuthorized() throws Exception {
        mockMvc.perform(delete("/ads/{id}", 1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("DELETE /ads/{id} — Удаление объявления пользователем, у которого нет прав на удаление")
    public void shouldReturnResultOfDeleteAdWhenCustomerNoEditingRights() throws Exception {
        doThrow(new AccessDeniedException("Нет прав на редактирование объявления")).when(adService).deleteAd(1);

        mockMvc.perform(delete("/ads/{id}", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("DELETE /ads/{id} — Попытка удалить объявление, id которого не найдено в БД")
    void shouldReturnResultOfDeleteAdWhenDoNotIdAdExist() throws Exception {
        doThrow(new AdNotFoundException(1)).when(adService).deleteAd(1);

        mockMvc.perform(delete("/ads/{id}", 1))
                .andExpect(status().isNotFound());

        verify(adService).deleteAd(1);
    }

    @Test
    @DisplayName("PATH /ads/{id} — Обновление информации об объявлении")
    void shouldReturnResultOfUpdateInfoAboutAdWhenSuccess() throws Exception {
        when(adService.updateInfoAboutAd(1, testCreateOrUpdateAdDTO)).thenReturn(testAdDTO);

        mockMvc.perform(patch("/ads/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateOrUpdateAdDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.title").value(testAdDTO.getTitle()))
                .andExpect(jsonPath("$.price").value(testAdDTO.getPrice()))
                .andDo(print());

        verify(adService).updateInfoAboutAd(1, testCreateOrUpdateAdDTO);
    }

    @Test
    @DisplayName("PATH /ads/{id} — Обновление информации об объявлении, неавторизованным пользователем")
    @WithAnonymousUser
    public void shouldReturnResultOfUpdateInfoAboutAdWhenCustomerIfNotAuthorized() throws Exception {
        mockMvc.perform(patch("/ads/{id}", 1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PATH /ads/{id} — Обновление информации об объявлении пользователем, у которого нет прав на удаление")
    public void shouldReturnResultOfUpdateInfoAboutAdWhenCustomerNoEditingRights() throws Exception {
        when(adService.updateInfoAboutAd(1, testCreateOrUpdateAdDTO)).thenThrow(AccessDeniedException.class);

        mockMvc.perform(patch("/ads/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateOrUpdateAdDTO)))
                .andExpect(status().isForbidden());

        verify(adService).updateInfoAboutAd(1, testCreateOrUpdateAdDTO);
    }

    @Test
    @DisplayName("PATH /ads/{id} — Попытка обновить объявление, id которого не найдено в БД")
    void shouldReturnResultOfUpdateInfoAboutAdWhenDoNotIdAdExist() throws Exception {
        when(adService.updateInfoAboutAd(1, testCreateOrUpdateAdDTO)).thenThrow(AdNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateOrUpdateAdDTO)))
                .andExpect(status().isNotFound());

        verify(adService).updateInfoAboutAd(1, testCreateOrUpdateAdDTO);
    }

    @Test
    @DisplayName("GET /ads/me — Получение объявлений авторизованного пользователя")
    void shouldReturnResultOfGetAdsByUserWhenSuccess() throws Exception {
        when(adService.getAdsByUser()).thenReturn(testAdsDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.count").value(testAdsDTO.getCount()))
                .andExpect(jsonPath("$.results[0].pk").value(testAdsDTO.getResults().get(0).getPk()))
                .andExpect(jsonPath("$.results[0].title").value(testAdsDTO.getResults().get(0).getTitle()));

        verify(adService).getAdsByUser();
    }

    @Test
    @DisplayName("GET /ads/me — Попытка получить объявления неавторизованным пользователем")
    @WithAnonymousUser
    void shouldReturnResultOfGetAdsByUserWhenCustomerIfNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/me"))
                .andExpect(status().isUnauthorized());
    }


}