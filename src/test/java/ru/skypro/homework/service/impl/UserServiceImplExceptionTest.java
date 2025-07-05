package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.register.NewPassword;
import ru.skypro.homework.dto.register.UpdateUser;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.AccessDeniedException;
import ru.skypro.homework.exception.InvalidPasswordException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.SecurityService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тесты исключений для UserService.
 * Тестирует обработку исключений для всех публичных методов UserService.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplExceptionTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityService securityService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity testUser;
    private NewPassword testNewPassword;

    /**
     * Подготовка тестовых данных перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setPassword("encodedPassword");

        testNewPassword = new NewPassword();
        testNewPassword.setCurrentPassword("oldPassword");
        testNewPassword.setNewPassword("newPassword");
    }

    /**
     * Тест UserNotFoundException при обновлении пароля.
     * Метод: updatePassword
     * Сценарий: SecurityService не может найти текущего пользователя
     */
    @Test
    void updatePassword_WhenUserNotFound_ShouldThrowUserNotFoundException() {
        when(securityService.getCurrentUser()).thenThrow(new AccessDeniedException("Пользователь не найден"));

        assertThrows(AccessDeniedException.class, () -> userService.updatePassword(testNewPassword));
        verify(securityService).getCurrentUser();
        verify(passwordEncoder, never()).matches(any(), any());
        verify(userRepository, never()).save(any());
    }

    /**
     * Тест UserNotFoundException при получении информации о пользователе.
     * Метод: getInfoAboutUser
     * Сценарий: SecurityService не может найти текущего пользователя
     */
    @Test
    void getInfoAboutUser_WhenUserNotFound_ShouldThrowException() {
        when(securityService.getCurrentUser()).thenThrow(new AccessDeniedException("Пользователь не найден"));

        assertThrows(AccessDeniedException.class, () -> userService.getInfoAboutUser());
        verify(securityService).getCurrentUser();
        verify(userMapper, never()).toUserDto(any());
    }

    /**
     * Тест UserNotFoundException при обновлении информации о пользователе.
     * Метод: updateInfoAboutUser
     * Сценарий: SecurityService не может найти текущего пользователя
     */
    @Test
    void updateInfoAboutUser_WhenUserNotFound_ShouldThrowException() {
        UpdateUser updateUser = new UpdateUser();
        updateUser.setFirstName("Jane");
        when(securityService.getCurrentUser()).thenThrow(new AccessDeniedException("Пользователь не найден"));

        assertThrows(AccessDeniedException.class, () -> userService.updateInfoAboutUser(updateUser));
        verify(securityService).getCurrentUser();
        verify(userMapper, never()).updateUserEntityFromDto(any(), any());
        verify(userRepository, never()).save(any());
    }

    /**
     * Тест UserNotFoundException при обновлении аватара.
     * Метод: updateAvatarUser
     * Сценарий: SecurityService не может найти текущего пользователя
     */
    @Test
    void updateAvatarUser_WhenUserNotFound_ShouldThrowException() {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(securityService.getCurrentUser()).thenThrow(new AccessDeniedException("Пользователь не найден"));

        assertThrows(AccessDeniedException.class, () -> userService.updateAvatarUser(mockFile));
        verify(securityService).getCurrentUser();
        verify(imageService, never()).saveImage(any());
        verify(userRepository, never()).save(any());
    }

    /**
     * Тест InvalidPasswordException с правильным сообщением об ошибке.
     * Метод: updatePassword
     * Сценарий: Текущий пароль указан неверно
     */
    @Test
    void updatePassword_WithInvalidPassword_ShouldThrowExceptionWithCorrectMessage() {
        when(securityService.getCurrentUser()).thenReturn(testUser);
        when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(false);

        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class,
                () -> userService.updatePassword(testNewPassword));
        assertEquals("Неверный текущий пароль", exception.getMessage());
        verify(securityService).getCurrentUser();
        verify(passwordEncoder).matches("oldPassword", "encodedPassword");
    }

    /**
     * Тест обработки исключений от ImageService при получении аватара.
     * Метод: getUserAvatar
     * Сценарий: ImageService выбрасывает исключение при неудачном получении аватара
     */
    @Test
    void getUserAvatar_WhenImageServiceThrowsException_ShouldPropagateException() {
        when(imageService.getUserAvatar()).thenThrow(new RuntimeException("Image service error"));

        assertThrows(RuntimeException.class, () -> userService.getUserAvatar());
        verify(imageService).getUserAvatar();
    }

    /**
     * Тест обработки исключений от UserRepository при сохранении.
     * Метод: updatePassword
     * Сценарий: UserRepository выбрасывает исключение при неудачном сохранении пользователя
     */
    @Test
    void updatePassword_WhenRepositoryThrowsException_ShouldPropagateException() {
        when(securityService.getCurrentUser()).thenReturn(testUser);
        when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.updatePassword(testNewPassword));
        verify(securityService).getCurrentUser();
        verify(passwordEncoder).matches("oldPassword", "encodedPassword");
        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(testUser);
    }

    /**
     * Тест обработки исключений от UserMapper при преобразовании.
     * Метод: getInfoAboutUser
     * Сценарий: UserMapper выбрасывает исключение при неудачном преобразовании UserEntity в User
     */
    @Test
    void getInfoAboutUser_WhenMapperThrowsException_ShouldPropagateException() {
        when(securityService.getCurrentUser()).thenReturn(testUser);
        when(userMapper.toUserDto(testUser)).thenThrow(new RuntimeException("Mapper error"));

        assertThrows(RuntimeException.class, () -> userService.getInfoAboutUser());
        verify(securityService).getCurrentUser();
        verify(userMapper).toUserDto(testUser);
    }
} 