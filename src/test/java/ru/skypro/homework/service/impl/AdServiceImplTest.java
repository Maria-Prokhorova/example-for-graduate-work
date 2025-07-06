package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
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
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.SecurityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Основные тесты для AdServiceImpl.
 * Тестирует успешные сценарии для всех публичных методов AdService.
 */
@ExtendWith(MockitoExtension.class)
public class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private AdMapper adMapper;

    @Mock
    private SecurityService securityService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private AdServiceImpl adService;

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
        testAdDTO.setImage("picturesads1");
        testAdDTO.setPk(1);
        testAdDTO.setPrice(1000);
        testAdDTO.setTitle("lopata");

        testCreateOrUpdateAdDTO = new CreateOrUpdateAd();
        testCreateOrUpdateAdDTO.setTitle("железная лопата");
        testCreateOrUpdateAdDTO.setPrice(1000);
        testCreateOrUpdateAdDTO.setDescription("ad 1");

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

    /**
     * Тест предоставления всех объявлений
     */
    @Test
    void shouldReturnResultOfGetAllAds() {
        when(adRepository.findAll()).thenReturn(List.of(testAdEntity));
        when(adMapper.toAdDto(testAdEntity)).thenReturn(testAdDTO);

        assertEquals(testAdsDTO, adService.getAllAds());

        verify(adRepository).findAll();
        verify(adMapper).toAdDto(testAdEntity);
    }

    /**
     * Тест на получение информации об объявлении по его id
     */
    @Test
    void shouldReturnResultOfGetInfoAboutAdWhenAdExist() {
        when(adRepository.findById(1)).thenReturn(Optional.ofNullable(testAdEntity));
        when(adMapper.toExtendedAdDto(testAdEntity)).thenReturn(testExtendedAdDTO);

        assertEquals(testExtendedAdDTO, adService.getInfoAboutAd(1));

        verify(adRepository).findById(1);
        verify(adMapper).toExtendedAdDto(testAdEntity);
    }

    /**
     * Тест на выброс исключения AdNotFoundException, в случае отсутствия в БД id объявления
     */
    @Test
    void shouldReturnResultOfGetInfoAboutAdWhenDoNotIdAdExist() {
        when(adRepository.findById(2)).thenThrow(AdNotFoundException.class);

        assertThrows(AdNotFoundException.class, () -> adService.getInfoAboutAd(2));
    }

    /**
     * Тест на добавление нового объявления
     */
    @Test
    void shouldReturnResultOfAddAdWhenAdWasAdded() {
        MultipartFile mockFile = mock(MultipartFile.class);
        String newImagePath = "/new-avatar.jpg";

        when(securityService.getCurrentUser()).thenReturn(testUser);
        when(adMapper.toAdEntity(testCreateOrUpdateAdDTO, testUser)).thenReturn(testAdEntity);
        when(imageService.saveImage(mockFile)).thenReturn(newImagePath);
        when(adRepository.save(any(AdEntity.class))).thenReturn(testAdEntity);
        when(adMapper.toAdDto(testAdEntity)).thenReturn(testAdDTO);

        assertEquals(testAdDTO, adService.addAd(testCreateOrUpdateAdDTO, mockFile));

        verify(securityService).getCurrentUser();
        verify(adMapper).toAdEntity(testCreateOrUpdateAdDTO, testUser);
        verify(imageService).saveImage(mockFile);
        verify(adRepository).save(any(AdEntity.class));
        verify(adMapper).toAdDto(testAdEntity);
    }

    /**
     * Тест на обновлении информации об объявлении
     */
    @Test
    void shouldReturnResultOfUpdateInfoAboutAdWhenSuccess() {
        when(adRepository.findById(1)).thenReturn(Optional.ofNullable(testAdEntity));
        when(adRepository.save(any(AdEntity.class))).thenReturn(testAdEntity);
        when(adMapper.toAdDto(testAdEntity)).thenReturn(testAdDTO);

        securityService.checkPermissionToEditAd(testAdEntity);
        adMapper.updateAdEntityFromDto(testAdEntity, testCreateOrUpdateAdDTO);

        verify(securityService).checkPermissionToEditAd(testAdEntity);
        verify(adMapper).updateAdEntityFromDto(testAdEntity, testCreateOrUpdateAdDTO);

        assertEquals(testAdDTO, adService.updateInfoAboutAd(1, testCreateOrUpdateAdDTO));

        verify(adRepository).save(any(AdEntity.class));
        verify(adMapper).toAdDto(testAdEntity);
    }

    /**
     * Тест на выброс исключения AdNotFoundException, в случае отсутствия в БД id объявления, которое нужно обновить
     */
    @Test
    void shouldReturnResultOfUpdateAdWhenDoNotIdAdExist() {
        when(adRepository.findById(2)).thenThrow(AdNotFoundException.class);

        assertThrows(AdNotFoundException.class, () -> adService.updateInfoAboutAd(2, testCreateOrUpdateAdDTO));

        verify(adRepository).findById(2);
    }

    /**
     * Тест на выброс исключения AccessDeniedException, в случае отсутствия прав на редактирование объявления
     */
    @Test
    void shouldReturnResultOfUpdateAdWhenNoEditingRights() {
        when(adRepository.findById(1)).thenReturn(Optional.ofNullable(testAdEntity));

        doThrow(new AccessDeniedException("Нет прав на редактирование объявления"))
                .when(securityService)
                .checkPermissionToEditAd(testAdEntity);

        assertThrows(AccessDeniedException.class, () -> adService.updateInfoAboutAd(1, testCreateOrUpdateAdDTO));

        verify(adRepository).findById(1);
        verify(securityService).checkPermissionToEditAd(testAdEntity);
    }

    /**
     * Тест на получение объявлений авторизованного пользователя
     */
    @Test
    void shouldReturnResultOfGetAdsByUserWhenSuccess() {
        when(securityService.getCurrentUser()).thenReturn(testUser);
        when(adRepository.findByIdUser(testUser.getId())).thenReturn(List.of(testAdEntity));
        when(adMapper.toAdDto(testAdEntity)).thenReturn(testAdDTO);

        assertEquals(testAdsDTO, adService.getAdsByUser());

        verify(adRepository).findByIdUser(testUser.getId());
        verify(adMapper).toAdDto(testAdEntity);
    }

    /**
     * Тест на выброс исключения AccessDeniedException, в случае отсутствия прав на редактирование объявления
     */
    @Test
    void shouldReturnResultOfGetAdsByUserWhenException() {
        when(securityService.getCurrentUser()).thenThrow(new AccessDeniedException("Пользователь не найден"));

        assertThrows(AccessDeniedException.class, () -> adService.getAdsByUser());

        verify(securityService).getCurrentUser();
    }

    /**
     * Тест успешного обновления картинки объявления.
     */
    @Test
    void shouldReturnResultOfUpdateAvatarAdWhenSuccess() {
        MultipartFile mockFile = mock(MultipartFile.class);
        String newImagePath = "/new-avatar.jpg";

        when(adRepository.findById(1)).thenReturn(Optional.ofNullable(testAdEntity));
        securityService.checkPermissionToEditAd(testAdEntity);
        when(imageService.saveImage(mockFile)).thenReturn(newImagePath);
        when(adRepository.save(any(AdEntity.class))).thenReturn(testAdEntity);

        adService.updateAvatarAd(1, mockFile);
        assertEquals(newImagePath, testAdEntity.getImagePath());

        verify(imageService).saveImage(mockFile);
        verify(adRepository).save(testAdEntity);
    }

    /**
     * Тест на выброс исключения AdNotFoundException, в случае отсутствия в БД id объявления,
     * в которое нужно обновить картинку
     */
    @Test
    void shouldReturnResultOfUpdateAvatarAdWhenDoNotId() {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(adRepository.findById(2)).thenThrow(AdNotFoundException.class);

        assertThrows(AdNotFoundException.class, () -> adService.updateAvatarAd(2, mockFile));

        verify(adRepository).findById(2);
    }

    /**
     * Тест на выброс исключения AccessDeniedException, в случае отсутствия прав на редактирование объявления
     */
    @Test
    void shouldReturnResultOfUpdateAvatarAdWhenNoEditingRights() {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(adRepository.findById(1)).thenReturn(Optional.ofNullable(testAdEntity));

        doThrow(new AccessDeniedException("Нет прав на редактирование объявления"))
                .when(securityService)
                .checkPermissionToEditAd(testAdEntity);

        assertThrows(AccessDeniedException.class, () -> adService.updateAvatarAd(1, mockFile));

        verify(adRepository).findById(1);
        verify(securityService).checkPermissionToEditAd(testAdEntity);
    }

    /**
     * Тест на удаление объявления
     */
    @Test
    void shouldReturnResultOfDeleteAdWhenSuccess() {
        when(adRepository.findById(1)).thenReturn(Optional.ofNullable(testAdEntity));
        when(securityService.isOwnerOfAd(testAdEntity)).thenReturn(true);

        securityService.checkPermissionToDeleteAd(testAdEntity);
        verify(securityService).checkPermissionToDeleteAd(testAdEntity);

        adService.deleteAd(1);

        verify(securityService).isOwnerOfAd(testAdEntity);
        verify(adRepository).findById(1);
        verify(adRepository).delete(testAdEntity);
    }

    /**
     * Тест на выброс исключения AdNotFoundException, в случае отсутствия в БД id объявления, которое нужно обновить
     */
    @Test
    void shouldReturnResultOfDeleteAdWhenDoNotIdAdExist() {
        when(adRepository.findById(2)).thenThrow(AdNotFoundException.class);

        assertThrows(AdNotFoundException.class, () -> adService.deleteAd(2));

        verify(adRepository).findById(2);
    }

    /**
     * Тест на выброс исключения AccessDeniedException, в случае отсутствия прав на редактирование объявления
     */
    @Test
    void shouldReturnResultOfDeleteAdWhenNoEditingRights() {
        when(adRepository.findById(1)).thenReturn(Optional.ofNullable(testAdEntity));

        doThrow(new AccessDeniedException("Нет прав на удаление объявления"))
                .when(securityService)
                .checkPermissionToDeleteAd(testAdEntity);

        assertThrows(AccessDeniedException.class, () -> adService.deleteAd(1));

        verify(adRepository).findById(1);
        verify(securityService).checkPermissionToDeleteAd(testAdEntity);
    }
}
